import * as React from 'react';
import * as queries from 'api/queries';
import { useRouter } from 'next/router';
import { useLazyQuery } from '@apollo/client';
import {
  Box,
  Button,
  Divider,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Spinner,
  Switch,
  Text,
  VStack,
} from '@chakra-ui/core';

import { Body, Header, Layout } from 'components/Layout';
import { ErrorState } from 'components/ErrorState/ErrorState';

import { useCurrentUser } from 'contexts/useCurrentUser';
import { IWod } from 'types/wod';

const AddScore: React.FC = () => {
  const router = useRouter();
  const { currentUser } = useCurrentUser();

  const [getWod, { data, loading, error }] = useLazyQuery<{ wod: IWod }>(
    queries.getWod,
  );

  React.useEffect(() => {
    const { wod_id } = router.query;

    if (!currentUser) {
      router.push('/');
      return;
    }

    getWod({ variables: { id: wod_id } });
  }, []);

  return (
    <Layout>
      <Header>
        AddScore
        <Box mt={2}>
          {loading && <Spinner />}
          {data && (
            <Text>
              Workout Of The Day
              <br /> {data.wod.workout.name}
            </Text>
          )}
        </Box>
      </Header>

      <Body>
        {error && <ErrorState />}
        {loading && <Spinner />}

        {data && (
          <>
            <Text>{data.wod.workout.description}</Text>

            <Heading fontSize='lg' my={6}>
              Add my score
            </Heading>

            <Box bg='_thunder' rounded='md' p={4}>
              <VStack spacing={4}>
                <FormControl display='flex' alignItems='center'>
                  <FormLabel htmlFor='rx' m={0}>
                    Scaled
                  </FormLabel>
                  <Switch id='rx' mx={2} />
                  <FormLabel htmlFor='rx' m={0}>
                    RX
                  </FormLabel>
                </FormControl>

                <HStack spacing={2}>
                  <FormControl id='weight_1'>
                    <FormLabel>Weight 1</FormLabel>
                    <Input bg='_white' />
                  </FormControl>
                  <FormControl id='weight_2'>
                    <FormLabel>Weight 2</FormLabel>
                    <Input bg='_white' />
                  </FormControl>
                  <FormControl id='weight_3'>
                    <FormLabel>Weight 3</FormLabel>
                    <Input bg='_white' />
                  </FormControl>
                </HStack>

                <Divider />

                <HStack spacing={2}>
                  <FormControl id='min'>
                    <FormLabel>Min</FormLabel>
                    <Input bg='_white' />
                  </FormControl>
                  <FormControl id='sec'>
                    <FormLabel>Sec</FormLabel>
                    <Input bg='_white' />
                  </FormControl>
                  <FormControl id='reps'>
                    <FormLabel>Reps</FormLabel>
                    <Input bg='_white' />
                  </FormControl>
                </HStack>

                <Box w='100%'>
                  <Button colorScheme='blue'>Add my score</Button>
                </Box>
              </VStack>
            </Box>
          </>
        )}
      </Body>
    </Layout>
  );
};

export default AddScore;
