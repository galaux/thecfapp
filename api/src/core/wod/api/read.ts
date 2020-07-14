import { Context } from '~types'

import { Wod } from '../entity'

async function wods({ conn }: Context): Promise<Wod[]> {
  return conn.getRepository(Wod).find()
}

// TODO rename all other functions to follow the pattern `…ById`?
async function wodById({ conn }: Context, id: string): Promise<Wod> {
  return conn.getRepository(Wod).findOneOrFail(id)
}

export { wodById, wods }
