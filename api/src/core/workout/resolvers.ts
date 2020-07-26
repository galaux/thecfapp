import { Resolvers } from '../../generated/graphql'
import { createWorkout } from './api/create'
import { workouts } from './api/read'

export default <Resolvers>{
  Mutation: {
    createWorkout: (_, { input }, context) => createWorkout(context, input),
  },
  Query: {
    workouts: (_, __, context) => workouts(context),
  },
}
