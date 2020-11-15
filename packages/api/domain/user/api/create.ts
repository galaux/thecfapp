import { Context } from '../../../types'
import { CreateUserInput } from '../../../generated/graphql'
import { User } from '../entity'

async function createUser(
  { conn }: Context,
  input: CreateUserInput,
): Promise<User> {
  return conn.getRepository(User).save(
    new User({
      name: input.name,
    }),
  )
}

export { createUser }
