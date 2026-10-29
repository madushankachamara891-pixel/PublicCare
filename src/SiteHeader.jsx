import { useEffect, useRef, useState } from 'react'

const DEMO_AUTH_STORAGE_KEY = 'publiccareDemoSignedIn'

function AccountMenuIcon({ name }) {
  const shared = { fill: 'none', stroke: 'currentColor', strokeWidth: 1.7, strokeLinecap: 'round', strokeLinejoin: 'round' }
  if (name === 'user') return <svg viewBox="0 0 24 24" aria-hidden="true" {...shared}><circle cx="12" cy="8" r="3.2"/><path d="M5.5 20c.7-3.4 3-5.2 6.5-5.2s5.8 1.8 6.5 5.2"/></svg>
  if (name === 'reports') return <svg viewBox="0 0 24 24" aria-hidden="true" {...shared}><path d="M7 3.75h7l4 4V20H7z"/><path d="M14 3.75v4h4M9.5 12h6M9.5 15.5h6"/></svg>
  if (name === 'login') return <svg viewBox="0 0 24 24" aria-hidden="true" {...shared}><path d="M13.5 4.5h5v15h-5M3.5 12h10M9.5 8l4 4-4 4"/></svg>
  return <svg viewBox="0 0 24 24" aria-hidden="true" {...shared}><path d="M10.5 4.5h-5v15h5M20.5 12h-10M14.5 8l-4 4 4 4"/></svg>
}

export default function SiteHeader({ active = '', reportsView = false, adminView = false, trackReportLabel = false }) {
  const [accountMenuOpen, setAccountMenuOpen] = useState(false)
  const [isDemoSignedIn, setIsDemoSignedIn] = useState(() => window.localStorage.getItem(DEMO_AUTH_STORAGE_KEY) === 'true')
  const accountMenuRef = useRef(null)
  const accountButtonRef = useRef(null)

  useEffect(() => {
    if (!accountMenuOpen) return undefined

    function closeOnOutsidePointer(event) {
      if (!accountMenuRef.current?.contains(event.target)) setAccountMenuOpen(false)
    }
    function closeOnEscape(event) {
      if (event.key === 'Escape') {
        setAccountMenuOpen(false)
        accountButtonRef.current?.focus()
      }
    }

    document.addEventListener('pointerdown', closeOnOutsidePointer)
    document.addEventListener('keydown', closeOnEscape)
    return () => {
      document.removeEventListener('pointerdown', closeOnOutsidePointer)
      document.removeEventListener('keydown', closeOnEscape)
    }
  }, [accountMenuOpen])

  useEffect(() => {
    function syncDemoAuth(event) {
      if (event.key === DEMO_AUTH_STORAGE_KEY) setIsDemoSignedIn(event.newValue === 'true')
    }
    window.addEventListener('storage', syncDemoAuth)
    return () => window.removeEventListener('storage', syncDemoAuth)
  }, [])

  function handleLogout() {
    window.localStorage.removeItem(DEMO_AUTH_STORAGE_KEY)
    setIsDemoSignedIn(false)
    setAccountMenuOpen(false)
  }

  function handleAccountMenuKeyDown(event) {
    if (!accountMenuOpen) return
    const menuItems = accountMenuRef.current?.querySelectorAll('[role="menuitem"]')
    if (!menuItems?.length) return
    const currentIndex = Array.from(menuItems).indexOf(document.activeElement)
    if (event.key === 'ArrowDown' || event.key === 'ArrowUp') {
      event.preventDefault()
      const direction = event.key === 'ArrowDown' ? 1 : -1
      const nextIndex = (currentIndex + direction + menuItems.length) % menuItems.length
      menuItems[nextIndex].focus()
    } else if (event.key === 'Home' || event.key === 'End') {
      event.preventDefault()
      menuItems[event.key === 'Home' ? 0 : menuItems.length - 1].focus()
    }
  }

  function handleAccountButtonKeyDown(event) {
    if (event.key !== 'ArrowDown' && event.key !== 'ArrowUp') return
    event.preventDefault()
    const openingKey = event.key
    setAccountMenuOpen(true)
    requestAnimationFrame(() => {
      const items = accountMenuRef.current?.querySelectorAll('[role="menuitem"]')
      if (!items?.length) return
      items[openingKey === 'ArrowDown' ? 0 : items.length - 1].focus()
    })
  }

  const accountMenuId = adminView ? 'shared-admin-menu' : 'shared-account-menu'

  return <header className={`topbar${adminView ? ' admin-topbar' : ''}`}>
    <a className="brand" href="/" aria-label="PublicCare home"><span className="brand-mark">P</span><span className="brand-copy"><strong>PublicCare</strong><small>A better way to report local issues</small></span></a>
    <nav className="navigation" aria-label="Main navigation">
      <a className={active === 'home' ? 'active' : ''} href="/">Home</a><a href="/#process">How it works</a><a href="/#categories">Issue categories</a><a className={active === 'reports' ? 'active' : ''} href="/my-reports">{reportsView || trackReportLabel ? 'Track a report' : 'My Reports'}</a>
    </nav>
    <div className="nav-actions">{adminView ? <div className="account-menu-wrap" ref={accountMenuRef} onKeyDown={handleAccountMenuKeyDown} onBlur={(event) => { if (!event.currentTarget.contains(event.relatedTarget)) setAccountMenuOpen(false) }}>
      <button ref={accountButtonRef} className="login account-link" type="button" aria-haspopup="menu" aria-expanded={accountMenuOpen} aria-controls={accountMenuId} onClick={() => setAccountMenuOpen((open) => !open)} onKeyDown={handleAccountButtonKeyDown}>
        Admin<span className={`account-chevron${accountMenuOpen ? ' is-open' : ''}`} aria-hidden="true" />
      </button>
      <div className="account-dropdown" id={accountMenuId} role="menu" aria-label="Admin navigation" hidden={!accountMenuOpen} onClick={(event) => { if (event.target.closest('[role="menuitem"]')) setAccountMenuOpen(false) }}>
        <div className="account-dropdown-heading" role="presentation"><AccountMenuIcon name="user"/><span><strong>Admin</strong><small>Administration</small></span><i className={`account-chevron${accountMenuOpen ? ' is-open' : ''}`} aria-hidden="true"/></div>
        <a className="account-menu-item" role="menuitem" href="/admin">Dashboard</a>
        <a className="account-menu-item" role="menuitem" href="/admin/issues">Issue management</a>
        <a className="account-menu-item" role="menuitem" href="/admin/update-issue">Update issue</a>
      </div>
      <a className="get-started" href="/" onClick={handleLogout}>Log out</a>
    </div> : reportsView ? <div className="account-menu-wrap" ref={accountMenuRef} onKeyDown={handleAccountMenuKeyDown} onBlur={(event) => { if (!event.currentTarget.contains(event.relatedTarget)) setAccountMenuOpen(false) }}>
      <button ref={accountButtonRef} className="login account-link" type="button" aria-label="My account" aria-haspopup="menu" aria-expanded={accountMenuOpen} aria-controls={accountMenuId} onClick={() => setAccountMenuOpen((open) => !open)} onKeyDown={handleAccountButtonKeyDown}>
        My account<span className={`account-chevron${accountMenuOpen ? ' is-open' : ''}`} aria-hidden="true" />
      </button>
      <div className="account-dropdown" id={accountMenuId} role="menu" aria-label="My account" hidden={!accountMenuOpen} onClick={(event) => { if (event.target.closest('a[role="menuitem"]')) setAccountMenuOpen(false) }}>
        <div className="account-dropdown-heading" role="presentation"><AccountMenuIcon name="user"/><span><strong>My account</strong><small>Account menu</small></span><i className="account-chevron is-open" aria-hidden="true"/></div>
        <div className="account-menu-item unavailable" role="menuitem" aria-disabled="true" tabIndex={0}><AccountMenuIcon name="user"/><span>My profile</span></div>
        <a className="account-menu-item" role="menuitem" href="/my-reports"><AccountMenuIcon name="reports"/><span>My Reports</span></a>
        <div className="account-menu-divider" role="separator" />
        {isDemoSignedIn
          ? <button className="account-menu-item" role="menuitem" type="button" onClick={handleLogout}><AccountMenuIcon name="logout"/><span>Log out</span></button>
          : <a className="account-menu-item" role="menuitem" href="/login"><AccountMenuIcon name="login"/><span>Log in</span></a>}
      </div>
    </div> : <><a className="login" href="/login">Log in</a><a className="get-started" href="/create-issue">Get started</a></>}</div>
  </header>
}
