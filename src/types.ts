import { Connection } from 'typeorm'

export type Context = {
  conn: Connection
}
