import { define } from 'typeorm-seeding'
import { v4 as uuid } from 'uuid'
import Faker from 'faker'

import { User } from './entity'

type SeedContext = {
  userId: string
}

define(User, (faker: typeof Faker, context?: SeedContext) => {
  return new User({
    id: context?.userId ?? uuid(),
    name: `${faker.name.firstName()} ${faker.name.lastName()}`,
  })
})
