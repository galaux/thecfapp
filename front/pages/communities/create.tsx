import * as React from 'react';
import { useRouter } from 'next/router';
import { Button, FormControl, FormLabel, Input, Stack } from '@chakra-ui/core';

import { Layout } from 'components/Layout/Layout';

const Signup: React.FC = () => {
  const router = useRouter();
  const onButtonClick = () => router.push({ pathname: '/dashboard' });

  return (
    <Layout>
      <Stack spacing={4}>
        <FormControl id='name'>
          <FormLabel>Name</FormLabel>
          <Input bg='white' />
        </FormControl>

        <Button onClick={onButtonClick} colorScheme='blue'>
          Create
        </Button>
      </Stack>
    </Layout>
  );
};

export default Signup;
