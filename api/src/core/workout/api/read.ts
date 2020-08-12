import { Connection } from 'typeorm'
import { Context } from '../../../types'
import { Workout } from '../entity'

async function selectWorkouts(
  conn: Connection,
  ids: string[],
): Promise<Workout[]> {
  return conn.getRepository(Workout).findByIds(ids)
}

async function workout({ loaders }: Context, id: string): Promise<Workout> {
  return loaders.Workout.load(id)
}

async function workouts({ conn }: Context): Promise<Workout[]> {
  return conn.getRepository(Workout).find()
}

export { selectWorkouts, workout, workouts }
