import { gql } from '@apollo/client';

import getUsersQuery from '../../common/operations/get-users.graphql';

export const getUsers = gql`
  ${getUsersQuery}
`;
