import { express as voyagerMiddleware } from 'graphql-voyager/middleware'
import express from 'express'
import path from 'path'

import gqlServer from '@thecfapp/api'

// TODO move `AppConfig` to common and use id here
const { PORT } = process.env

async function main() {
  const app = express()

  await bootstrapApolloServer(app)
  if (process.env.SERVE_MOBILE !== 'false') {
    serveMobileApp(app)
  }

  app.listen(PORT, () => {
    console.log(`[ server ] ready on port ${PORT}`)
  })
}

function serveMobileApp(app) {
  app.use(express.static(path.join(__dirname, '../mobile/build')))
}

async function bootstrapApolloServer(app) {
  gqlServer.applyMiddleware({ app })
  app.use('/voyager', voyagerMiddleware({ endpointUrl: '/graphql' }))
}

main()
