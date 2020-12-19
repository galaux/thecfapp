import { Connection, In } from 'typeorm'
import { Context } from '../../../types'
import { Score } from '../entity'
import { groupBy } from 'lodash/fp'

async function selectScoresByWodIds(
  conn: Connection,
  wodIds: readonly string[],
): Promise<Score[][]> {
  const scoresGpByUserWodIds = await conn
    .getRepository(Score)
    .find({ where: { wodId: In(<string[]>wodIds) } })
    .then((scores) => groupBy((score) => score.wodId, scores))
  return wodIds.map((wodId) => scoresGpByUserWodIds[wodId] ?? [])
}

async function wodScores(
  { loaders }: Context,
  wodId: string,
): Promise<Score[]> {
  return loaders.Score.byWodId.load(wodId)
}

export { selectScoresByWodIds, wodScores }
