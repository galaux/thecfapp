import { LocalDate } from 'graphql-joda-types'
import { Resolvers } from './generated/graphql'
import { resolvers as communityResolvers } from './core/community'
import { resolvers as scoreResolvers } from './core/score'
import { resolvers as userResolvers } from './core/user'
import { resolvers as wodResolvers } from './core/wod'
import { resolvers as workoutResolvers } from './core/workout'

const scalarResolvers: Resolvers = {
  Date: LocalDate,
}

export default [
  scalarResolvers,

  communityResolvers,
  scoreResolvers,
  userResolvers,
  wodResolvers,
  workoutResolvers,
]
