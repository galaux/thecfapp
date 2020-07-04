import { ApolloServer } from 'apollo-server'

import resolvers from './resolvers'
import typeDefs from './type-defs'
import { appConfig } from './app-config'

const server = new ApolloServer({
  introspection: appConfig.apollo.introspection,
  playground: appConfig.apollo.playground,
  resolvers,
  typeDefs,
})

server
  .listen(appConfig.port)
  .then(({ url }) => console.log(`Server ready at ${url}.`))
