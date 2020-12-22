import { ApolloClient, HttpLink, InMemoryCache } from '@apollo/client'
// import { HttpLink } from 'apollo-link-http'
import { CachePersistor, LocalStorageWrapper } from 'apollo3-cache-persist'
import {
  UserCommunitiesQueryVariables,
  UserCommunitiesQuery,
  UserCommunitiesDocument,
} from '../pages/community'
import {
  UserFieldsFragment,
  UserFieldsFragmentDoc,
} from './fragment/fields.generated'
import { loggedUserId } from './state/app'

/* https://www.apollographql.com/docs/react/caching/cache-field-behavior/#fieldpolicy-api-reference */

const SCHEMA_VERSION = '1'
const SCHEMA_VERSION_KEY = 'apollo-schema-version'

const getApolloClient = async () => {
  /* This mobile app is served from a web server but it is setup as a
     Progressive Web Application (PWA) so that its assets are cached on the
     client's browser. But at run time, the browser still considers it is
     running JS from the web server's location. For this reason using a
     relative path to point at the API is telling the client that API is
     located next to where it got the assets of the application: the remote web
     server. */
  const httpLink = new HttpLink({ uri: '/graphql' })
  const cache = new InMemoryCache({
    typePolicies: {
      Community: {
        fields: {
          isMember: {
            read(_, { cache, readField }) {
              const communityId = readField<string>('id')
              // This can't happen but is required by the compiler
              if (!communityId) return false
              const userCommunities = cache.readQuery<
                UserCommunitiesQuery,
                UserCommunitiesQueryVariables
              >({
                query: UserCommunitiesDocument,
                variables: { userId: loggedUserId() },
              })
              return userCommunities?.userCommunities
                .map((c) => c.id)
                .includes(communityId)
            },
          },
        },
      },
      Score: {
        fields: {
          belongsToLoggedUser: {
            read(_, { cache, readField }) {
              const ownerRef = readField<UserFieldsFragment>('user')
              if (!ownerRef) return
              const owner = cache.readFragment({
                id: cache.identify(ownerRef),
                fragment: UserFieldsFragmentDoc,
              }) as UserFieldsFragment
              // If owner is undefined then it mustn't be the logged user
              return owner?.id === loggedUserId()
            },
          },
        },
      },
    },
  })

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

  return new ApolloClient({
    link: httpLink,
    cache,
    /* Directly mutating cache values is a bad pattern that is discouraged
       by Apollo developers. One can still modify values inside the cache
       by replacing them with new one as this is not a direct mutation of
       a value in the cache (see link).
       If we are sure nowhere in the code do we try to mutate the value of
       the cache, we can enable let Apollo know which wich free it from some
       safety checks.
       https://www.apollographql.com/blog/whats-new-in-apollo-client-2-6-b3acf28ecad1/
    */
    // assumeImmutableResults: true,
  })
}

export default getApolloClient
