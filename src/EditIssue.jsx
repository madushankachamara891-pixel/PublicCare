import { useRef, useState } from 'react'
import SiteHeader from './SiteHeader.jsx'
import { getSelectedReport } from './reportsData.js'
import './ReportDetail.css'

export default function EditIssue() {
  const report = getSelectedReport()
  const photoInput = useRef(null)
  const [photo, setPhoto] = useState(report.photo || '')

  function saveChanges(event) {
    event.preventDefault()
    const form = new FormData(event.currentTarget)
    const storageKey = `publiccare-${report.id}`
    let existing = {}
    try {
      const saved = JSON.parse(localStorage.getItem(storageKey) || '{}')
      if (saved && typeof saved === 'object' && !Array.isArray(saved)) existing = saved
    } catch {
      // Keep this edit from crashing if older local data is malformed.
    }
    localStorage.setItem(storageKey, JSON.stringify({
      ...existing,
      title: form.get('title'),
      category: form.get('category'),
      location: form.get('location'),
      description: form.get('description'),
      photo,
      id: report.id,
    }))
    window.location.assign(`/issue-details?id=${report.id}`)
  }

  function choosePhoto(event) {
    const file = event.target.files?.[0]
    if (file) setPhoto(file.name)
  }

  return <main className="site-shell detail-page edit-issue-page">
    <SiteHeader active="reports" reportsView />
    <section className="detail-banner edit-issue-banner">
      <div className="detail-inner">
        <p className="breadcrumbs"><a href="/">Home</a><span>/</span><a href="/my-reports">My reports</a><span>/</span><a href={`/issue-details?id=${report.id}`}>{report.id}</a><span>/</span><span>Edit</span></p>
        <div className="edit-banner-row"><div><h1>Edit your report</h1><p>Update the details of your public issue report.</p></div><span className="reference-pill">REFERENCE&nbsp; {report.id}</span></div>
      </div>
    </section>

    <section className="edit-issue-layout">
      <form className="edit-issue-form" onSubmit={saveChanges}>
        <div className="edit-form-heading"><h2>Issue details</h2><p>Fields marked with * are required.</p></div>

        <label htmlFor="edit-title">Issue title *</label>
        <input id="edit-title" name="title" defaultValue={report.title} required />

        <label htmlFor="edit-category">Issue category *</label>
        <select id="edit-category" name="category" defaultValue={report.category} required>
          <option>Streetlights</option><option>Road damage</option><option>Waste &amp; garbage</option><option>Drainage</option><option>Road safety</option><option>Other public issue</option>
        </select>

        <label htmlFor="edit-description">Description *</label>
        <textarea id="edit-description" name="description" defaultValue={report.description} rows="4" required />
        <p className="edit-field-hint">Describe what happened and why it needs attention.</p>

        <label htmlFor="edit-location">Location / address *</label>
        <input id="edit-location" name="location" defaultValue={report.detailLocation || report.location} required />
        <p className="edit-field-hint">Add a street, town or nearby landmark.</p>

        <div className="photo-edit-heading"><strong>Photo attachment</strong><span>Optional · PNG or JPG</span></div>
        <div className="photo-edit-row">
          <div className="current-photo"><div className="edit-photo-placeholder" aria-label={photo ? `Current attachment ${photo}` : 'No current attachment'}>▧</div><div className="current-photo-info"><strong>{photo || 'No photo attached'}</strong><small>{photo ? 'Current attachment' : 'Add a photo if it helps explain the issue.'}</small>{photo && <div className="photo-actions"><button type="button" onClick={() => photoInput.current?.click()}>Replace photo</button><button className="remove-photo" type="button" onClick={() => { setPhoto(''); if (photoInput.current) photoInput.current.value = '' }}>Remove photo</button></div>}</div></div>
          <label className="add-photo-tile" htmlFor="edit-photo"><span className="add-photo-icon">↑</span><span><strong>Add a new photo</strong><small>Browse files or drag one here</small></span></label>
        </div>
        <input ref={photoInput} className="edit-file-input" id="edit-photo" type="file" accept="image/png,image/jpeg" onChange={choosePhoto} />
        <p className="edit-field-hint photo-size-hint">Maximum file size will be validated when uploading.</p>

        <div className="edit-issue-actions"><a className="edit-cancel-button" href={`/issue-details?id=${report.id}`}>Cancel</a><button className="edit-save-button" type="submit">Save changes <span aria-hidden="true">✓</span></button></div>
      </form>

      <aside className="edit-guidance">
        <section className="before-save-card"><div className="guidance-heading"><span className="guidance-icon">i</span><h2>Before you save</h2></div><p>Please check your report details carefully.</p><ol><li><span>01</span><div><strong>Be specific</strong><small>Use a clear title and accurate description.</small></div></li><li><span>02</span><div><strong>Check the location</strong><small>Add a nearby street or landmark.</small></div></li><li><span>03</span><div><strong>Protect your privacy</strong><small>Do not include private or sensitive information.</small></div></li></ol></section>
        <section className="edit-side-note"><span className="guidance-icon">✓</span><div><h2>Your report, your details</h2><p>You can edit only reports submitted from your account. Only an administrator can delete reports.</p></div></section>
        <section className="edit-side-note leaving-note"><h2>Leaving without saving?</h2><p>Changes that are not saved will not be applied.</p></section>
      </aside>
    </section>

    <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
  </main>
}
