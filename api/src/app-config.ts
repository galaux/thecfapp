export interface DBConfig {
  logging: boolean
  synchronize: boolean
  url: string
}

export interface AppConfig {
  apollo: {
    introspection: boolean
    playground: boolean
  }
  serveFrontEnd: boolean
  db: DBConfig
  port: number
}

export const appConfig: AppConfig = {
  apollo: {
    introspection: process.env.APOLLO_INTROSPECTION === 'true',
    playground: process.env.APOLLO_PLAYGROUND === 'true',
  },
  serveFrontEnd: process.env.SERVE_FRONT_END !== 'false',
  db: {
    logging: process.env.DATABASE_LOGGING === 'true',
    synchronize: process.env.DATABASE_SYNCHRONIZE === 'true',
    url: process.env.DATABASE_URL!,
  },
  port: parseInt(process.env.PORT!),
}
