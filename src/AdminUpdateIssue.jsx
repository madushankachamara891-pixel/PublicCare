import { useMemo, useRef, useState } from 'react'
import SiteHeader from './SiteHeader.jsx'
import { getReportById, getReports, getStatusProgress, normalizeStatus, saveReportUpdates } from './reportsData.js'
import './AdminUpdateIssue.css'

const statusOptions = ['Pending', 'In progress', 'Resolved']
const statusClass = (status) => status.toLowerCase().replaceAll(' ', '-')
const statusDescription = (status) => status === 'Pending' ? 'This issue is waiting to be reviewed.' : status === 'Resolved' ? 'This issue is marked as resolved.' : 'This issue is currently being handled.'

export default function AdminUpdateIssue() {
  const availableReports = getReports()
  const requestedId = new URLSearchParams(window.location.search).get('id')
  const [selectedReportId, setSelectedReportId] = useState(() => requestedId || availableReports[0]?.id || '')
  const [reportRevision, setReportRevision] = useState(0)
  const report = useMemo(() => getReportById(selectedReportId), [selectedReportId, reportRevision])
  const attachmentInput = useRef(null)
  const [status, setStatus] = useState(report?.status || 'Pending')
  const [adminUpdateNote, setAdminUpdateNote] = useState(report?.adminUpdateNote || '')
  const [attachment, setAttachment] = useState('')
  const [saveError, setSaveError] = useState('')
  const [saveSuccess, setSaveSuccess] = useState('')
  const progress = getStatusProgress(report?.status)

  function selectReport(id) {
    const selectedReport = getReportById(id)
    setSelectedReportId(id)
    setStatus(selectedReport?.status || 'Pending')
    setAdminUpdateNote(selectedReport?.adminUpdateNote || '')
    setAttachment('')
    setSaveError('')
    setSaveSuccess('')
  }

  function saveChanges(event) {
    event.preventDefault()
    const validatedStatus = normalizeStatus(status)
    if (!report || !availableReports.some((item) => item.id === report.id) || !validatedStatus) {
      setSaveError('Select a valid report and status before saving.')
      setSaveSuccess('')
      return
    }
    if (saveReportUpdates(report.id, { status: validatedStatus, adminUpdateNote })) {
      setStatus(validatedStatus)
      setReportRevision((revision) => revision + 1)
      setSaveError('')
      setSaveSuccess(`Changes to ${report.id} were saved.`)
    } else {
      setSaveError('The update could not be saved. Please try again.')
      setSaveSuccess('')
    }
  }

  function selectAttachment(event) {
    const file = event.target.files?.[0]
    if (file) setAttachment(file.name)
  }

  if (!report) return <main className="site-shell admin-page admin-update-page">
    <SiteHeader active="reports" adminView reportsView trackReportLabel />
    <section className="admin-banner admin-update-banner"><div className="admin-banner-inner"><p className="admin-breadcrumbs"><a href="/">Home</a><span>/</span><a href="/admin">Admin</a><span>/</span><a href="/admin/issues">Issue Management</a><span>/</span><span>Update issue</span></p><div className="admin-banner-row"><div><h1>Update Issue</h1><p>Select a report to review and update its details.</p></div></div></div></section>
    <section className="admin-update-missing"><h2>Choose a report</h2><label className="update-report-selector-label" htmlFor="update-report">Report</label><div className="select-wrap"><select id="update-report" value="" onChange={(event) => selectReport(event.target.value)}><option value="">Select a report</option>{availableReports.map((item) => <option key={item.id} value={item.id}>{item.id} — {item.title}</option>)}</select></div><p role="status">{selectedReportId ? `No report matches reference ${selectedReportId}.` : 'Select a report to continue.'}</p><a href="/admin/issues">Back to Issue Management</a></section>
    <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
  </main>

  return <main className="site-shell admin-page admin-update-page">
    <SiteHeader active="reports" adminView reportsView trackReportLabel />
    <section className="admin-banner admin-update-banner">
      <div className="admin-banner-inner">
        <p className="admin-breadcrumbs"><a href="/">Home</a><span>/</span><a href="/admin">Admin</a><span>/</span><a href="/admin/issues">Issue Management</a><span>/</span><span>{report.id}</span></p>
        <div className="admin-banner-row"><div><h1>Update Issue</h1><p>Review and update the details of this public issue.</p></div><span className="update-reference">REFERENCE: {report.id}</span></div>
      </div>
    </section>

    <section className="admin-update-content">
      <form className="update-form-card" onSubmit={saveChanges}>
        <h2>Issue Information</h2>
        {saveError && <p className="update-save-error" role="alert">{saveError}</p>}{saveSuccess && <p className="update-save-success" role="status">{saveSuccess}</p>}
        <div className="update-field-grid">
          <label htmlFor="update-report">Select report</label><div className="select-wrap"><select id="update-report" value={report.id} onChange={(event) => selectReport(event.target.value)} aria-label="Select report to update">{availableReports.map((item) => <option key={item.id} value={item.id}>{item.id} — {item.title}</option>)}</select></div>
          <label htmlFor="update-id">Issue ID</label><input id="update-id" value={report.id} readOnly />
          <label htmlFor="update-title">Issue Title</label><input id="update-title" value={report.title} readOnly />
          <label htmlFor="update-category">Category</label><input id="update-category" value={report.category} readOnly />
          <label htmlFor="update-description">Description</label><textarea id="update-description" value={report.description || 'Not available'} readOnly rows="3" />
          <label htmlFor="update-location">Location / Address</label><input id="update-location" value={report.detailLocation || report.location || 'Not available'} readOnly />
          <label htmlFor="current-status">Current Status</label><div className="current-status-wrap"><span id="current-status" className={`update-status-pill ${statusClass(report.status)}`}><i />{report.status}</span><small>{statusDescription(report.status)}</small></div>
          <label htmlFor="update-status">Update Status <b>*</b></label><div className="select-wrap"><select id="update-status" value={status} onChange={(event) => setStatus(event.target.value)} required>{statusOptions.map((option) => <option key={option}>{option}</option>)}</select></div>
          <span className="field-spacer" /><small className="update-hint">Change the current status of this issue.</small>
          <label htmlFor="admin-update-note">Admin Update Note</label><div className="note-wrap"><textarea id="admin-update-note" name="adminUpdateNote" value={adminUpdateNote} onChange={(event) => setAdminUpdateNote(event.target.value)} rows="3" placeholder="Add a note about the current progress or actions taken." /><small>Add a note about the current progress, actions taken, or any additional information.</small></div>
        </div>

        <div className="attachments-section">
          <h3>Attachments <span>Optional</span></h3>
          <div className="attachment-row">
            <div className="existing-attachment"><span className="attachment-thumb" aria-hidden="true">▧</span><span><strong>{report.photo || 'No existing attachment'}</strong><small>{report.photo ? 'Attached to this report' : 'No file is attached to this report.'}</small></span></div>
            <button type="button" className="attachment-picker" onClick={() => attachmentInput.current?.click()}><span className="upload-icon">↑</span><span><strong>{attachment || 'Add new photo'}</strong><small>{attachment ? 'Selected locally; not uploaded' : 'JPG, PNG (Max 5MB)'}</small></span></button>
            <input ref={attachmentInput} className="update-file-input" type="file" accept="image/png,image/jpeg" onChange={selectAttachment} aria-label="Choose an optional photo attachment" />
          </div>
          <p className="attachment-disclaimer">Choosing a file only displays its name here. This demo does not upload files to a server.</p>
        </div>

        <div className="update-form-actions"><a href="/admin/issues">Cancel</a><button type="submit"><span aria-hidden="true">✓</span> Save Changes</button></div>
      </form>

      <aside className="admin-update-sidebar">
        <section className="update-side-card citizen-card"><h2>Citizen Information</h2><p className="unavailable-state">Not available</p><small>No citizen contact information is stored with this report.</small></section>
        <section className="update-side-card timeline-card"><h2>Timeline / Progress</h2><ol className="update-timeline">
          {progress.steps.map((step, index) => {
            const date = index === 0 ? report.date : index === 3 && report.status === 'Resolved' ? report.updated : ''
            const detail = date ? (index === 3 ? `Last updated ${date}` : date) : step.current ? 'Current status' : step.complete ? 'Completed' : 'Not yet reached'
            return <li key={step.label} className={step.current ? 'current' : step.complete ? 'complete' : 'future'}><span>{step.complete ? '✓' : ''}</span><div><strong>{step.label}</strong><small>{detail}</small></div></li>
          })}
        </ol>{report.adminUpdateNote && <p className="timeline-admin-note">Latest administrator update: {report.adminUpdateNote}</p>}</section>
        <section className="update-side-card helpful-card"><h2>Helpful Actions</h2><a href={`/issue-details?id=${encodeURIComponent(report.id)}`}><span>▤</span>View full report</a><button type="button" disabled><span>✉</span>Contact citizen <small>Not available</small></button><button type="button" onClick={() => setStatus('Resolved')}><span>✓</span>Mark as resolved</button></section>
      </aside>
    </section>
    <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
  </main>
}
