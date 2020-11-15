import { Community } from './domain/community'
import { ConnectionOptions } from 'typeorm'
import { Score } from './domain/score'
import { User } from './domain/user'
import { UserCommunity } from './domain/user-community'
import { Wod } from './domain/wod'
import { Workout } from './domain/workout'

export interface AppConfig {
  apollo: {
    introspection: boolean
    playground: boolean
  }
  db?: ConnectionOptions
  port: number
}

export const liveAppConfig: AppConfig = {
  apollo: {
    introspection: process.env.APOLLO_INTROSPECTION === 'true',
    playground: process.env.APOLLO_PLAYGROUND === 'true',
  },
  db: {
    entities: [Community, Score, User, UserCommunity, Wod, Workout],
    logging: process.env.DATABASE_LOGGING === 'true',
    synchronize: process.env.DATABASE_SYNCHRONIZE === 'true',
    type: 'postgres',
    url: process.env.DATABASE_URL!,
  },
  port: parseInt(process.env.PORT!),
}

export const testAppConfig: AppConfig = {
  apollo: {
    introspection: false, // Not used in tests
    playground: false, // Not used in tests
  },
  db: undefined, // Will use connection defined in `ormconfig.yml`
  port: -1, // Not used in tests
}
