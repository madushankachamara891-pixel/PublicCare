import { useState } from 'react'
import './CreateIssue.css'
import SiteHeader from './SiteHeader.jsx'

function CreateIssue() {
  const [photoName, setPhotoName] = useState('')
  const [submitted, setSubmitted] = useState(false)

  function handleSubmit(event) {
    event.preventDefault()
    if (!event.currentTarget.reportValidity()) return
    setSubmitted(true)
  }

  return (
    <main className="site-shell create-issue-page">
      <SiteHeader />

      <section className="issue-banner">
        <div className="issue-banner-inner">
          <p className="breadcrumbs"><a href="/">Home</a><span>/</span><span>Report an issue</span></p>
          <div className="issue-banner-row"><div><h1>Report a public issue</h1><p>Tell us what needs attention in your community.</p></div><span className="step-pill">STEP 1 OF 2</span></div>
        </div>
      </section>

      <section className="issue-layout">
        <form className="issue-form-card" onSubmit={handleSubmit}>
          <div className="form-heading"><h2>Issue details</h2><p>Fields marked with * are required.</p></div>
          <label className="field-label" htmlFor="issue-title">Issue title *</label>
          <input id="issue-title" name="title" required placeholder="e.g. Large pothole near the school" />

          <label className="field-label" htmlFor="issue-category">Issue category *</label>
          <select id="issue-category" name="category" required defaultValue="">
            <option value="" disabled>Select an issue category</option>
            <option>Road damage</option><option>Waste &amp; garbage</option><option>Streetlights</option><option>Drainage</option><option>Water supply</option><option>Other public issue</option>
          </select>

          <label className="field-label" htmlFor="issue-description">Description *</label>
          <textarea id="issue-description" name="description" required placeholder="Describe the issue, what happened, and why it needs attention." />
          <p className="field-hint description-hint">Please avoid including private or sensitive personal information.</p>

          <label className="field-label" htmlFor="issue-location">Location / address *</label>
          <input id="issue-location" name="location" required placeholder="Street, town or nearby landmark" />

          <label className="field-label photo-label" htmlFor="issue-photo">Photo (optional)</label>
          <label className="upload-box" htmlFor="issue-photo">
            <span className="upload-icon" aria-hidden="true">↑</span><span className="upload-copy"><strong>{photoName || 'Choose a photo to upload'}</strong><small>PNG or JPG · Optional</small></span><span className="browse-link">Browse files</span>
          </label>
          <input className="file-input" id="issue-photo" name="photo" type="file" accept="image/png,image/jpeg" onChange={(event) => setPhotoName(event.target.files?.[0]?.name || '')} />
          <p className="field-hint upload-hint">For this design, photo upload is optional.</p>

          <div className="form-actions"><a className="cancel-button" href="/">Cancel</a><button className="submit-button" type="submit">Submit report <span aria-hidden="true">→</span></button></div>
          {submitted && <p className="demo-notice" role="status">Your report is ready for review. This demo does not save or send data to a server.</p>}
        </form>

        <aside className="issue-sidebar">
          <section className="why-card"><div className="why-heading"><span className="info-icon">i</span><h2>Why report an issue?</h2></div><p className="why-intro">Your report helps local services understand what needs attention.</p>
            <ol className="why-list"><li><span>01</span><div><strong>Be specific</strong><small>Include a clear location and useful details.</small></div></li><li><span>02</span><div><strong>Add a photo</strong><small>A photo can help explain the issue.</small></div></li><li><span>03</span><div><strong>Track progress</strong><small>Keep your reference number after submitting.</small></div></li></ol>
          </section>
          <section className="privacy-card"><h2>Your information matters</h2><p>Only share details needed to identify the public issue. Avoid uploading photos that reveal private information.</p><span>DESIGNED FOR PUBLIC REPORTING</span></section>
        </aside>
      </section>

      <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
    </main>
  )
}

export default CreateIssue
