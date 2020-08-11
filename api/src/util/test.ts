import { ApolloServerTestClient, createTestClient } from 'apollo-server-testing'
import { Connection } from 'typeorm'
import { createServer } from '../server'
import {
  setConnectionOptions,
  useRefreshDatabase,
  useSeeding,
} from 'typeorm-seeding'
import { testAppConfig } from '../app-config'
import { v4 as uuid } from 'uuid'

const dbConnectionBaseName = 'test'

const uuidRe = /[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/i

async function setupTestConnection(): Promise<Connection> {
  /* Instruct typeorm-seeding that once it has loaded the connection options
     (see `useRefreshDatabase` afterwards), it should change the name of the
     connection */
  setConnectionOptions({ name: `${dbConnectionBaseName}-${uuid()}` })
  // Instruct typeorm-seeding to use the options `test` from `ormconfig.yml`
  const connection = useRefreshDatabase({
    connection: dbConnectionBaseName,
  })
  await useSeeding({ connection: dbConnectionBaseName })

  return connection
}

async function setupTestServer(): Promise<ApolloServerTestClient> {
  const connection = await setupTestConnection()

  const server = createServer(testAppConfig, connection)

  // https://github.com/apollographql/apollo-server/issues/1140
  // https://github.com/apollographql/apollo-server/issues/4376
  return createTestClient(server as any)
}

export { dbConnectionBaseName, setupTestServer, setupTestConnection, uuidRe }
