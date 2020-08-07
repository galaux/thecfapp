import { AddUserToCommunityInput } from '../../../generated/graphql'
import { Context } from '../../../types'
import { User } from '../entity'
import { UserCommunity } from '../../../core/user-community'

async function addUserToCommunity(
  { conn }: Context,
  input: AddUserToCommunityInput,
): Promise<User> {
  await conn.getRepository(UserCommunity).save(
    new UserCommunity({
      communityId: input.communityId,
      userId: input.userId,
    }),
  )
  return conn.getRepository(User).findOneOrFail(input.userId)
}

export { addUserToCommunity }
