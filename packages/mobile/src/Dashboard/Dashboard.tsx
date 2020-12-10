import * as React from 'react'
import { Link } from '@reach/router'

export const Dashboard: React.FC = () => {
  return (
    <nav>
      <Link to='/wod-list'>My WoD List</Link>
      <br />
      <Link to='/user-profile'>My Profile</Link>
    </nav>
  )
}
