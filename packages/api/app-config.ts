import { AppConfig } from './types'
import { ConnectionOptions } from 'typeorm-seeding'

const liveDBConfig: ConnectionOptions = {
  name: 'default',
  type: 'postgres',
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
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
  // migrations: ['migration/*.js'],
  // cli: {
  //   migrationsDir: 'migrations',
  // },
  /* FIXME write/update the DB schema based on entities at startup (applies in
     all environments: dev, test, review apps, staging, production). This is
     OK until we have real production data at which point we will need to remove
     this option and setup migrations.
     Nota: this has nothing to do with the seeder which needs to be manually run
     except for review apps. */
  synchronize: true,
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
