import { Connection } from 'typeorm'
import { User } from './core/user'
import { Wod } from './core/wod'
import { Workout } from './core/workout'
import DataLoader from 'dataloader'

export type Context = {
  conn: Connection
  loaders: Loaders
}

export type Loaders = {
  User: DataLoader<string, User>
  Wod: DataLoader<string, Wod>
  Workout: DataLoader<string, Workout>
}
