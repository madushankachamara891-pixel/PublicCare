import './App.css'
import CreateIssue from './CreateIssue.jsx'
import MyReports from './MyReports.jsx'
import IssueDetails from './IssueDetails.jsx'
import EditIssue from './EditIssue.jsx'
import AccountPage from './AccountPage.jsx'
import AdminDashboard from './AdminDashboard.jsx'
import AdminIssues from './AdminIssues.jsx'
import AdminUpdateIssue from './AdminUpdateIssue.jsx'

const stats = [
  { value: '1,240+', label: 'Issues reported', tone: 'red' },
  { value: '860', label: 'Issues resolved', tone: 'green' },
  { value: '24', label: 'Local areas represented' },
  { value: 'Transparent', label: 'Updates you can follow' },
]

const categories = [
  { initials: 'RD', title: 'Road damage', description: 'Potholes and damaged roads', tone: 'blue' },
  { initials: 'WG', title: 'Waste & garbage', description: 'Missed collections and litter', tone: 'mint' },
  { initials: 'SL', title: 'Streetlights', description: 'Lights that are broken or off', tone: 'amber' },
  { initials: 'DR', title: 'Drainage', description: 'Blocked drains and flooding', tone: 'purple' },
]

const steps = [
  { number: '01', title: 'Report the issue', description: 'Tell us what happened and where.' },
  { number: '02', title: 'Follow progress', description: 'Check status updates at any time.' },
  { number: '03', title: 'See the outcome', description: 'Know when the issue is resolved.' },
]

function App() {
  const path = window.location.pathname
  if (path === '/admin') return <AdminDashboard />
  if (path === '/admin/issues') return <AdminIssues />
  if (path === '/admin/update-issue') return <AdminUpdateIssue />
  if (path === '/create-issue') return <CreateIssue />
  if (path === '/my-reports') return <MyReports />
  if (path === '/issue-details') return <IssueDetails />
  if (path === '/edit-issue') return <EditIssue />
  if (path === '/login' || path === '/register') return <AccountPage mode={path.slice(1)} />

  return (
    <main className="site-shell">
      <header className="topbar">
        <a className="brand" href="/" aria-label="PublicCare home">
          <span className="brand-mark">P</span>
          <span className="brand-copy"><strong>PublicCare</strong><small>A better way to report local issues</small></span>
        </a>
        <nav className="navigation" aria-label="Main navigation">
          <a className="active" href="/">Home</a>
          <a href="#process">How it works</a>
          <a href="#categories">Issue categories</a>
          <a href="/my-reports">My Reports</a>
        </nav>
        <div className="nav-actions"><a className="login" href="/login">Log in</a><a className="get-started" href="/create-issue">Get started</a></div>
      </header>

      <section className="hero-section" id="home">
        <div className="hero-content">
          <div className="hero-copy">
            <span className="eyebrow-pill">CITIZEN ISSUE REPORTING</span>
            <h1>Let's make our<br className="desktop-break" /> communities better.</h1>
            <p className="hero-lead">Report public issues in your area and follow every update.</p>
            <p className="hero-sub">Together, we can help build cleaner, safer places across Sri Lanka.</p>
            <div className="hero-actions"><a className="button primary" href="/create-issue"><span>＋</span> Report an issue</a><a className="button secondary" href="/my-reports">Track a report <span>→</span></a></div>
            <p className="hero-note">Free to use&nbsp; • &nbsp;Simple&nbsp; • &nbsp;Transparent</p>
          </div>
          <div className="overview-card">
            <div className="overview-heading"><div><h2>Your community, in focus</h2><p>A sample of local reports</p></div><span className="live-badge">LIVE OVERVIEW</span></div>
            <div className="overview-content">
              <div className="map-illustration" aria-label="Illustration of a community map">
                <i className="map-block block-one" /><i className="map-block block-two" />
                <i className="road road-horizontal road-top" /><i className="road road-horizontal road-bottom" />
                <i className="road road-vertical road-left" /><i className="road road-vertical road-right" />
                <span className="map-pin pin-green">!</span><span className="map-pin pin-amber">!</span><span className="map-pin pin-navy">!</span>
              </div>
              <article className="issue-preview"><span className="status-badge">IN PROGRESS</span><h3>Streetlight not<br /> working</h3><p>Galle • Report #PC-1024</p></article>
            </div>
          </div>
        </div>
      </section>

      <section className="stats-section" aria-label="PublicCare sample statistics">
        <div className="stats-card">{stats.map((stat) => <div className="stat" key={stat.label}><strong className={stat.tone || ''}>{stat.value}</strong><span>{stat.label}</span></div>)}</div>
        <p className="sample-note">Sample statistics for display</p>
      </section>

      <section className="categories-section" id="categories">
        <p className="section-kicker">REPORT A PUBLIC ISSUE</p>
        <h2>What needs attention?</h2>
        <p className="section-description">Choose a category to help us understand the problem.</p>
        <div className="category-grid">{categories.map((category) => <a className="category-card" href="/create-issue" key={category.title}>
          <span className={`category-icon ${category.tone}`}>{category.initials}</span><strong>{category.title}</strong>
          <span className="category-description">{category.description}</span><span className="category-arrow">↗</span>
        </a>)}</div>
        <p className="sample-note category-note">Example issue categories</p>
      </section>

      <section className="process-section" id="process">
        <div className="process-intro"><p className="section-kicker">SIMPLE AND TRANSPARENT</p><h2>From report to resolution</h2></div>
        <div className="steps-grid">{steps.map((step) => <article className="step" key={step.number}><span className="step-number">{step.number}</span><h3>{step.title}</h3><p>{step.description}</p></article>)}</div>
      </section>

      <footer className="footer"><strong>PublicCare</strong><span>A clearer path from reporting to resolution.</span></footer>
    </main>
  )
}

export default App
