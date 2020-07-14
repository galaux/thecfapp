import { resolvers as communityResolvers } from '~core/community'
import { resolvers as scoreResolvers } from '~core/score'
import { resolvers as userResolvers } from '~core/user'
import { resolvers as wodResolvers } from '~core/wod'
import { resolvers as workoutResolvers } from '~core/workout'

export default [
  communityResolvers,
  scoreResolvers,
  userResolvers,
  wodResolvers,
  workoutResolvers,
]
