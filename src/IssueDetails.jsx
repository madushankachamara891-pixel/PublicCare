import SiteHeader from './SiteHeader.jsx'
import { getSelectedReport, getStatusProgress } from './reportsData.js'
import './ReportDetail.css'

function longDate(date) {
  return date.replace(' Oct ', ' October ')
}

export default function IssueDetails() {
  const report = getSelectedReport()
  const issueStatus = report.status.toLowerCase().replaceAll(' ', '-')
  const submittedDate = longDate(report.date)
  const progress = getStatusProgress(report.status)

  return <main className="site-shell detail-page issue-tracking-page">
    <SiteHeader active="reports" reportsView />
    <section className="detail-banner tracking-banner">
      <div className="detail-inner">
        <p className="breadcrumbs"><a href="/">Home</a><span>/</span><a href="/my-reports">My reports</a><span>/</span><span>{report.id}</span></p>
        <div className="tracking-banner-row"><div><h1>Issue details</h1><p>Review your report and follow its progress.</p></div><span className="reference-pill">REFERENCE&nbsp; {report.id}</span></div>
      </div>
    </section>

    <section className="tracking-content">
      <article className="issue-summary-card">
        <h2>{report.title}</h2>
        <div className="issue-summary-meta"><span className={`tracking-status ${issueStatus}`}>{report.status}</span><span>Reported on {submittedDate}</span></div>
        <div className="issue-facts"><div><small>CATEGORY</small><strong>{report.category}</strong></div><div><small>LOCATION</small><strong>{report.detailLocation || report.location}</strong></div></div>
        <section className="issue-copy"><h3>DESCRIPTION</h3><p>{report.description}</p></section>
        <section className="issue-copy additional-info"><h3>ADDITIONAL INFORMATION</h3><p>{report.additionalInformation || 'No additional information was provided.'}</p></section>
        <section className="attached-photo"><h3>ATTACHED PHOTO</h3><div className="photo-row"><div className="photo-placeholder" aria-label={report.photo ? `Photo attachment: ${report.photo}` : 'No photo attached'}>▧</div><div><strong>{report.photo || 'No photo attached'}</strong><small>{report.photo ? 'Uploaded with your report' : 'No image was included with this report.'}</small></div></div></section>
      </article>

      <aside className="progress-card">
        <h2>Report progress</h2><p className="progress-intro">Follow the latest updates to your issue.</p>
        <span className={`tracking-status ${issueStatus}`}>{report.status}</span>
        <ol className="progress-timeline">
          {progress.steps.map((step, index) => {
            const date = index === 0 ? submittedDate : index === 3 && report.status === 'Resolved' && report.updated ? longDate(report.updated) : ''
            const detail = date || (step.current ? 'Current status' : step.complete ? 'Completed' : 'Not yet reached')
            const message = index === 0 ? 'Your report was received.' : step.current ? 'This is the current status of your report.' : step.complete ? 'This step is complete.' : 'This step has not been reached.'
            return <li key={step.label} className={step.complete ? 'done' : step.current ? 'active' : 'upcoming'}><span className="timeline-marker">{step.complete ? '✓' : ''}</span><div><strong>{step.label}</strong><small>{detail}</small><p>{message}</p></div></li>
          })}
        </ol>
        <p className="updates-note">{report.adminUpdateNote ? `Latest administrator update: ${report.adminUpdateNote}` : 'Updates appear here when the report status changes.'}</p>
      </aside>

      <section className="update-report-card"><div><h2>Need to update your report?</h2><p>You can edit your own report. Only an administrator can delete reports.</p></div><div className="tracking-buttons"><a className="back-reports-button" href="/my-reports"><span aria-hidden="true">←</span> Back to My Reports</a><a className="edit-my-issue-button" href={`/edit-issue?id=${report.id}`}>Edit my issue <span aria-hidden="true">✎</span></a></div></section>
      <aside className="help-card"><span className="help-icon" aria-hidden="true">i</span><div><h2>Need help?</h2><p>If you have more information about this issue, update your report or contact the relevant local authority.</p></div></aside>
    </section>
    <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
  </main>
}
