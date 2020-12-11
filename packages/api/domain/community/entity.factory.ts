import { define } from 'typeorm-seeding'
import Faker from 'faker'

import { Community } from './entity'

define(Community, (faker: typeof Faker) => {
  const city = faker.address.city()
  return new Community({
    city: faker.random.number({ max: 3 }) !== 0 ? city : undefined,
    country:
      faker.random.number({ max: 3 }) !== 0
        ? faker.address.country()
        : undefined,
    name: `CrossFit ${city}`,
    street:
      faker.random.number({ max: 3 }) !== 0
        ? faker.address.streetAddress()
        : undefined,
    zipCode:
      faker.random.number({ max: 3 }) !== 0
        ? faker.address.zipCode()
        : undefined,
  })
})
