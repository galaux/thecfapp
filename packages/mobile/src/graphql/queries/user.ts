import { gql } from '@apollo/client'

export const GET_USER = gql`
  query GetUser($userId: ID!) {
    user(id: $userId) {
      id
      name
      communities {
        id
        name
        wods {
          id
          community {
            id
            name
          }
          date
          scores(userId: $userId) {
            id
            value
          }
          workout {
            id
            name
            description
            scoringType
          }
        }
      }
    }
  }
`
