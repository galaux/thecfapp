import React from 'react';
import Layout from './components/Layout';
import LayoutRouter from './components/LayoutRouter';
import FindCommunity from './pages/FindCommunity';
import FontStyles from './styles/fonts/fontStyles';
import { GlobalStyle } from './styles/theme';

function App() {
  return (
    <>
      <FontStyles />
      <GlobalStyle />
      <Layout>
        <LayoutRouter>
          <FindCommunity path="/find-community" />
        </LayoutRouter>
      </Layout>
    </>
  );
}

export default App;
