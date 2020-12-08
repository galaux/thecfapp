import { gql } from '@apollo/client'

export const CREATE_SCORE = gql`
  mutation createScore($userId: ID!, $wodId: ID!, $value: Int!) {
    createScore(input: { value: $value, userId: $userId, wodId: $wodId }) {
      id
      user {
        id
      }
      value
      wod {
        id
      }
    }
  }
`
