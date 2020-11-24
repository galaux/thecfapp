import { ApolloServerTestClient, createTestClient } from 'apollo-server-testing'
import { Connection } from 'typeorm'
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

async function setupTestConn(): Promise<Connection> {
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
  return conn
}

async function setupTestContext(): Promise<Context> {
  const conn = await setupTestConn()
  const loaders = createLoaders(conn, { cache: false })
  return { conn, loaders }
}

async function setupTest(): Promise<ApolloServerTestClient> {
  const conn = await setupTestConn()
  const server = createServer(testAppConfig, conn)
  // https://github.com/apollographql/apollo-server/issues/1140
  // https://github.com/apollographql/apollo-server/issues/4376
  /* FIXME could potentially be fixed by using similar versions between
     apollo-server and apollo-server-test */
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  return createTestClient(server as any)
}

export { readOperation, setupTestContext, setupTest, uuidRe }
