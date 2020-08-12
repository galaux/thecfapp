import { ApolloServerTestClient, createTestClient } from 'apollo-server-testing'
import { Connection } from 'typeorm'
import { Context } from '../types'
import { createLoaders } from './loaders'
import { createServer } from '../server'
import { readFileSync } from 'fs'
import {
  setConnectionOptions,
  useRefreshDatabase,
  useSeeding,
} from 'typeorm-seeding'
import { testAppConfig } from '../app-config'
import { v4 as uuid } from 'uuid'
import path from 'path'

const dbConnectionBaseName = 'test'

const uuidRe = /[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/i

function readOperation(fname: string): string {
  return readFileSync(
    path.resolve('./', `../common/operations/${fname}.graphql`),
    'utf8',
  )
}

async function setupTestContext(): Promise<Context> {
  /* Instruct typeorm-seeding that once it has loaded the connection options
     (see `useRefreshDatabase` afterwards), it should change the name of the
     connection */
  setConnectionOptions({ name: `${dbConnectionBaseName}-${uuid()}` })
  // Instruct typeorm-seeding to use the options `test` from `ormconfig.yml`
  const conn = await useRefreshDatabase({
    connection: dbConnectionBaseName,
  })
  await useSeeding({ connection: dbConnectionBaseName })

  const loaders = createLoaders(conn, { cache: false })

  return { conn, loaders }
}

async function setupTestServer(): Promise<ApolloServerTestClient> {
  const { conn } = await setupTestContext()

  const server = createServer(testAppConfig, conn)

  // https://github.com/apollographql/apollo-server/issues/1140
  // https://github.com/apollographql/apollo-server/issues/4376
  return createTestClient(server as any)
}

export {
  dbConnectionBaseName,
  readOperation,
  setupTestContext,
  setupTestServer,
  uuidRe,
}
