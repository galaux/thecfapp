import * as React from 'react';
import * as queries from 'api/queries';
import { useRouter } from 'next/router';
import { useQuery } from '@apollo/client';
import { Box, Button, Flex, Spinner, Stack, Text } from '@chakra-ui/core';

import { Body, Header, Layout } from 'components/Layout';
import { ErrorState } from 'components/ErrorState/ErrorState';

import { useCurrentUser } from 'contexts/useCurrentUser';
import { IUser } from 'types/user';

const Dashboard: React.FC = () => {
  const router = useRouter();
  const { setCurrentUser } = useCurrentUser();
  const { data, loading, error } = useQuery<{ users: IUser[] }>(
    queries.getUsers,
  );

  const onUserClick = (user: IUser) => () => {
    setCurrentUser(user);
    router.push('/');
  };

  return (
    <Layout>
      <Header>Choose your player</Header>

      <Body>
        <Stack spacing={4}>
          {loading && <Spinner />}
          {error && <ErrorState />}
          {data && (
            <>
              {data.users.map((user) => (
                <Flex key={user.id} alignItems='center' bg='_thunder' p={4}>
                  <Box mr={4} flexGrow={1}>
                    <Text w='100%' mb={1}>
                      {user.name}
                    </Text>
                    <Text w='100%' fontSize='sm'>
                      ({user.communities.length}) communities
                    </Text>
                  </Box>

                  <Button colorScheme='blue' onClick={onUserClick(user)}>
                    Choose
                  </Button>
                </Flex>
              ))}
            </>
          )}
        </Stack>
      </Body>
    </Layout>
  );
};

export default Dashboard;
