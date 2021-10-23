import React from 'react';
import Layout from './components/Layout';
import { communitiesMock } from './mockData/mockData';
import FindCommunity from './pages/FindCommunity';
import FontStyles from './styles/fonts/fontStyles';
import { GlobalStyle } from './styles/theme';

function App() {
  const noop = () => {};
  return (
    <>
      <FontStyles />
      <GlobalStyle />
      <Layout>
        <FindCommunity
          communities={ communitiesMock }
          joinCommunity={ noop }
          findCommunities={ noop }
          navToCommunity={ noop }
        />
      </Layout>
    </>
  );
}

export default App;
