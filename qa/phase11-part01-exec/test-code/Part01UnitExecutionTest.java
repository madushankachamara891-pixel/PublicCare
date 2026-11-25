package lk.publiccare.backend.part01;

import static lk.publiccare.backend.part01.Part01Support.observe;
import static lk.publiccare.backend.part01.Part01Support.repeat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import lk.publiccare.backend.admin.AdminAccountSetupService;
import lk.publiccare.backend.admin.AdminSetupRunner;
import lk.publiccare.backend.auth.AuthDtos;
import lk.publiccare.backend.auth.AuthService;
import lk.publiccare.backend.auth.DuplicateEmailException;
import lk.publiccare.backend.exception.ApiExceptionHandler;
import lk.publiccare.backend.report.AdminReportController;
import lk.publiccare.backend.report.PrivateImageStorage;
import lk.publiccare.backend.report.PublicCareReport;
import lk.publiccare.backend.report.ReportDeletionService;
import lk.publiccare.backend.report.ReportDtos;
import lk.publiccare.backend.report.ReportImage;
import lk.publiccare.backend.report.ReportImageRepository;
import lk.publiccare.backend.report.ReportImageService;
import lk.publiccare.backend.report.ReportNotFoundException;
import lk.publiccare.backend.report.ReportProgressService;
import lk.publiccare.backend.report.ReportProgressUpdate;
import lk.publiccare.backend.report.ReportProgressUpdateRepository;
import lk.publiccare.backend.report.ReportRepository;
import lk.publiccare.backend.report.ReportService;
import lk.publiccare.backend.report.ReportStatus;
import lk.publiccare.backend.user.PublicCareUser;
import lk.publiccare.backend.user.UserRepository;
import lk.publiccare.backend.user.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Part 01 (EC4206 Section 3.2) unit test cases, executed with JUnit 5 and Mockito against unchanged production classes. */
@ExtendWith(OutputCaptureExtension.class)
class Part01UnitExecutionTest {
    private static final String PASSWORD = "Synthetic-Pass-123!";

    private static PublicCareReport report(long id, ReportStatus status, boolean approved) {
        PublicCareUser owner = new PublicCareUser("Owner", "owner@qa.example.test", "hash", UserRole.CITIZEN);
        ReflectionTestUtils.setField(owner, "id", 1L);
        PublicCareReport report = new PublicCareReport("Title", "Description", "Roads", "Street", owner);
        ReflectionTestUtils.setField(report, "id", id);
        report.setStatus(status);
        if (approved) report.approveForPublic(9L);
        return report;
    }

    // -------------------------------------------------------------- UT-01 – UT-03 AuthService

    @Test @DisplayName("UT-01 AuthService.register: normalisation, BCrypt hash and role")
    void ut01() {
        UserRepository users = mock(UserRepository.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        when(users.existsByEmail(anyString())).thenReturn(false);
        when(users.saveAndFlush(any(PublicCareUser.class))).thenAnswer(inv -> inv.getArgument(0));
        AuthDtos.UserResponse response = new AuthService(users, encoder)
                .register(new AuthDtos.RegisterRequest("  Padded Name  ", " A@X.com ", PASSWORD));
        ArgumentCaptor<PublicCareUser> saved = ArgumentCaptor.forClass(PublicCareUser.class);
        verify(users).saveAndFlush(saved.capture());
        PublicCareUser user = saved.getValue();
        List<String> responseFields = new ArrayList<>();
        for (var component : AuthDtos.UserResponse.class.getRecordComponents()) responseFields.add(component.getName());
        observe("UT-01", "stored email='" + user.getEmail() + "' name='" + user.getFullName() + "' hash prefix=" + user.getPasswordHash().substring(0, 7)
                + " matches raw=" + encoder.matches(PASSWORD, user.getPasswordHash()) + " role=" + user.getRole() + "; response fields=" + responseFields);
        assertThat(user.getEmail()).isEqualTo("a@x.com");
        assertThat(user.getFullName()).isEqualTo("Padded Name");
        assertThat(user.getPasswordHash()).startsWith("$2a$12$").isNotEqualTo(PASSWORD);
        assertThat(encoder.matches(PASSWORD, user.getPasswordHash())).isTrue();
        assertThat(user.getRole()).isEqualTo(UserRole.CITIZEN);
        assertThat(response.role()).isEqualTo(UserRole.CITIZEN);
        assertThat(responseFields).doesNotContain("password", "passwordHash");
    }

    @Test @DisplayName("UT-02 Registration race: unique-constraint error becomes DuplicateEmailException")
    void ut02() {
        UserRepository users = mock(UserRepository.class);
        when(users.existsByEmail(anyString())).thenReturn(false);
        when(users.saveAndFlush(any(PublicCareUser.class))).thenThrow(new DataIntegrityViolationException("uk_users_email"));
        Throwable thrown = null;
        try { new AuthService(users, new BCryptPasswordEncoder(4)).register(new AuthDtos.RegisterRequest("QA", "race@qa.example.test", PASSWORD)); }
        catch (Throwable t) { thrown = t; }
        observe("UT-02", "pre-check false, saveAndFlush throws DataIntegrityViolationException -> " + (thrown == null ? "no exception" : thrown.getClass().getSimpleName()));
        assertThat(thrown).isInstanceOf(DuplicateEmailException.class);
    }

    @Test @DisplayName("UT-03 Password byte limit at registration")
    void ut03() {
        UserRepository users = mock(UserRepository.class);
        when(users.saveAndFlush(any(PublicCareUser.class))).thenAnswer(inv -> inv.getArgument(0));
        AuthService service = new AuthService(users, new BCryptPasswordEncoder(4));
        String bytes72 = repeat('é', 10) + repeat('a', 52);
        String bytes73 = repeat('é', 10) + repeat('a', 53);
        assertThat(bytes72.getBytes(StandardCharsets.UTF_8)).hasSize(72);
        assertThat(bytes73.getBytes(StandardCharsets.UTF_8)).hasSize(73);
        String r72;
        String r73;
        try { service.register(new AuthDtos.RegisterRequest("QA", "b72@qa.example.test", bytes72)); r72 = "accepted"; } catch (RuntimeException e) { r72 = e.getClass().getSimpleName() + ": " + e.getMessage(); }
        try { service.register(new AuthDtos.RegisterRequest("QA", "b73@qa.example.test", bytes73)); r73 = "accepted"; } catch (RuntimeException e) { r73 = e.getClass().getSimpleName() + ": " + e.getMessage(); }
        observe("UT-03", "72 bytes / " + bytes72.length() + " chars -> " + r72 + "; 73 bytes / " + bytes73.length() + " chars -> " + r73);
        assertThat(r72).isEqualTo("accepted");
        assertThat(r73).isEqualTo("IllegalArgumentException: Password must be at most 72 UTF-8 bytes.");
    }

    // -------------------------------------------------------------- UT-06 ReportService.updateStatus

    @Test @DisplayName("UT-06 ReportService.updateStatus")
    void ut06() {
        ReportRepository reports = mock(ReportRepository.class);
        when(reports.save(any(PublicCareReport.class))).thenAnswer(inv -> inv.getArgument(0));
        ReportService service = new ReportService(reports, mock(UserRepository.class));
        PublicCareReport same = report(11L, ReportStatus.PENDING, true);
        same.setAdminUpdateNote("old");
        when(reports.findById(11L)).thenReturn(Optional.of(same));
        service.updateStatus(11L, new ReportDtos.StatusRequest(ReportStatus.PENDING, "note"));
        boolean step1 = same.isPublicApproved();
        PublicCareReport changed = report(12L, ReportStatus.PENDING, true);
        when(reports.findById(12L)).thenReturn(Optional.of(changed));
        service.updateStatus(12L, new ReportDtos.StatusRequest(ReportStatus.IN_PROGRESS, "note"));
        String step3;
        try { service.updateStatus(12L, new ReportDtos.StatusRequest(null, "note")); step3 = "accepted"; }
        catch (IllegalArgumentException e) { step3 = "IllegalArgumentException: " + e.getMessage(); }
        service.updateStatus(12L, new ReportDtos.StatusRequest(ReportStatus.RESOLVED, null));
        observe("UT-06", "step 1 same status -> approval kept=" + step1 + " (observed; pass criterion blocked by RTM Q1 / BR-13)"
                + "; step 2 changed status -> approval=" + changed.isPublicApproved() + "; step 3 null status -> " + step3
                + "; step 4 null note -> note=" + changed.getAdminUpdateNote() + " status=" + changed.getStatus());
        assertThat(changed.isPublicApproved()).isFalse();
        assertThat(step3).isEqualTo("IllegalArgumentException: A valid status is required.");
        assertThat(changed.getAdminUpdateNote()).isNull();
    }

    // -------------------------------------------------------------- UT-08 ReportImageService image validation

    @Test @DisplayName("UT-08 ReportImageService.validateImage and isWebp (through upload)")
    void ut08() {
        Map<String, byte[]> inputs = new LinkedHashMap<>();
        inputs.put("valid JPG", Part01Support.jpg(10, 10));
        inputs.put("valid PNG", Part01Support.png(10, 10));
        inputs.put("WebP VP8L", Part01Support.webpVp8l());
        inputs.put("WebP VP8", Part01Support.webpVp8());
        inputs.put("WebP VP8X + VP8L", Part01Support.webpVp8x());
        byte[] truncated = java.util.Arrays.copyOf(Part01Support.webpVp8l(), 22);
        inputs.put("truncated WebP", truncated);
        byte[] mismatch = Part01Support.webpVp8l();
        Part01Support.littleEndian(mismatch, 4, 99);
        inputs.put("WebP with wrong RIFF length", mismatch);
        inputs.put("0 bytes", new byte[0]);
        inputs.put("PNG 4000 x 2500 (exactly 10,000,000 px)", Part01Support.png(4000, 2500));
        inputs.put("PNG 4000 x 2501 (10,004,000 px)", Part01Support.png(4000, 2501));
        Map<String, String> outcome = new LinkedHashMap<>();
        for (var entry : inputs.entrySet()) {
            ReportRepository reports = mock(ReportRepository.class);
            ReportImageRepository images = mock(ReportImageRepository.class);
            PrivateImageStorage storage = mock(PrivateImageStorage.class);
            when(reports.findOne(org.mockito.ArgumentMatchers.<Specification<PublicCareReport>>any())).thenReturn(Optional.of(report(5L, ReportStatus.PENDING, false)));
            when(storage.store(any(), anyString())).thenReturn(UUID.randomUUID() + ".bin");
            when(images.saveAndFlush(any(ReportImage.class))).thenAnswer(inv -> inv.getArgument(0));
            try {
                new ReportImageService(reports, images, storage).upload(1L, 5L,
                        new MockMultipartFile("image", "upload", "application/octet-stream", entry.getValue()));
                ArgumentCaptor<ReportImage> saved = ArgumentCaptor.forClass(ReportImage.class);
                verify(images).saveAndFlush(saved.capture());
                outcome.put(entry.getKey(), "accepted as " + saved.getValue().getContentType());
            } catch (IllegalArgumentException e) {
                outcome.put(entry.getKey(), "rejected: " + e.getMessage());
            }
        }
        outcome.forEach((k, v) -> observe("UT-08", k + " -> " + v));
        assertThat(outcome.get("valid JPG")).isEqualTo("accepted as image/jpeg");
        assertThat(outcome.get("valid PNG")).isEqualTo("accepted as image/png");
        assertThat(outcome.get("WebP VP8L")).isEqualTo("accepted as image/webp");
        assertThat(outcome.get("truncated WebP")).startsWith("rejected");
        assertThat(outcome.get("WebP with wrong RIFF length")).startsWith("rejected");
        assertThat(outcome.get("0 bytes")).startsWith("rejected");
        assertThat(outcome.get("PNG 4000 x 2500 (exactly 10,000,000 px)")).isEqualTo("accepted as image/png");
        assertThat(outcome.get("PNG 4000 x 2501 (10,004,000 px)")).startsWith("rejected");
    }

    // -------------------------------------------------------------- UT-09 ReportProgressService.create

    @Test @DisplayName("UT-09 ReportProgressService.create")
    void ut09() {
        ReportRepository reports = mock(ReportRepository.class);
        ReportProgressUpdateRepository updates = mock(ReportProgressUpdateRepository.class);
        PrivateImageStorage storage = mock(PrivateImageStorage.class);
        PublicCareReport approved = report(7L, ReportStatus.PENDING, true);
        when(reports.findById(7L)).thenReturn(Optional.of(approved));
        when(updates.saveAndFlush(any(ReportProgressUpdate.class))).thenAnswer(inv -> inv.getArgument(0));
        ReportProgressService service = new ReportProgressService(reports, updates, storage);
        Map<String, String> outcome = new LinkedHashMap<>();
        outcome.put("1 note 1000", run(() -> service.create(7L, ReportStatus.PENDING, repeat('n', 1000), null)));
        outcome.put("2 note 1001", run(() -> service.create(7L, ReportStatus.PENDING, repeat('n', 1001), null)));
        outcome.put("3 WebP photo", run(() -> service.create(7L, ReportStatus.PENDING, "note",
                new MockMultipartFile("image", "p.webp", "image/webp", Part01Support.webpVp8l()))));
        boolean approvalAfterSameStatus = approved.isPublicApproved();
        String key = UUID.randomUUID() + ".jpg";
        when(storage.store(any(), anyString())).thenReturn(key);
        when(updates.saveAndFlush(any(ReportProgressUpdate.class))).thenThrow(new IllegalStateException("simulated save failure"));
        outcome.put("4 valid JPG, save fails", run(() -> service.create(7L, ReportStatus.PENDING, "note",
                new MockMultipartFile("image", "p.jpg", "image/jpeg", Part01Support.jpg(10, 10)))));
        boolean compensatingDelete;
        try { verify(storage).delete(key); compensatingDelete = true; } catch (AssertionError e) { compensatingDelete = false; }
        PublicCareReport second = report(8L, ReportStatus.PENDING, true);
        when(reports.findById(8L)).thenReturn(Optional.of(second));
        org.mockito.Mockito.reset(updates);
        when(updates.saveAndFlush(any(ReportProgressUpdate.class))).thenAnswer(inv -> inv.getArgument(0));
        outcome.put("5 different status on approved report", run(() -> service.create(8L, ReportStatus.IN_PROGRESS, "note", null)));
        outcome.forEach((k, v) -> observe("UT-09", "step " + k + " -> " + v));
        observe("UT-09", "approval after same-status update (step 1)=" + approvalAfterSameStatus + "; step 4 compensating delete of " + key
                + " called=" + compensatingDelete + "; step 5 approval after status change=" + second.isPublicApproved());
        assertThat(outcome.get("1 note 1000")).isEqualTo("accepted");
        assertThat(outcome.get("2 note 1001")).isEqualTo("IllegalArgumentException: Update note must be 1000 characters or fewer.");
        assertThat(outcome.get("3 WebP photo")).isEqualTo("IllegalArgumentException: Only valid JPG or PNG images are supported.");
        assertThat(outcome.get("4 valid JPG, save fails")).startsWith("IllegalStateException");
        assertThat(compensatingDelete).isTrue();
        assertThat(second.isPublicApproved()).isFalse();
    }

    private static String run(Runnable action) {
        try { action.run(); return "accepted"; }
        catch (RuntimeException e) { return e.getClass().getSimpleName() + ": " + e.getMessage(); }
    }

    // -------------------------------------------------------------- UT-10 ReportProgressUpdate.approveNoteForPublic

    @Test @DisplayName("UT-10 ReportProgressUpdate.approveNoteForPublic")
    void ut10() {
        PublicCareReport report = report(3L, ReportStatus.PENDING, false);
        Map<String, String> outcome = new LinkedHashMap<>();
        for (String note : new String[] {null, "", "   "}) {
            ReportProgressUpdate update = new ReportProgressUpdate(report, ReportStatus.PENDING, note, null, null, null);
            String result = run(() -> update.approveNoteForPublic(42L));
            outcome.put("note " + (note == null ? "null" : "'" + note + "'"), result + "; flag=" + update.isNotePublicApproved()
                    + " approver=" + ReflectionTestUtils.getField(update, "lastNotePublicApprovedBy"));
        }
        ReportProgressUpdate valid = new ReportProgressUpdate(report, ReportStatus.PENDING, "Valid note", null, null, null);
        valid.approveNoteForPublic(42L);
        outcome.put("valid note", "flag=" + valid.isNotePublicApproved() + " approver=" + ReflectionTestUtils.getField(valid, "lastNotePublicApprovedBy")
                + " time set=" + (ReflectionTestUtils.getField(valid, "lastNotePublicApprovedAt") != null));
        outcome.forEach((k, v) -> observe("UT-10", k + " -> " + v));
        for (String key : List.of("note null", "note ''", "note '   '")) {
            assertThat(outcome.get(key)).startsWith("IllegalArgumentException").contains("flag=false approver=null");
        }
        assertThat(outcome.get("valid note")).isEqualTo("flag=true approver=42 time set=true");
    }

    // -------------------------------------------------------------- UT-12 PrivateImageStorage path guard

    @Test @DisplayName("UT-12 PrivateImageStorage.resolve path guard (through store/read/delete)")
    void ut12(@TempDir Path dir) throws Exception {
        PrivateImageStorage storage = new PrivateImageStorage(dir.toString());
        Map<String, String> outcome = new LinkedHashMap<>();
        String uuid = UUID.randomUUID().toString();
        for (String key : List.of("../x.jpg", "abc.jpg", uuid + ".gif")) {
            outcome.put(key, run(() -> storage.read(key)));
        }
        String stored = storage.store(new byte[] {1, 2, 3}, "jpg");
        Path expected = dir.toAbsolutePath().normalize().resolve(stored);
        outcome.put("<UUID>.jpg (" + stored + ")", "stored inside the storage directory=" + Files.exists(expected)
                + "; read back " + storage.read(stored).length + " bytes");
        outcome.forEach((k, v) -> observe("UT-12", "key " + k + " -> " + v));
        assertThat(outcome.get("../x.jpg")).isEqualTo("IllegalArgumentException: Invalid image storage key.");
        assertThat(outcome.get("abc.jpg")).isEqualTo("IllegalArgumentException: Invalid image storage key.");
        assertThat(outcome.get(uuid + ".gif")).isEqualTo("IllegalArgumentException: Invalid image storage key.");
        assertThat(stored).matches("[0-9a-f-]{36}\\.jpg");
        assertThat(Files.exists(expected)).isTrue();
    }

    // -------------------------------------------------------------- UT-13 ReportDeletionService

    @Test @DisplayName("UT-13 ReportDeletionService")
    void ut13(CapturedOutput output) throws Exception {
        ReportRepository reports = mock(ReportRepository.class);
        ReportImageRepository images = mock(ReportImageRepository.class);
        ReportProgressUpdateRepository updates = mock(ReportProgressUpdateRepository.class);
        PrivateImageStorage storage = mock(PrivateImageStorage.class);
        PublicCareReport report = report(21L, ReportStatus.RESOLVED, true);
        String imageKey = UUID.randomUUID() + ".png";
        String progressKey = UUID.randomUUID() + ".jpg";
        when(reports.findById(21L)).thenReturn(Optional.of(report));
        when(images.findByReportId(21L)).thenReturn(Optional.of(new ReportImage(report, imageKey, "image/png", 1)));
        when(updates.findByReportIdOrderByCreatedAtAscIdAsc(21L)).thenReturn(List.of(
                new ReportProgressUpdate(report, ReportStatus.RESOLVED, "done", progressKey, "image/jpeg", 1L)));
        ReportDeletionService service = new ReportDeletionService(reports, images, updates, storage);

        // Step 1 – files deleted only after the commit.
        TransactionSynchronizationManager.initSynchronization();
        boolean deletedBeforeCommit;
        try {
            service.deleteAdmin(21L);
            deletedBeforeCommit = org.mockito.Mockito.mockingDetails(storage).getInvocations().stream().anyMatch(i -> i.getMethod().getName().equals("delete"));
            for (TransactionSynchronization sync : TransactionSynchronizationManager.getSynchronizations()) sync.afterCommit();
        } finally {
            TransactionSynchronizationManager.clearSynchronization();
        }
        verify(storage).delete(imageKey);
        verify(storage).delete(progressKey);
        // Step 2 – missing report.
        String step2 = run(() -> service.deleteAdmin(404L));
        // Step 3 – database failure: no file touched; controller maps to 500.
        PrivateImageStorage storage3 = mock(PrivateImageStorage.class);
        ReportRepository reports3 = mock(ReportRepository.class);
        when(reports3.findById(21L)).thenReturn(Optional.of(report));
        doThrow(new IllegalStateException("simulated database failure")).when(reports3).delete(any(PublicCareReport.class));
        ReportDeletionService failing = new ReportDeletionService(reports3, images, updates, storage3);
        TransactionSynchronizationManager.initSynchronization();
        String step3;
        try { step3 = run(() -> failing.deleteAdmin(21L)); } finally { TransactionSynchronizationManager.clearSynchronization(); }
        verify(storage3, never()).delete(anyString());
        MockMvc mvc = MockMvcBuilders.standaloneSetup(new AdminReportController(null, null, null, failing))
                .setControllerAdvice(new ApiExceptionHandler()).build();
        TransactionSynchronizationManager.initSynchronization();
        MvcResult http;
        try { http = mvc.perform(MockMvcRequestBuilders.delete("/api/admin/reports/21")).andReturn(); }
        finally { TransactionSynchronizationManager.clearSynchronization(); }
        // Step 4 – file delete fails after commit: logged, no failure.
        PrivateImageStorage storage4 = mock(PrivateImageStorage.class);
        doThrow(new IllegalStateException("simulated file failure")).when(storage4).delete(anyString());
        ReportDeletionService fileFailure = new ReportDeletionService(reports, images, updates, storage4);
        TransactionSynchronizationManager.initSynchronization();
        String step4;
        try {
            fileFailure.deleteAdmin(21L);
            step4 = run(() -> TransactionSynchronizationManager.getSynchronizations().forEach(TransactionSynchronization::afterCommit));
        } finally {
            TransactionSynchronizationManager.clearSynchronization();
        }
        boolean logged = output.getAll().contains("Unable to remove private image after deleting report 21");
        observe("UT-13", "step 1 storage.delete called before commit=" + deletedBeforeCommit + ", after commit for both keys=true");
        observe("UT-13", "step 2 missing report -> " + step2 + "; step 3 database failure -> " + step3 + ", storage untouched=true, HTTP "
                + http.getResponse().getStatus() + " " + http.getResponse().getContentAsString());
        observe("UT-13", "step 4 file delete fails after commit -> " + step4 + "; error logged=" + logged);
        assertThat(deletedBeforeCommit).isFalse();
        assertThat(step2).startsWith("ReportNotFoundException");
        assertThat(step3).startsWith("IllegalStateException");
        assertThat(http.getResponse().getStatus()).isEqualTo(500);
        assertThat(http.getResponse().getContentAsString()).isEqualTo("{\"error\":\"Unable to delete report.\"}");
        assertThat(step4).isEqualTo("accepted");
        assertThat(logged).isTrue();
    }

    // -------------------------------------------------------------- UT-14 AdminSetupRunner.isLoopbackPostgres

    @Test @DisplayName("UT-14 AdminSetupRunner.isLoopbackPostgres (reflection, no source change)")
    void ut14() throws Exception {
        Method check = AdminSetupRunner.class.getDeclaredMethod("isLoopbackPostgres", String.class);
        check.setAccessible(true);
        Map<String, Boolean> outcome = new LinkedHashMap<>();
        for (String url : List.of("jdbc:postgresql://localhost:5432/db", "jdbc:postgresql://127.0.0.1:5432/db",
                "jdbc:postgresql://[::1]:5432/db", "jdbc:postgresql://db.remote.example:5432/db", "jdbc:mysql://localhost:3306/db")) {
            outcome.put(url, (Boolean) check.invoke(null, url));
        }
        outcome.forEach((k, v) -> observe("UT-14", k + " -> " + v + (k.contains("[::1]") ? " (observed; not judged, RTM Q1)" : "")));
        assertThat(outcome.get("jdbc:postgresql://localhost:5432/db")).isTrue();
        assertThat(outcome.get("jdbc:postgresql://127.0.0.1:5432/db")).isTrue();
        assertThat(outcome.get("jdbc:postgresql://db.remote.example:5432/db")).isFalse();
        assertThat(outcome.get("jdbc:mysql://localhost:3306/db")).isFalse();
    }

    // -------------------------------------------------------------- UT-15 AdminAccountSetupService

    @Test @DisplayName("UT-15 AdminAccountSetupService")
    void ut15() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        Map<String, String> outcome = new LinkedHashMap<>();
        String[][] missing = {{null, "admin@qa.example.test", PASSWORD}, {"QA Admin", null, PASSWORD}, {"QA Admin", "admin@qa.example.test", null}};
        String[] labels = {"missing name", "missing email", "missing password"};
        for (int i = 0; i < missing.length; i++) {
            UserRepository users = mock(UserRepository.class);
            String[] input = missing[i];
            outcome.put("1 " + labels[i], run(() -> new AdminAccountSetupService(users, encoder).createFirstAdmin(input[0], input[1], input[2]))
                    + "; saves=" + org.mockito.Mockito.mockingDetails(users).getInvocations().stream().filter(x -> x.getMethod().getName().startsWith("save")).count());
        }
        UserRepository users = mock(UserRepository.class);
        when(users.findByEmail("admin@qa.example.test")).thenReturn(Optional.empty());
        when(users.existsByRole(UserRole.ADMIN)).thenReturn(false);
        AdminAccountSetupService service = new AdminAccountSetupService(users, encoder);
        var first = service.createFirstAdmin("QA Admin", " Admin@QA.example.test ", PASSWORD);
        ArgumentCaptor<PublicCareUser> created = ArgumentCaptor.forClass(PublicCareUser.class);
        verify(users).saveAndFlush(created.capture());
        when(users.findByEmail("admin@qa.example.test")).thenReturn(Optional.of(created.getValue()));
        var second = service.createFirstAdmin("QA Admin", "admin@qa.example.test", PASSWORD);
        verify(users, org.mockito.Mockito.times(1)).saveAndFlush(any());
        outcome.put("2 first run / second run", first + " (role " + created.getValue().getRole() + ", email " + created.getValue().getEmail() + ") / " + second);
        UserRepository withCitizen = mock(UserRepository.class);
        when(withCitizen.findByEmail("citizen@qa.example.test")).thenReturn(Optional.of(new PublicCareUser("C", "citizen@qa.example.test", "h", UserRole.CITIZEN)));
        outcome.put("3 existing citizen email", run(() -> new AdminAccountSetupService(withCitizen, encoder).createFirstAdmin("QA", "citizen@qa.example.test", PASSWORD)));
        verify(withCitizen, never()).saveAndFlush(any());
        UserRepository withAdmin = mock(UserRepository.class);
        when(withAdmin.findByEmail("second@qa.example.test")).thenReturn(Optional.empty());
        when(withAdmin.existsByRole(UserRole.ADMIN)).thenReturn(true);
        outcome.put("4 second admin email", run(() -> new AdminAccountSetupService(withAdmin, encoder).createFirstAdmin("QA", "second@qa.example.test", PASSWORD)));
        verify(withAdmin, never()).saveAndFlush(any());
        outcome.forEach((k, v) -> observe("UT-15", "step " + k + " -> " + v));
        for (String label : labels) assertThat(outcome.get("1 " + label)).startsWith("IllegalArgumentException").endsWith("saves=0");
        assertThat(first).isEqualTo(AdminAccountSetupService.SetupResult.CREATED);
        assertThat(created.getValue().getRole()).isEqualTo(UserRole.ADMIN);
        assertThat(second).isEqualTo(AdminAccountSetupService.SetupResult.ALREADY_CONFIGURED);
        assertThat(outcome.get("3 existing citizen email")).isEqualTo("IllegalStateException: The requested email already belongs to a non-admin account.");
        assertThat(outcome.get("4 second admin email")).isEqualTo("IllegalStateException: An administrator already exists; setup did not change any account.");
        assertThatThrownBy(() -> { throw new ReportNotFoundException(); }).isInstanceOf(ReportNotFoundException.class);
    }
}
