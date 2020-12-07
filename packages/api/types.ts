import { Community } from './domain/community'
import { Connection } from 'typeorm'
import { ConnectionOptions } from 'typeorm-seeding'
import { Score } from './domain/score'
import { User } from './domain/user'
import { Wod } from './domain/wod'
import { Workout } from './domain/workout'
import DataLoader from 'dataloader'

export type AppConfig = {
  apollo: {
    introspection: boolean
    playground: boolean
  }
  db?: ConnectionOptions
}

export type Context = {
  conn: Connection
  loaders: Loaders
}

export type UserWodKey = { userId: string; wodId: string }

export type Loaders = {
  Community: {
    byUserId: DataLoader<string, Community[]>
    byId: DataLoader<string, Community>
  }
  Score: {
    byUserWodId: DataLoader<UserWodKey, Score[]>
  }
  User: DataLoader<string, User>
  Wod: {
    byCommunityId: DataLoader<string, Wod[]>
    byId: DataLoader<string, Wod>
  }
  Workout: DataLoader<string, Workout>
}
