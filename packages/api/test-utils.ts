import { ApolloServerTestClient, createTestClient } from 'apollo-server-testing'
import { Context } from './types'
import { createLoaders } from './loaders'
import { createServer } from './server'
import { readFileSync } from 'fs'
import {
  setConnectionOptions,
  useRefreshDatabase,
  useSeeding,
} from 'typeorm-seeding'
import { testAppConfig } from './app-config'
import path from 'path'

const uuidRe = /[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/i

function readOperation(fname: string): string {
  return readFileSync(
    // TODO Should go an fetch this by accessing `@thecfapp/common`
    path.resolve('./', `../common/operations/${fname}.graphql`),
    'utf8',
  )
}

async function setupTestContext(): Promise<Context> {
  setConnectionOptions({
    type: 'sqlite',
    database: ':memory:',
    // Using entities (classes) here prevents the tests from running
    entities: ['build/domain/**/entity.js', 'domain/**/entity.ts'],
  })
  // Connects to the database, drops it and recreates the schema
  const conn = await useRefreshDatabase()
  // Loads all the defined entity factories
  await useSeeding()

  const loaders = createLoaders(conn, { cache: false })

  return { conn, loaders }
}

async function setupTestServer(): Promise<ApolloServerTestClient> {
  const { conn } = await setupTestContext()

  /* FIXME we created a Context but we are not passing it to createServer
     Either
       - don't call this function createServer
       - or actually make it run the fn that creates the Context
         and then pass the Context to the createServer
*/
  const server = createServer(testAppConfig, conn)

  // https://github.com/apollographql/apollo-server/issues/1140
  // https://github.com/apollographql/apollo-server/issues/4376
  return createTestClient(server as any)
}

export { readOperation, setupTestContext, setupTestServer, uuidRe }
