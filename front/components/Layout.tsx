import * as React from 'react';
import Head from 'next/head';

const Layout: React.FC = ({ children }) => (
  <div>
    <Head>
      <link rel='icon' href='/favicon.ico' />
      <title>Next App</title>
    </Head>

    <main>{children}</main>
  </div>
);

export default Layout;
