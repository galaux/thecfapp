import * as React from 'react';
import { useRouter } from 'next/router';
import { Button, FormControl, FormLabel, Input, Stack } from '@chakra-ui/core';

import { Body, Header, Layout } from 'components/Layout';

const Signup: React.FC = () => {
  const router = useRouter();
  const onButtonClick = () =>
    router.push({
      pathname: '/',
      query: { is_login: true },
    });

  return (
    <Layout>
      <Header>Signup</Header>

      <Body>
        <Stack spacing={4}>
          <FormControl id='name'>
            <FormLabel>Name</FormLabel>
            <Input bg='white' />
          </FormControl>

          <Button onClick={onButtonClick} colorScheme='blue'>
            Signup
          </Button>
        </Stack>
      </Body>
    </Layout>
  );
};

export default Signup;
