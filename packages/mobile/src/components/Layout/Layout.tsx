import { COPYRIGHT_STR } from '../../utils'
import { Grid, Text, Heading } from '@chakra-ui/react'
import * as React from 'react'

const templateAreas = `
  'Header'
  'Body'
  'Footer'
`

export const Layout: React.FC = ({ children }) => (
  <>
    {/* FIXME what exactly is Next's Head component? Do we want sthing similar?
        https://nextjs.org/docs/api-reference/next/head */}
    <Heading>
      <link rel='icon' href='/favicon.ico' />
      <title>THECFAPP</title>
    </Heading>

    <Grid
      h='100vh'
      templateAreas={templateAreas}
      templateColumns='1fr'
      templateRows='auto 1fr auto'
      bg='_shark'
      color='white'
    >
      {children}

      <Text gridArea='Footer' fontSize='xs' color='_gray' px={6} py={3}>
        {COPYRIGHT_STR}
      </Text>
    </Grid>
  </>
)
