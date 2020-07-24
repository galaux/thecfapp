import { Context } from '~types'
import { CreateCommunityInput } from '~generated/graphql'

import { Community } from '../entity'

async function createCommunity(
  { conn }: Context,
  input: CreateCommunityInput,
): Promise<Community> {
  return conn.getRepository(Community).save(
    new Community({
      name: input.name,
    }),
  )
}

export { createCommunity }
