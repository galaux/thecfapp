import * as React from 'react';
import Link from 'next/link';
import { Button, HStack, Text } from '@chakra-ui/core';
import { Layout } from 'components/Layout/Layout';

const Login: React.FC = () => {
  return (
    <Layout>
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
    </Layout>
  );
};

export default Login;
