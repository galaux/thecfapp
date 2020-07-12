import 'module-alias/register'
import 'reflect-metadata'
import { ApolloServer } from 'apollo-server'
import { Connection, createConnection } from 'typeorm'
import { ContextFunction } from 'apollo-server-core'
import { PostgresConnectionOptions } from 'typeorm/driver/postgres/PostgresConnectionOptions'

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

server
  .listen(appConfig.port)
  .then(({ url }) => console.log(`Server ready at ${url}.`))
