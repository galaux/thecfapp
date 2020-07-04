import 'reflect-metadata'
import { ApolloServer } from 'apollo-server'

import { appConfig } from './app-config'
import resolvers from './resolvers'
import typeDefs, { Context } from './type-defs'
import { createConnection, Connection } from 'typeorm'
import { UserRecord, UserRecordType } from './core/user'
import { PostgresConnectionOptions } from 'typeorm/driver/postgres/PostgresConnectionOptions'
import { ContextFunction } from 'apollo-server-core'

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
    entities: [UserRecord, UserRecordType],
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
