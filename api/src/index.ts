import 'module-alias/register'
import 'reflect-metadata'

import { ApolloServer } from 'apollo-server-express'
import { Connection, createConnection } from 'typeorm'
import { ContextFunction } from 'apollo-server-core'
import { PostgresConnectionOptions } from 'typeorm/driver/postgres/PostgresConnectionOptions'
import express from 'express'
import { express as voyagerMiddleware } from 'graphql-voyager/middleware'

import { Context } from './types'
import { ORMUserRecord, ORMUserRecordType } from './core/user-record'
import { appConfig, DBConfig } from './app-config'
import resolvers from './resolvers'
import typeDefs from './type-defs'

function createDBConn(dbConfig: DBConfig): Promise<Connection> {
  return createConnection(<PostgresConnectionOptions>{
    type: 'postgres',
    ...dbConfig,
    entities: [ORMUserRecord, ORMUserRecordType],
  })
}

function contextFn(conn: Promise<Connection>): ContextFunction {
  return async function (): Promise<Context> {
    return <Context>{
      conn: await conn,
    }
  }
}

const server = new ApolloServer({
  context: contextFn(createDBConn(appConfig.db)),
  introspection: appConfig.apollo.introspection,
  playground: appConfig.apollo.playground,
  resolvers,
  typeDefs,
})

const app = express()

server.applyMiddleware({ app })
app.use('/voyager', voyagerMiddleware({ endpointUrl: '/graphql' }))
if (appConfig.serveFrontEnd) {
  app.use(express.static('../front/build'))
}

app.listen({ port: appConfig.port }, () =>
  console.log(
    `🚀 Server ready at http://localhost:${appConfig.port}${server.graphqlPath}`,
  ),
)
