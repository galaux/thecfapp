import { gql } from '@apollo/client';

import getCommunitiesQuery from '../../common/operations/get-communities.graphql';
import getUserByIdQuery from '../../common/operations/get-user.graphql';

export const getCommunities = gql`
  ${getCommunitiesQuery}
`;

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

export const getUserById = gql`
  ${getUserByIdQuery}
`;
