import { Connection } from 'typeorm'
import { Loaders } from './types'
import { curry } from 'lodash/fp'
import { groupBy } from 'lodash/fp'
import {
  selectCommunities,
  selectCommunitiesByUserIds,
} from './domain/community'
import { selectUsers } from './domain/user'
import { selectWods, selectWodsByCommunityIds } from './domain/wod'
import { selectWorkouts } from './domain/workout'
import DataLoader from 'dataloader'

/**
 * Make `repoFind` a "batch loading function", ie: transform it into a function
 * that can be directly passed to the constructor of `DataLoader`.
 *
 * A batch loading function accepts an Array of keys, and returns a Promise
 * which resolves to an Array of values or Error instances.
 *
 * There are a few constraints this function must uphold:
 *
 * - The Array of values must be the same length as the Array of keys.
 * - Each index in the Array of values must correspond to the same index in
 * the Array of keys.
 *
 * https://github.com/graphql/dataloader#batch-function
 */
async function batchable<Entity extends { id: string }>(
  repoFind: (_: string[]) => Promise<Entity[]>,
  keys: readonly string[],
): Promise<(Entity | Error)[]> {
  const entitiesByIds = await repoFind(
    <string[]>keys,
  ).then((entities: Entity[]) => groupBy((entity) => entity.id, entities))
  return keys.map((key: string) =>
    entitiesByIds[key]
      ? entitiesByIds[key][0]
      : new Error(`No entity for ${key}`),
  )
}

function createLoaders(
  conn: Connection,
  options?: Record<string, unknown>,
): Loaders {
  return {
    Community: {
      byUserId: new DataLoader(
        async (keys) => selectCommunitiesByUserIds(conn, keys),
        options,
      ),
      byId: new DataLoader(
        async (keys) => batchable(curry(selectCommunities)(conn), keys),
        options,
      ),
    },
    User: new DataLoader(
      async (keys) => batchable(curry(selectUsers)(conn), keys),
      options,
    ),
    Wod: {
      byCommunityId: new DataLoader(
        async (keys) => selectWodsByCommunityIds(conn, keys),
        options,
      ),
      byId: new DataLoader(
        async (keys) => batchable(curry(selectWods)(conn), keys),
        options,
      ),
    },
    Workout: new DataLoader(
      async (keys) => batchable(curry(selectWorkouts)(conn), keys),
      options,
    ),
  }
}

export { createLoaders }
