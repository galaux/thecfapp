import * as React from 'react';
import * as queries from 'api/queries';
import { useQuery } from '@apollo/client';

import { Layout } from '../components/Layout/Layout';
import { WodSlider } from '../components/WodSlider/WodSlider';

const Home: React.FC = () => {
  const { loading, data } = useQuery(queries.getUsers);

  console.log('loading', loading);
  console.log('data', data);

  return (
    <Layout>
      <WodSlider />
    </Layout>
  );
};

export default Home;
