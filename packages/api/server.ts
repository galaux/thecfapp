import { ApolloServer } from 'apollo-server-express'
import { AppConfig } from './app-config'
import { Connection, createConnection } from 'typeorm'
import { Context } from './types'
import { ContextFunction } from 'apollo-server-core'
import { createLoaders } from './loaders'
import resolvers from './resolvers'
import typeDefs from './type-defs'

function contextFn(conn: Promise<Connection>): ContextFunction {
  return async function (): Promise<Context> {
    return <Context>{
      conn: await conn,
      loaders: createLoaders(await conn),
    }
  }
}

function createServer(appConfig: AppConfig, conn?: Connection): ApolloServer {
  return new ApolloServer({
    context: contextFn(
      conn ? Promise.resolve(conn) : createConnection(appConfig.db!),
    ),
    introspection: appConfig.apollo.introspection,
    playground: appConfig.apollo.playground,
    resolvers,
    typeDefs,
  })
}

export { createServer }
