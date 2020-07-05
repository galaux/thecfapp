import 'reflect-metadata'
import { ApolloServer } from 'apollo-server'
import { Connection, createConnection } from 'typeorm'
import { ContextFunction } from 'apollo-server-core'
import { PostgresConnectionOptions } from 'typeorm/driver/postgres/PostgresConnectionOptions'

import { Context } from './types'
import { ORMUserRecord, ORMUserRecordType } from './core/user-record'
import { appConfig } from './app-config'
import resolvers from './resolvers'
import typeDefs from './type-defs'

function createDBConn(): Promise<Connection> {
  return createConnection(<PostgresConnectionOptions>{
    type: 'postgres',
    host: 'localhost',
    port: 5432,
    username: 'postgres',
    password: '',
    database: 'crossfit',
    synchronize: true,
    logging: true,
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
  context: contextFn(createDBConn()),
  introspection: appConfig.apollo.introspection,
  playground: appConfig.apollo.playground,
  resolvers,
  typeDefs,
})

server
  .listen(appConfig.port)
  .then(({ url }) => console.log(`Server ready at ${url}.`))
