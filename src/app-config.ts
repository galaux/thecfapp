const defaultPort = 4000

interface AppConfig {
  apollo: {
    introspection: boolean
    playground: boolean
  }
  port: number | string
}

export const appConfig: AppConfig = {
  apollo: {
    introspection: process.env.APOLLO_INTROSPECTION === 'true',
    playground: process.env.APOLLO_PLAYGROUND === 'true',
  },
  port: process.env.PORT || defaultPort,
}
