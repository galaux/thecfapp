import * as React from 'react';
import * as queries from 'api/queries';
import { useQuery } from '@apollo/client';
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

import { ICommunity } from 'types/community';

const Dashboard: React.FC = () => {
  const { data, loading, error } = useQuery<{ communities: ICommunity[] }>(
    queries.getCommunities,
  );

  const [filter, setFilter] = React.useState('');
  const onFilterChange = (e: React.ChangeEvent<HTMLInputElement>) =>
    setFilter(e.target.value);

  const communities = React.useMemo(() => {
    if (!data) return [];
    return data.communities.filter((community) =>
      community.name.toLowerCase().includes(filter.toLowerCase()),
    );
  }, [data, filter]);

  return (
    <Layout>
      <Header>Dashboard</Header>

      <Body>
        <Stack spacing={4}>
          <Text>Dashboard</Text>

          {loading && <Spinner />}
          {error && <ErrorState />}
          {data && (
            <FormControl id='communities-filter'>
              <FormLabel>Search community</FormLabel>
              <Input
                bg='white'
                color='_thunder'
                type='search'
                onChange={onFilterChange}
              />
            </FormControl>
          )}

          {communities.map((community) => (
            <Box key={community.id} bg='_thunder' p={4}>
              <Text mb={2}>{community.name}</Text>
              <Button colorScheme='blue' size='sm'>
                Join
              </Button>
            </Box>
          ))}

          {data && (
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
