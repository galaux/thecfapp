import { define } from 'typeorm-seeding'
import Faker from 'faker'

import { Community } from '../community'
import { User } from '../user/entity'

import { UserCommunity } from './entity'

type SeedContext = {
  communities: Community[]
  users: User[]
}

define(UserCommunity, (_: typeof Faker, context?: SeedContext) => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const { users, communities } = context!
  const community = communities[Math.floor(Math.random() * communities.length)]
  const user = users[Math.floor(Math.random() * users.length)]
  return new UserCommunity({
    communityId: community.id,
    userId: user.id,
  })
})
