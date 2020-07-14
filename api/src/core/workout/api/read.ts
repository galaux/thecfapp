import { Context } from '~types'

import { Workout } from '../entity'

async function workout({ conn }: Context, id: string): Promise<Workout> {
  return conn.getRepository(Workout).findOneOrFail(id)
}

async function workouts({ conn }: Context): Promise<Workout[]> {
  return conn.getRepository(Workout).find()
}

export { workout, workouts }
