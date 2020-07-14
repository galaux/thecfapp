import { Context } from '~types'

import { User } from '../entity'

async function users({ conn }: Context): Promise<User[]> {
  return conn.getRepository(User).find()
}

async function user({ conn }: Context, id: string): Promise<User> {
  return conn.getRepository(User).findOneOrFail(id)
}

export { user, users }
