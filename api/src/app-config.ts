export interface DBConfig {
  host: string
  port: number
  username: string
  password: string
  database: string
  synchronize: boolean
  logging: boolean
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
    host: process.env.DB_HOST!,
    port: parseInt(process.env.DB_PORT!),
    username: process.env.DB_USERNAME!,
    password: process.env.DB_PASSWORD!,
    database: process.env.DB_DATABASE!,
    synchronize: process.env.DB_SYNCHRONIZE === 'true',
    logging: process.env.DB_LOGGING === 'true',
  },
  port: parseInt(process.env.PORT!),
}
