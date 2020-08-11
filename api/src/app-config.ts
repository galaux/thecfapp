import { Community } from './core/community'
import { ConnectionOptions } from 'typeorm'
import { Score } from './core/score'
import { User } from './core/user'
import { UserCommunity } from './core/user-community'
import { Wod } from './core/wod'
import { Workout } from './core/workout'

export interface AppConfig {
  apollo: {
    introspection: boolean
    playground: boolean
  }
  serveFrontEnd: boolean
  db?: ConnectionOptions
  port: number
}

export const liveAppConfig: AppConfig = {
  apollo: {
    introspection: process.env.APOLLO_INTROSPECTION === 'true',
    playground: process.env.APOLLO_PLAYGROUND === 'true',
  },
  serveFrontEnd: process.env.SERVE_FRONT_END !== 'false',
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
  serveFrontEnd: false, // Not used in tests
  db: undefined, // Will use connection defined in `ormconfig.yml`
  port: -1, // Not used in tests
}
