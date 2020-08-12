import { Connection } from 'typeorm'
import { Context } from '../../../types'
import { User } from '../entity'

async function selectUsers(conn: Connection, ids: string[]): Promise<User[]> {
  return conn.getRepository(User).findByIds(ids)
}

async function users({ conn }: Context): Promise<User[]> {
  return conn.getRepository(User).find()
}

async function user({ loaders }: Context, id: string): Promise<User> {
  return loaders.User.load(id)
}

export { user, users, selectUsers }
