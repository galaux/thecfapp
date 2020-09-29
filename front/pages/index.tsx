import * as React from 'react';
import * as queries from 'api/queries';
import { useQuery } from '@apollo/client';

import { Layout } from 'components/Layout/Layout';
import { WodSlider } from 'components/WodSlider/WodSlider';

const Home: React.FC = () => {
  const userId = '46cca0ba-e2f0-4e16-971c-11f38fe4e67a';
  const { loading, data } = useQuery(queries.getUserById, {
    variables: {
      id: userId,
    },
  });

  console.log('loading', loading);
  console.log('data', data);

  return (
    <Layout>
      <WodSlider />
    </Layout>
  );
};

export default Home;
