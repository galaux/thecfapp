import { define } from 'typeorm-seeding'
import Faker from 'faker'

import { User } from './entity'

define(User, (faker: typeof Faker) => {
  return new User({
    name: `${faker.name.firstName()} ${faker.name.lastName()}`,
  })
})
