import { Context } from '../../../types'
import { CreateWorkoutInput } from '../../../generated/graphql'
import { Workout } from '../entity'

async function createWorkout(
  { conn }: Context,
  input: CreateWorkoutInput,
): Promise<Workout> {
  return conn.getRepository(Workout).save(
    new Workout({
      description: input.description,
      name: input.name,
      scoringType: input.scoringType,
    }),
  )
}

export { createWorkout }
