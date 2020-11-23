import { Community } from './domain/community'
import { Connection } from 'typeorm'
import { User } from './domain/user'
import { Wod } from './domain/wod'
import { Workout } from './domain/workout'
import DataLoader from 'dataloader'
import { ConnectionOptions } from 'typeorm-seeding'

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

export type Loaders = {
  Community: {
    byUserId: DataLoader<string, Community[]>
    byId: DataLoader<string, Community>
  }
  User: DataLoader<string, User>
  Wod: {
    byCommunityId: DataLoader<string, Wod[]>
    byId: DataLoader<string, Wod>
  }
  Workout: DataLoader<string, Workout>
}
