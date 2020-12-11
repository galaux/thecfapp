import { Community } from '../entity'
import { Context } from '../../../types'
import { CreateCommunityInput } from '../../../generated/graphql'

async function createCommunity(
  { conn }: Context,
  input: CreateCommunityInput,
): Promise<Community> {
  return conn.getRepository(Community).save(
    new Community({
      city: input.city,
      country: input.country,
      name: input.name,
      street: input.street,
      zipCode: input.zipCode,
    }),
  )
}

export { createCommunity }
