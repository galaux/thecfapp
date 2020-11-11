import * as React from 'react';
import Link from 'next/link';
import { Button, HStack, Text } from '@chakra-ui/core';

import { Body, Header, Layout } from 'components/Layout';

const Login: React.FC = () => {
  return (
    <Layout>
      <Header>Login</Header>

      <Body>
        <HStack spacing={2}>
          <Link
            href={{
              pathname: '/',
              query: { is_login: true },
            }}
          >
            <a>
              <Button colorScheme='blue'>Login</Button>
            </a>
          </Link>

          <Text>or</Text>

          <Link href='/signup'>
            <a>sign-up</a>
          </Link>
        </HStack>
      </Body>
    </Layout>
  );
};

export default Login;
