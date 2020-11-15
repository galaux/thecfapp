import * as React from 'react';
import { useRouter } from 'next/router';
import { Spinner } from '@chakra-ui/core';

import { Layout } from 'components/Layout/Layout';

const Home: React.FC = () => {
  const router = useRouter();
  const { is_login } = router.query;

  React.useEffect(() => {
    router.push(is_login ? '/dashboard' : '/login');
  }, []);

  return (
    <Layout>
      <Spinner />
    </Layout>
  );
};

export default Home;
