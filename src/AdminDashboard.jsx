import SiteHeader from './SiteHeader.jsx'
import { getReports } from './reportsData.js'
import './AdminDashboard.css'

const categoryOrder = ['Streetlights', 'Waste & garbage', 'Road damage', 'Drainage', 'Others']

function daysAgo(date) {
  const [, day] = date.match(/(\d+) Oct/)
  const difference = 29 - Number(day)
  return difference === 1 ? '1 day ago' : `${difference} days ago`
}

function formatStatus(status) {
  return status === 'In progress' ? 'In Progress' : status
}

function activityDescription(report) {
  if (report.status === 'Resolved') return `Issue ${report.id} marked as Resolved`
  if (report.status === 'In progress') return `Issue ${report.id} status changed to In Progress`
  return `Issue ${report.id} marked as Pending`
}

export default function AdminDashboard() {
  const reports = getReports().sort((a, b) => Number(b.date.match(/(\d+) Oct/)[1]) - Number(a.date.match(/(\d+) Oct/)[1]))
  const counts = reports.reduce((result, report) => {
    const status = report.status.toLowerCase()
    result[status] = (result[status] || 0) + 1
    result.categories[report.category] = (result.categories[report.category] || 0) + 1
    return result
  }, { categories: {} })
  const stats = [
    { label: 'Total Issues', value: reports.length, detail: 'All sample reports', symbol: '▤', tone: 'blue' },
    { label: 'Pending', value: counts.pending || 0, detail: 'Awaiting review', symbol: '◷', tone: 'amber' },
    { label: 'In Progress', value: counts['in progress'] || 0, detail: 'Currently being handled', symbol: '↻', tone: 'sky' },
    { label: 'Resolved', value: counts.resolved || 0, detail: 'Successfully resolved', symbol: '✓', tone: 'green' },
  ]
  const activities = reports.slice(0, 4)

  return <main className="site-shell admin-page">
    <SiteHeader active="admin" adminView reportsView />
    <section className="admin-banner">
      <div className="admin-banner-inner">
        <p className="admin-breadcrumbs"><a href="/">Home</a><span>/</span><span>Admin</span><span>/</span><span>Dashboard</span></p>
        <div className="admin-banner-row"><div><h1>Admin Dashboard</h1><p>Overview of public issues and management activities.</p></div><span className="admin-date">▦&nbsp;&nbsp; 29 October 2026</span></div>
      </div>
    </section>

    <section className="admin-content">
      <div className="admin-summary" aria-label="Issue summary">
        {stats.map((stat) => <article className="admin-stat-card" key={stat.label}>
          <div className="admin-stat-top"><span className={`admin-stat-icon ${stat.tone}`}>{stat.symbol}</span><span>{stat.label}</span></div>
          <strong>{stat.value}</strong><small>{stat.detail}</small>
        </article>)}
      </div>

      <div className="admin-overview-grid">
        <section className="admin-panel category-panel">
          <h2>Issues by Category</h2>
          <ul className="admin-category-list">{categoryOrder.map((category, index) => <li key={category}>
            <span className={`admin-category-icon category-${index}`}>{['☼', '▤', '▲', '◆', '•••'][index]}</span><span>{category}</span><strong>{counts.categories[category] || 0}</strong>
          </li>)}</ul>
        </section>
        <section className="admin-panel activity-panel">
          <div className="admin-panel-heading"><h2>Recent Activity</h2><a href="#recent-issues">View all activity <span aria-hidden="true">→</span></a></div>
          <ol className="activity-list">{activities.map((report) => <li key={report.id}>
            <span className={`activity-marker ${report.status.toLowerCase().replaceAll(' ', '-')}`}>{report.status === 'Resolved' ? '✓' : report.status === 'In progress' ? '+' : '◷'}</span>
            <span className="activity-copy"><strong>{activityDescription(report)}</strong><small>Demo report update</small></span><time>{daysAgo(report.updated)}</time>
          </li>)}</ol>
        </section>
      </div>

      <section className="admin-panel recent-issues" id="recent-issues">
        <div className="admin-panel-heading"><h2>Recent Issues</h2><a href="/my-reports">View all issues <span aria-hidden="true">→</span></a></div>
        <div className="admin-table-wrap"><table className="admin-table">
          <thead><tr><th scope="col">ID</th><th scope="col">Title</th><th scope="col">Category</th><th scope="col">Location</th><th scope="col">Status</th><th scope="col">Reported Date</th><th scope="col">View</th></tr></thead>
          <tbody>{reports.map((report) => <tr key={report.id}>
            <td><a className="admin-reference" href={`/issue-details?id=${report.id}`}>{report.id}</a></td><td>{report.title}</td><td>{report.category}</td><td>{report.location}</td>
            <td><span className={`admin-status ${report.status.toLowerCase().replaceAll(' ', '-')}`}>{formatStatus(report.status)}</span></td><td>{report.date}</td><td><a className="admin-view-link" href={`/issue-details?id=${report.id}`}>View</a></td>
          </tr>)}</tbody>
        </table></div>
        <p className="admin-demo-note">Statistics and activity shown here are calculated from the local demo reports.</p>
      </section>
    </section>
    <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
  </main>
}
