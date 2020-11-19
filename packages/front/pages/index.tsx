import * as React from 'react';
import { useRouter } from 'next/router';
import { Spinner } from '@chakra-ui/core';

import { Body, Header, Layout } from 'components/Layout';

import { useCurrentUser } from 'contexts/useCurrentUser';

const Home: React.FC = () => {
  const { currentUser } = useCurrentUser();
  const router = useRouter();
  // const { is_login } = router.query;

  React.useEffect(() => {
    if (currentUser) router.push(`/users/${currentUser.id}/communities`);
    else router.push('/choose-user');
    // router.push(is_login ? '/dashboard' : '/login');
  }, []);

  return (
    <Layout>
      <Header />
      <Body>
        <Spinner />
      </Body>
    </Layout>
  );
};

export default Home;
