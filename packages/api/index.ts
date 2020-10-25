import 'reflect-metadata'

import { liveAppConfig as appConfig } from './app-config'
import { createServer } from './server'

export default createServer(appConfig)
