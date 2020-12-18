import { Context } from '../../../types'
import { CreateWodInput } from '../../../generated/graphql'
import { Wod } from '../entity'

async function createWod(
  { conn }: Context,
  input: CreateWodInput,
): Promise<Wod> {
  return conn.getRepository(Wod).save(
    // FIXME First check we don't already have this triplet in DB
    new Wod({
      communityId: input.communityId,
      date: input.date,
      workoutId: input.workoutId,
    }),
  )
}

export { createWod }
