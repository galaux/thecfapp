import { Connection, In } from 'typeorm'
import { Context } from '../../../types'
import { Wod } from '../entity'
import { groupBy } from 'lodash/fp'

async function selectWods(conn: Connection, ids: string[]): Promise<Wod[]> {
  return conn.getRepository(Wod).findByIds(ids)
}

async function wods({ conn }: Context): Promise<Wod[]> {
  return conn.getRepository(Wod).find()
}

// TODO rename all other functions to follow the pattern `…ById`?
async function wodById({ loaders }: Context, id: string): Promise<Wod> {
  return loaders.Wod.byId.load(id)
}

async function selectWodsByCommunityIds(
  conn: Connection,
  communityIds: readonly string[],
): Promise<Wod[][]> {
  const wodsGpByCommunityIds = await conn
    .getRepository(Wod)
    .find({ where: { communityId: In(<string[]>communityIds) } })
    .then((wods) => groupBy((wod) => wod.communityId, wods))
  return communityIds.map((communityId) => wodsGpByCommunityIds[communityId])
}

async function wodsByCommunityId(
  { loaders }: Context,
  communityId: string,
): Promise<Wod[]> {
  return loaders.Wod.byCommunityId.load(communityId).then((e) => e ?? [])
}

export {
  selectWods,
  selectWodsByCommunityIds,
  wodById,
  wodsByCommunityId,
  wods,
}
