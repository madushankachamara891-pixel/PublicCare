import { useMemo, useState } from 'react'
import SiteHeader from './SiteHeader.jsx'
import { getReports } from './reportsData.js'
import './MyReports.css'

const statusClass = (status) => status.toLowerCase().replaceAll(' ', '-')

export default function MyReports() {
  const reports = getReports()
  const [query, setQuery] = useState('')
  const [filter, setFilter] = useState('All statuses')
  const [sort, setSort] = useState('Newest first')
  const visibleReports = useMemo(() => {
    const matching = reports.filter((item) =>
      (filter === 'All statuses' || item.status === filter) &&
      `${item.id} ${item.title} ${item.category} ${item.location}`.toLowerCase().includes(query.toLowerCase()),
    )
    return sort === 'Oldest first' ? [...matching].reverse() : matching
  }, [reports, query, filter, sort])
  const counts = reports.reduce((result, report) => {
    const key = report.status.toLowerCase()
    result[key] = (result[key] || 0) + 1
    return result
  }, {})

  function resetFilters() {
    setQuery('')
    setFilter('All statuses')
    setSort('Newest first')
  }

  return <main className="site-shell reports-page">
    <SiteHeader active="reports" reportsView />
    <section className="reports-hero">
      <div className="reports-hero-inner">
        <p className="breadcrumbs"><a href="/">Home</a><span>/</span><span>My reports</span></p>
        <div className="reports-title-row">
          <div><h1>My reports</h1><p>View and follow up on the issues youâ€™ve reported.</p></div>
          <a className="new-report-button" href="/create-issue">ï¼‹ Report new issue</a>
        </div>
      </div>
    </section>

    <section className="reports-content">
      <div className="reports-summary">
        <article><span className="summary-icon total">#</span><div><small>My reports</small><strong>{reports.length}</strong><small>Reports submitted by you</small></div></article>
        <article><span className="summary-icon pending">!</span><div><small>Pending</small><strong>{counts.pending || 0}</strong><small>Waiting for review</small></div></article>
        <article><span className="summary-icon progress">â†»</span><div><small>In progress</small><strong>{counts['in progress'] || 0}</strong><small>Being handled</small></div></article>
        <article><span className="summary-icon resolved">âœ“</span><div><small>Resolved</small><strong>{counts.resolved || 0}</strong><small>Marked as resolved</small></div></article>
      </div>

      <section className="reports-table-card">
        <div className="reports-section-heading"><div><h2>Your submitted issues</h2><p>Only reports submitted from your account appear here.</p></div></div>
        <div className="report-tools">
          <label className="report-search"><span aria-hidden="true">âŒ•</span><input value={query} onChange={(event) => setQuery(event.target.value)} placeholder="Search by reference, title or location" aria-label="Search reports by reference, title or location" /></label>
          <label><span className="sr-only">Filter reports by status</span><select value={filter} onChange={(event) => setFilter(event.target.value)}><option>All statuses</option><option>Pending</option><option>In progress</option><option>Resolved</option></select></label>
          <label><span className="sr-only">Sort reports</span><select value={sort} onChange={(event) => setSort(event.target.value)}><option>Newest first</option><option>Oldest first</option></select></label>
          <button className="reset-filters" type="button" onClick={resetFilters}>Reset filters</button>
        </div>
        <div className="reports-table-wrap"><table className="reports-table"><thead><tr><th scope="col">REFERENCE</th><th scope="col">ISSUE TITLE</th><th scope="col">CATEGORY</th><th scope="col">LOCATION</th><th scope="col">DATE REPORTED</th><th scope="col">STATUS</th><th scope="col">ACTIONS</th></tr></thead>
          <tbody>{visibleReports.map((report) => <tr key={report.id}>
            <td><a className="reference-link" href={`/issue-details?id=${report.id}`}>{report.id}</a></td>
            <td><a className="report-name" href={`/issue-details?id=${report.id}`}>{report.title}</a></td>
            <td>{report.category}</td><td>{report.location}</td><td>{report.date}</td>
            <td><span className={`report-status ${statusClass(report.status)}`}>{report.status}</span></td>
            <td><div className="report-actions"><a href={`/issue-details?id=${report.id}`}>View</a><a href={`/edit-issue?id=${report.id}`}>Edit</a><a href={`/issue-details?id=${report.id}`}>Track</a></div></td>
          </tr>)}{!visibleReports.length && <tr><td colSpan="7" className="no-reports">No reports match those filters.</td></tr>}</tbody>
        </table></div>
        <div className="table-pagination"><p>Showing {visibleReports.length ? `1â€“${visibleReports.length}` : '0'} of {visibleReports.length} reports</p><span>Rows per page: 4</span><nav aria-label="Reports pages"><button type="button" aria-label="Previous page" disabled>â†</button><button className="current-page" type="button" aria-current="page">1</button><button type="button" aria-label="Next page" disabled>â†’</button></nav></div>
        <aside className="reports-privacy"><span className="privacy-shield" aria-hidden="true">âœ“</span><div><strong>Your reports are private to your account</strong><p>You can edit your own reports. Only an administrator can delete a report.</p></div></aside>
      </section>

      <aside className="reports-empty"><span className="empty-icon" aria-hidden="true">â–¤</span><div><strong>No reports yet</strong><p>When you submit a public issue, it will appear here so you can track its progress.</p></div><a href="/create-issue">ï¼‹ Report your first issue</a></aside>
    </section>
    <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
  </main>
}
