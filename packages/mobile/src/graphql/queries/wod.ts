import { gql } from '@apollo/client'

export const GET_WOD = gql`
  query GetWod($wodId: ID!, $userId: ID!) {
    wod(id: $wodId) {
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
`
