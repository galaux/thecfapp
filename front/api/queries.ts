import { gql } from '@apollo/client';

import getUsersQuery from '../../common/operations/get-users.graphql';
import getUserByIdQuery from '../../common/operations/get-user.graphql';

export const getUsers = gql`
  ${getUsersQuery}
`;

export const getUserById = gql`
  ${getUserByIdQuery}
`;
