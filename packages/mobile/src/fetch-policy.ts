import { WatchQueryFetchPolicy } from '@apollo/client'

/* https://www.apollographql.com/docs/react/data/queries/#setting-a-fetch-policy */

const FIFTEEN_MINUTES_IN_MS = 15 * 60 * 1000

// const singletonPollingVar = makeVar<Record<string, Date>>({})

export function fetchPolicy(
  key: string,
  ttlMs: number = FIFTEEN_MINUTES_IN_MS,
): WatchQueryFetchPolicy {
  return 'cache-and-network'
  // const now = Date.now()
  // const current = singletonPollingVar()
  // if (now < current[key]?.getTime() + ttlMs) {
  //   return 'cache-first'
  // }
  // current[key] = new Date(now)
  // singletonPollingVar(current)
  // return 'cache-and-network'
}
