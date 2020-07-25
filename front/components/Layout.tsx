import * as React from 'react';
import Head from 'next/head';

const Layout: React.FC = ({ children }) => (
  <>
    <Head>
      <link rel='icon' href='/favicon.ico' />
      <title>Next App</title>
    </Head>

    <main>{children}</main>
  </>
);

export default Layout;
