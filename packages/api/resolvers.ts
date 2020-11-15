import { LocalDate } from 'graphql-joda-types'
import { Resolvers } from './generated/graphql'
import { resolvers as communityResolvers } from './domain/community'
import { resolvers as scoreResolvers } from './domain/score'
import { resolvers as userResolvers } from './domain/user'
import { resolvers as wodResolvers } from './domain/wod'
import { resolvers as workoutResolvers } from './domain/workout'

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
