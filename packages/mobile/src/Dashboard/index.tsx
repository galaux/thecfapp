import React from 'react'
import { Body, Header, Layout } from '../Layout'
import { Dashboard } from './Dashboard'
import { RouteComponentProps } from '@reach/router'

interface Props extends RouteComponentProps {
  userId: string
}

const DashboardPage: React.FC<Props> = (props: Props) => {
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
