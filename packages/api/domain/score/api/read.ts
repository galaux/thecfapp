import { Connection } from 'typeorm'
import { Context, UserWodKey } from '../../../types'
import { Score } from '../entity'
import { groupBy } from 'lodash/fp'

async function scores({ conn }: Context): Promise<Score[]> {
  return conn.getRepository(Score).find()
}

async function selectScoresByUserIds(
  conn: Connection,
  userWodIds: readonly UserWodKey[],
): Promise<Score[][]> {
  const scoresGpByUserWodIds = await conn
    .getRepository(Score)
    .find({ where: userWodIds.flat() })
    .then((scores) => groupBy((score) => [score.userId, score.wodId], scores))
  return userWodIds.map(
    ({ userId, wodId }) => scoresGpByUserWodIds[`${userId},${wodId}`] ?? [],
  )
}

async function userScores(
  { loaders }: Context,
  userId: string,
  wodId: string,
): Promise<Score[]> {
  return loaders.Score.byUserWodId.load({ userId, wodId })
}

export { scores, selectScoresByUserIds, userScores }
