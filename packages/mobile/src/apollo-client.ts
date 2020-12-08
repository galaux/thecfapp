import { ApolloClient, HttpLink, InMemoryCache } from '@apollo/client'
// import { HttpLink } from 'apollo-link-http'
import { CachePersistor, LocalStorageWrapper } from 'apollo3-cache-persist'

// Local
const API_HOST = 'http://localhost:4000/graphql'
export const userId = '499487df-3e84-4fa8-ad60-c9444dcdf7f5'

// Prod
// const API_HOST = 'https://thecfapp.herokuapp.com/graphql'
// export const userId = "366a8963-6f81-4cfb-9a0c-c2c2629bd556"

const SCHEMA_VERSION = '1'
const SCHEMA_VERSION_KEY = 'apollo-schema-version'

const getApolloClient = async () => {
  const httpLink = new HttpLink({ uri: API_HOST })
  const cache = new InMemoryCache()

  const persistor = new CachePersistor({
    cache,
    storage: new LocalStorageWrapper(window.localStorage),
  })

  const currentVersion = window.localStorage.getItem(SCHEMA_VERSION_KEY)

  if (currentVersion === SCHEMA_VERSION) {
    await persistor.restore()
  } else {
    await persistor.purge()
    window.localStorage.setItem(SCHEMA_VERSION_KEY, SCHEMA_VERSION)
  }

  return new ApolloClient({ link: httpLink, cache })
}

export default getApolloClient
