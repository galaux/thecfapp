import 'reflect-metadata'

import { ApolloServer } from 'apollo-server-express'
import { Connection, createConnection } from 'typeorm'
import { ContextFunction } from 'apollo-server-core'
import { PostgresConnectionOptions } from 'typeorm/driver/postgres/PostgresConnectionOptions'
import { express as voyagerMiddleware } from 'graphql-voyager/middleware'
import express from 'express'

import { Community } from './core/community'
import { Context } from './types'
import { DBConfig, appConfig } from './app-config'
import { Score } from './core/score'
import { User } from './core/user'
import { UserCommunity } from './core/user-community'
import { Wod } from './core/wod'
import { Workout } from './core/workout'
import resolvers from './resolvers'
import typeDefs from './type-defs'

function createDBConn(dbConfig: DBConfig): Promise<Connection> {
  return createConnection(<PostgresConnectionOptions>{
    type: 'postgres',
    ...dbConfig,
    entities: [Community, Score, UserCommunity, User, Wod, Workout],
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
  app.use(express.static('../front/out'))
}

app.listen({ port: appConfig.port }, () =>
  console.log(
    `🚀 Server ready at http://localhost:${appConfig.port}${server.graphqlPath}`,
  ),
)
