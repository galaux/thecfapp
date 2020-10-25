import { gql } from '@apollo/client';

import getCommunitiesQuery from '../../common/operations/get-communities.graphql';
import getUsersQuery from '../../common/operations/get-users.graphql';
import getUserByIdQuery from '../../common/operations/get-user.graphql';

export const getCommunities = gql`
  ${getCommunitiesQuery}
`;

export const getUsers = gql`
  ${getUsersQuery}
`;

export const getUserById = gql`
  ${getUserByIdQuery}
`;
