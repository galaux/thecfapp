import { Context } from '../../../types'
import { CreateScore } from '../../../generated/graphql'
import { Score } from '../entity'

async function createScore(
  { conn }: Context,
  input: CreateScore,
): Promise<Score> {
  return conn.getRepository(Score).save(
    new Score({
      userId: input.userId,
      value: input.value,
      wodId: input.wodId,
    }),
  )
}

export { createScore }
