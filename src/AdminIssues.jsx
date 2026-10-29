import { useMemo, useState } from 'react'
import SiteHeader from './SiteHeader.jsx'
import { getReports } from './reportsData.js'
import './AdminIssues.css'

const PAGE_SIZE = 3
const toStatusClass = (status) => status.toLowerCase().replaceAll(' ', '-')
const dateValue = (date) => new Date(`${date} 00:00:00`).getTime()

function downloadCsv(reports) {
  const columns = [
    ['Reference', 'id'], ['Issue title', 'title'], ['Category', 'category'],
    ['Location', 'location'], ['Date reported', 'date'], ['Status', 'status'],
  ]
  const escape = (value) => `"${String(value ?? '').replaceAll('"', '""')}"`
  const content = [columns.map(([label]) => escape(label)).join(','), ...reports.map((report) => columns.map(([, key]) => escape(report[key])).join(','))].join('\r\n')
  const url = URL.createObjectURL(new Blob([content], { type: 'text/csv;charset=utf-8' }))
  const link = document.createElement('a')
  link.href = url
  link.download = 'publiccare-filtered-reports.csv'
  link.click()
  URL.revokeObjectURL(url)
}

export default function AdminIssues() {
  const [reports] = useState(() => getReports())
  const [hiddenIds, setHiddenIds] = useState([])
  const [search, setSearch] = useState('')
  const [category, setCategory] = useState('All categories')
  const [status, setStatus] = useState('All statuses')
  const [sortOrder, setSortOrder] = useState('newest')
  const [page, setPage] = useState(1)

  const visibleReports = useMemo(() => {
    const query = search.trim().toLocaleLowerCase()
    return reports.filter((report) => !hiddenIds.includes(report.id))
      .filter((report) => category === 'All categories' || report.category === category)
      .filter((report) => status === 'All statuses' || report.status === status)
      .filter((report) => !query || [report.title, report.id, report.location, report.detailLocation].some((value) => value?.toLocaleLowerCase().includes(query)))
      .sort((a, b) => (dateValue(a.date) - dateValue(b.date)) * (sortOrder === 'newest' ? -1 : 1))
  }, [reports, hiddenIds, search, category, status, sortOrder])

  const pageCount = Math.max(1, Math.ceil(visibleReports.length / PAGE_SIZE))
  const currentPage = Math.min(page, pageCount)
  const pageReports = visibleReports.slice((currentPage - 1) * PAGE_SIZE, currentPage * PAGE_SIZE)
  const counts = reports.filter((report) => !hiddenIds.includes(report.id)).reduce((acc, report) => {
    const key = report.status.toLowerCase()
    acc[key] = (acc[key] || 0) + 1
    return acc
  }, {})

  function resetFilters() {
    setSearch('')
    setCategory('All categories')
    setStatus('All statuses')
    setSortOrder('newest')
    setPage(1)
  }

  function confirmDemoHide(report) {
    const confirmed = window.confirm(`Hide ${report.id} from this demo view? This does not permanently delete or change the saved report.`)
    if (confirmed) setHiddenIds((ids) => [...ids, report.id])
  }

  return <main className="site-shell admin-page admin-issues-page">
    <SiteHeader active="reports" adminView reportsView trackReportLabel />
    <section className="admin-banner admin-issues-banner">
      <div className="admin-banner-inner">
        <p className="admin-breadcrumbs"><a href="/">Home</a><span>/</span><a href="/admin">Admin</a><span>/</span><span>Issue management</span></p>
        <div className="admin-banner-row"><div><h1>Issue management</h1><p>View, search and manage public issue reports.</p></div><a className="admin-new-issue" href="/create-issue"><span aria-hidden="true">＋</span> New issue</a></div>
      </div>
    </section>

    <section className="admin-issues-content">
      <div className="admin-summary" aria-label="Issue summary">
        <article className="admin-stat-card"><div className="admin-stat-top"><span className="admin-stat-icon blue">#</span><span>Total reports</span></div><strong>{reports.length - hiddenIds.length}</strong><small>All sample reports</small></article>
        <article className="admin-stat-card"><div className="admin-stat-top"><span className="admin-stat-icon amber">!</span><span>Pending</span></div><strong>{counts.pending || 0}</strong><small>Awaiting review</small></article>
        <article className="admin-stat-card"><div className="admin-stat-top"><span className="admin-stat-icon sky">↻</span><span>In progress</span></div><strong>{counts['in progress'] || 0}</strong><small>Currently being handled</small></article>
        <article className="admin-stat-card"><div className="admin-stat-top"><span className="admin-stat-icon green">✓</span><span>Resolved</span></div><strong>{counts.resolved || 0}</strong><small>Marked as resolved</small></article>
      </div>

      <section className="admin-panel issue-management-panel">
        <div className="issues-panel-heading"><div><h2>All issue reports</h2><p>Search and filter reports, then choose an action for an issue.</p></div><button className="issues-export-button" type="button" onClick={() => downloadCsv(visibleReports)} disabled={!visibleReports.length}>Export CSV</button></div>
        <div className="issues-filters" role="search">
          <label className="issues-search"><span aria-hidden="true">⌕</span><input type="search" value={search} onChange={(event) => { setSearch(event.target.value); setPage(1) }} placeholder="Search by title, reference or location" aria-label="Search by title, reference or location" /></label>
          <select value={category} onChange={(event) => { setCategory(event.target.value); setPage(1) }} aria-label="Filter by category"><option>All categories</option>{[...new Set(reports.map((report) => report.category))].sort().map((item) => <option key={item}>{item}</option>)}</select>
          <select value={status} onChange={(event) => { setStatus(event.target.value); setPage(1) }} aria-label="Filter by status"><option>All statuses</option>{['Pending', 'In progress', 'Resolved'].map((item) => <option key={item}>{item}</option>)}</select>
          <select value={sortOrder} onChange={(event) => { setSortOrder(event.target.value); setPage(1) }} aria-label="Sort reports by date"><option value="newest">Newest first ↓</option><option value="oldest">Oldest first ↑</option></select>
          <button className="issues-reset-button" type="button" onClick={resetFilters}>Reset</button>
        </div>

        <div className="issues-table-wrap"><table className="issues-table">
          <thead><tr><th scope="col">Reference</th><th scope="col">Issue title</th><th scope="col">Category</th><th scope="col">Location</th><th scope="col">Date reported</th><th scope="col">Status</th><th scope="col">Actions</th></tr></thead>
          <tbody>{pageReports.map((report) => <tr key={report.id}>
            <td><a className="issues-reference" href={`/issue-details?id=${encodeURIComponent(report.id)}`}>{report.id}</a></td><td className="issues-title-cell">{report.title}</td><td>{report.category}</td><td>{report.location}</td><td className="issues-date-cell">{report.date}</td>
            <td><span className={`issues-status ${toStatusClass(report.status)}`}>{report.status}</span></td>
            <td><div className="issues-row-actions"><a href={`/issue-details?id=${encodeURIComponent(report.id)}`}>View</a><a href={`/admin/update-issue?id=${encodeURIComponent(report.id)}`}>Edit</a><button type="button" onClick={() => confirmDemoHide(report)}>Delete</button></div></td>
          </tr>)}
          {!pageReports.length && <tr><td className="issues-empty" colSpan="7">No reports match your search or filters.</td></tr>}
          </tbody>
        </table></div>

        <div className="issues-table-footer"><p>Showing {visibleReports.length ? (currentPage - 1) * PAGE_SIZE + 1 : 0}–{Math.min(currentPage * PAGE_SIZE, visibleReports.length)} of {visibleReports.length} reports</p><span className="issues-page-size">Rows per page: {PAGE_SIZE}</span><nav className="issues-pagination" aria-label="Report pages">
          <button type="button" aria-label="Previous page" disabled={currentPage <= 1} onClick={() => setPage((value) => Math.max(1, value - 1))}>←</button>
          {Array.from({ length: pageCount }, (_, index) => index + 1).map((number) => <button key={number} type="button" aria-current={number === currentPage ? 'page' : undefined} className={number === currentPage ? 'current' : ''} onClick={() => setPage(number)}>{number}</button>)}
          <button type="button" aria-label="Next page" disabled={currentPage >= pageCount} onClick={() => setPage((value) => Math.min(pageCount, value + 1))}>→</button>
        </nav></div>
        <p className="issues-demo-note">Sample reports are stored locally for this demo. Delete only hides a report until you reload the page.</p>
      </section>

      <aside className="issues-management-note"><strong>Built for clear issue management</strong><span>Create new reports, read and search existing issues, edit details, and delete with confirmation.</span></aside>
    </section>
    <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
  </main>
}
