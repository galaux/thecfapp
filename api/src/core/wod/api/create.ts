import { Context } from '../../../types'
import { CreateWodInput } from '../../../generated/graphql'
import { Wod } from '../entity'

async function createWod(
  { conn }: Context,
  input: CreateWodInput,
): Promise<Wod> {
  return conn.getRepository(Wod).save(
    new Wod({
      date: input.date,
      workoutId: input.workoutId,
    }),
  )
}

export { createWod }
