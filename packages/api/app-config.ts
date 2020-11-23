import { ConnectionOptions } from 'typeorm-seeding'
import { AppConfig } from './types'
import { Community } from './domain/community'
import { Score } from './domain/score'
import { User } from './domain/user'
import { UserCommunity } from './domain/user-community'
import { Wod } from './domain/wod'
import { Workout } from './domain/workout'

const liveDBConfig: ConnectionOptions = {
  name: 'default',
  type: 'postgres',
  url: process.env.DATABASE_URL!,
  logging: process.env.DATABASE_LOGGING === 'true',
  // Warning: chaning entity paths for entities fails tests
  entities: [Community, Score, User, UserCommunity, Wod, Workout],
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
