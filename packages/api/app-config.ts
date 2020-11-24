import { ConnectionOptions } from 'typeorm-seeding'
import { AppConfig } from './types'

const liveDBConfig: ConnectionOptions = {
  name: 'default',
  type: 'postgres',
  url: process.env.DATABASE_URL!,
  logging: process.env.DATABASE_LOGGING === 'true',
  /* Using entities (classes) directly prevents typeorm-seeding to run and using
     entity paths without `__dirname` here prevents the app from working
     https://github.com/typeorm/typeorm/issues/3017 */
  entities: [
    __dirname + '/build/domain/**/entity.js',
    __dirname + '/domain/**/entity.ts',
  ],
  seeds: ['db-seeder.ts', 'build/db-seeder.js'],
  factories: [
    'domain/**/entity.factory.ts',
    'build/domain/**/entity.factory.js',
  ],
}

const liveAppConfig: AppConfig = {
  apollo: {
    introspection: process.env.APOLLO_INTROSPECTION === 'true',
    playground: process.env.APOLLO_PLAYGROUND === 'true',
  },
  db: liveDBConfig,
}

const testAppConfig: AppConfig = {
  apollo: {
    introspection: false,
    playground: false,
  },
  // Not used in tests, typeorm-seeding creates a connection with overrides
  db: undefined,
}

export { liveAppConfig, liveDBConfig, testAppConfig }
