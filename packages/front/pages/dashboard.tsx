import * as React from 'react';
import * as queries from 'api/queries';
import { useRouter } from 'next/router';
import { useLazyQuery } from '@apollo/client';
import { Box, Heading, Flex, Select, Spinner } from '@chakra-ui/core';

import { Body, Header, Layout } from 'components/Layout';
import { ErrorState } from 'components/ErrorState/ErrorState';
import { WodSlider } from 'components/WodSlider/WodSlider';

import { useCurrentUser } from 'contexts/useCurrentUser';
import { IUser } from 'types/user';

const Dashboard: React.FC = () => {
  const router = useRouter();
  const { currentUser } = useCurrentUser();
  const [currentCommunityId, setCurrentCommunityId] = React.useState('');

  const [getWods, { data, loading, error }] = useLazyQuery<{
    user: IUser;
  }>(queries.getUserWods);

  const onCommunitySelectChange = (e: React.ChangeEvent<HTMLSelectElement>) =>
    setCurrentCommunityId(e.target.value);

  const currentCommunity =
    data && data.user.communities.find((c) => c.id === currentCommunityId);

  React.useEffect(() => {
    if (!currentUser) {
      router.push('/');
      return;
    }

    getWods({
      variables: { id: currentUser.id },
    });
  }, []);

  React.useEffect(() => {
    if (data) setCurrentCommunityId(data.user.communities[0].id);
  }, [data]);

  return (
    <Layout>
      <Header>Dashboard</Header>

      <Body>
        {error && <ErrorState />}
        {loading && <Spinner />}

        {data && (
          <Flex alignItems='center' mb={4}>
            <Heading fontSize='lg' flex={1}>
              WOD
            </Heading>

            <Select
              w='auto'
              border={0}
              color='_gray'
              fontSize='md'
              onChange={onCommunitySelectChange}
            >
              {data.user.communities?.map((community) => (
                <option key={community.id} value={community.id}>
                  {community.name}
                </option>
              ))}
            </Select>
          </Flex>
        )}

        {currentCommunity && (
          <Box mx={-6}>
            <WodSlider community={currentCommunity} />
          </Box>
        )}
      </Body>
    </Layout>
  );
};

export default Dashboard;
