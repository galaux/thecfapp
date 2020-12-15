import * as React from 'react'
import {
  Button,
  Flex,
  VStack,
  FormControl,
  FormLabel,
  Input,
} from '@chakra-ui/react'
import { NewCommunityInput } from '../types'

interface Props {
  createCommunity: (input: NewCommunityInput) => void
}

export const NewCommunity: React.FC<Props> = (props: Props) => {
  const { createCommunity } = props
  const [name, setName] = React.useState<string | null>()
  const [city, setCity] = React.useState<string | null>()
  const [country, setCountry] = React.useState<string | null>()
  const [street, setStreet] = React.useState<string | null>()
  const [zipCode, setZipCode] = React.useState<string | null>()

  function validName(s?: string | null): boolean {
    return Boolean(s && s?.trim().length >= 5)
  }

  function onClickCreate() {
    if (name && validName(name)) {
      createCommunity({
        name: name.trim(),
        city: city?.trim() || undefined,
        country: country?.trim() || undefined,
        street: street?.trim() || undefined,
        zipCode: zipCode?.trim() || undefined,
      })
    }
  }

  return (
    <VStack p='2%'>
      <FormControl id='name' isRequired>
        <FormLabel color='_gray'>Community name</FormLabel>
        <Input onChange={(e) => setName(e.target.value)} />
      </FormControl>
      <FormControl pt='5%' id='street'>
        <FormLabel color='_gray'>Street and number</FormLabel>
        <Input onChange={(e) => setStreet(e.target.value)} />
      </FormControl>
      <Flex>
        <FormControl width='25%' id='zipcode'>
          <FormLabel color='_gray'>zipcode</FormLabel>
          <Input onChange={(e) => setZipCode(e.target.value)} />
        </FormControl>
        <FormControl pl='5%' width='75%' id='city'>
          <FormLabel color='_gray'>city</FormLabel>
          <Input onChange={(e) => setCity(e.target.value)} />
        </FormControl>
      </Flex>
      <FormControl id='country'>
        <FormLabel color='_gray'>country</FormLabel>
        <Input onChange={(e) => setCountry(e.target.value)} />
      </FormControl>
      <Button
        colorScheme='blue'
        disabled={!validName(name)}
        onClick={onClickCreate}
      >
        Create
      </Button>
    </VStack>
  )
}
