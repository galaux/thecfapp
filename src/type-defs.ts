import { gql } from 'apollo-server'
import { Connection } from 'typeorm'

export default gql`
  type Query {
    """
    Test Message.
    """
    testMessage: String!
  }
`

export type Context = {
  conn: Connection
}
