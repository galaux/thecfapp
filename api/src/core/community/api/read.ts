import { Context } from '~types'

import { Community } from '../entity'
import { UserCommunity } from '~core/user-community'

async function communities({ conn }: Context): Promise<Community[]> {
  return conn.getRepository(Community).find()
}

// TODO rename in `userCommunities`?
async function communitiesByUserId(
  { conn }: Context,
  userId: string,
): Promise<Community[]> {
  return conn
    .getRepository(UserCommunity)
    .find({ where: { userId }, relations: ['community'] })
    .then((userCommunities) =>
      userCommunities.map((userCommunity) => userCommunity.community),
    )
}

export { communities, communitiesByUserId }
