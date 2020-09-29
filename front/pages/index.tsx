import * as React from 'react';
import * as queries from 'api/queries';
import Head from 'next/head';
import { Box, Flex, Heading, Select, Spinner } from '@chakra-ui/core';

import { Layout } from 'components/Layout/Layout';
import { Topbar } from 'components/Topbar/Topbar';
import { UserStats } from 'components/UserStats/UserStats';
import { WodSlider } from 'components/WodSlider/WodSlider';

import { useQuery } from '@apollo/client';

const Home: React.FC = () => {
  const userId = '46cca0ba-e2f0-4e16-971c-11f38fe4e67a';
  const { data } = useQuery(queries.getUserById, {
    variables: {
      id: userId,
    },
  });

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

          {data ? (
            <Select w='auto' border={0} color='_gray' fontSize='md'>
              {data.user.communities?.map((community) => (
                <option key={community.id}>{community.name}</option>
              ))}
            </Select>
          ) : (
            <Spinner size='sm' />
          )}
        </Flex>

        {data ? (
          <Box mx={-6}>
            <WodSlider />
          </Box>
        ) : (
          <Spinner />
        )}
      </Box>
    </Layout>
  );
};

export default Home;
