import { gql } from '@apollo/client';

export const getUsers = gql`
  query getUsers {
    users {
      id
      name
      communities {
        id
        name
      }
    }
  }
`;

export const getUserCommunities = gql`
  query getUserCommunities($id: ID!) {
    user(id: $id) {
      id
      communities {
        id
        name
      }
    }
  }
`;

export const getUserWods = gql`
  query getUserCommunities($id: ID!) {
    user(id: $id) {
      id
      communities {
        id
        name
        wods {
          id
          workout {
            id
            name
            description
          }
        }
      }
    }
  }
`;

export const getWod = gql`
  query getWod($id: ID!) {
    wod(id: $id) {
      id
      date
      workout {
        description
        name
      }
    }
  }
`;
