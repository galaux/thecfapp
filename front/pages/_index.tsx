import * as React from 'react';
import * as queries from 'api/queries';
import Head from 'next/head';
import { Box, Flex, Heading, Select, Spinner } from '@chakra-ui/core';

import { ErrorState } from 'components/ErrorState/ErrorState';
import { Layout } from 'components/Layout/Layout';
import { Topbar } from 'components/Layout/Topbar';
import { UserStats } from 'components/UserStats/UserStats';
import { WodSlider } from 'components/WodSlider/WodSlider';

import { useQuery } from '@apollo/client';
import { IUser } from 'types/user';

const Home: React.FC = () => {
  const { data, loading, error } = useQuery<{ user: IUser }>(
    queries.getUserById,
    {
      variables: { id: '46cca0ba-e2f0-4e16-971c-11f38fe4e67a' },
    },
  );

  return (
    <Layout>
      <Head>
        <link rel='icon' href='/favicon.ico' />
        <title>THECFAPP</title>
      </Head>

      <Topbar />
      <UserStats />

      <Box p={6}>
        <Flex alignItems='center' mb={4}>
          <Heading fontSize='lg' flex={1}>
            WOD
          </Heading>

          {loading && <Spinner size='sm' />}
          {error && <ErrorState />}
          {data && (
            <Select w='auto' border={0} color='_gray' fontSize='md'>
              {data.user.communities?.map((community) => (
                <option key={community.id}>{community.name}</option>
              ))}
            </Select>
          )}
        </Flex>

        {loading && <Spinner size='sm' />}
        {error && <ErrorState />}
        {data && (
          <Box mx={-6}>
            <WodSlider />
          </Box>
        )}
      </Box>
    </Layout>
  );
};

export default Home;
