import { Body } from '../Body'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { Text } from '@chakra-ui/react'
import * as React from 'react'

export const DashboardPanel: React.FC = () => {
  return (
    <Layout>
      <Header>Dashboard</Header>
      <Body>
        <Text>This is the Dashboard TODO</Text>
      </Body>
    </Layout>
  )
}
