import { Connection } from 'typeorm'
import { Context } from '../../../types'
import { Wod } from '../entity'

async function selectWods(conn: Connection, ids: string[]): Promise<Wod[]> {
  return conn.getRepository(Wod).findByIds(ids)
}

async function wods({ conn }: Context): Promise<Wod[]> {
  return conn.getRepository(Wod).find()
}

// TODO rename all other functions to follow the pattern `…ById`?
async function wodById({ loaders }: Context, id: string): Promise<Wod> {
  return loaders.Wod.load(id)
}

export { selectWods, wodById, wods }
