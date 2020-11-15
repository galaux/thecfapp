import { define } from 'typeorm-seeding'
import Faker from 'faker'

import { Community } from './entity'

define(Community, (faker: typeof Faker) => {
  return new Community({
    name: `CrossFit ${faker.address.city()}`,
  })
})
