import { Context } from '~types'

import { Score } from '../entity'

async function scores({ conn }: Context): Promise<Score[]> {
  return conn.getRepository(Score).find()
}

export { scores }
