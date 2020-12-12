import React from 'react'
import { Body, Header, Layout } from '../Layout'
import { Dashboard } from './Dashboard'
import { RouteComponentProps } from '@reach/router'

const DashboardPage: React.FC<RouteComponentProps> = () => {
  return (
    <Layout>
      <Header>Dashboard</Header>
      <Body>
        <Dashboard />
      </Body>
    </Layout>
  )
}

export default DashboardPage
