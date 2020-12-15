import { Community } from '../entity'
import { Connection, ILike, In } from 'typeorm'
import { Context } from '../../../types'
import { UserCommunity } from '../../../domain/user-community'

async function selectCommunitiesByUserIds(
  conn: Connection,
  userIds: readonly string[],
): Promise<Community[][]> {
  const userCommunityList = await conn.getRepository(UserCommunity).find({
    where: { userId: In(<string[]>userIds) },
    relations: ['community'],
  })

  const userToCommunityMap = new Map<string, Community[]>()
  for (const userCommunity of userCommunityList) {
    if (userToCommunityMap.has(userCommunity.userId)) {
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
      userToCommunityMap
        .get(userCommunity.userId)!
        .push(userCommunity.community)
    } else {
      userToCommunityMap.set(userCommunity.userId, [userCommunity.community])
    }
  }

  return userIds.map((userId) => userToCommunityMap.get(userId) || [])
}

async function communities({ conn }: Context): Promise<Community[]> {
  return conn.getRepository(Community).find()
}

// TODO rename in `userCommunities`?
// TODO use dataloader
async function communitiesByUserId(
  { loaders }: Context,
  userId: string,
): Promise<Community[]> {
  return loaders.Community.byUserId.load(userId)
}

async function lookupCommunities(
  { conn }: Context,
  name: string,
): Promise<Community[]> {
  return conn.getRepository(Community).find({
    name: ILike(`%${name}%`),
  })
}

async function selectCommunities(
  conn: Connection,
  ids: string[],
): Promise<Community[]> {
  return conn.getRepository(Community).findByIds(ids)
}

async function community({ loaders }: Context, id: string): Promise<Community> {
  return loaders.Community.byId.load(id)
}

async function memberCount(
  { conn }: Context,
  communityId: string,
): Promise<number> {
  return conn.getRepository(UserCommunity).count({ communityId })
}

export {
  communities,
  community,
  communitiesByUserId,
  lookupCommunities,
  memberCount,
  selectCommunities,
  selectCommunitiesByUserIds,
}
