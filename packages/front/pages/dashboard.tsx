import * as React from 'react';
import * as queries from 'api/queries';
import { useRouter } from 'next/router';
import { useLazyQuery } from '@apollo/client';
import Link from 'next/link';
import {
  Box,
  Button,
  FormControl,
  FormLabel,
  Input,
  Spinner,
  Stack,
  Text,
} from '@chakra-ui/core';

import { Body, Header, Layout } from 'components/Layout';
import { ErrorState } from 'components/ErrorState/ErrorState';

import { useCurrentUser } from 'contexts/useCurrentUser';
import { IUser } from 'types/user';

const Dashboard: React.FC = () => {
  const router = useRouter();
  const { currentUser } = useCurrentUser();

  const [getCommunities, { data, error }] = useLazyQuery<{ user: IUser }>(
    queries.getUserCommunities,
  );

  const [filter, setFilter] = React.useState('');
  const onFilterChange = (e: React.ChangeEvent<HTMLInputElement>) =>
    setFilter(e.target.value);

  const communities = React.useMemo(() => {
    if (!data) return [];
    return data.user.communities.filter((community) =>
      community.name.toLowerCase().includes(filter.toLowerCase()),
    );
  }, [data, filter]);

  React.useEffect(() => {
    if (!currentUser) {
      router.push('/');
      return;
    }

    getCommunities({
      variables: {
        id: currentUser.id,
      },
    });
  }, []);

  return (
    <Layout>
      <Header>Dashboard</Header>

      <Body>
        <Stack spacing={4}>
          {error && <ErrorState />}

          {data ? (
            <FormControl id='communities-filter'>
              <FormLabel>Search community</FormLabel>
              <Input
                bg='white'
                color='_thunder'
                type='search'
                onChange={onFilterChange}
              />
            </FormControl>
          ) : (
            <Spinner />
          )}

          {communities.map((community) => (
            <Box key={community.id} bg='_thunder' p={4}>
              <Text mb={2}>{community.name}</Text>
              <Button colorScheme='blue' size='sm'>
                Join
              </Button>
            </Box>
          ))}

          {data && filter !== '' && (
            <Link href='/communities/create'>
              <a>
                <Button colorScheme='blue'>Create box</Button>
              </a>
            </Link>
          )}
        </Stack>
      </Body>
    </Layout>
  );
};

export default Dashboard;
