import 'reflect-metadata'

import { express as voyagerMiddleware } from 'graphql-voyager/middleware'
import express from 'express'

import { liveAppConfig as appConfig } from './app-config'
import { createServer } from './server'

const server = createServer(appConfig)
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
