import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm'
import { v4 as uuid } from 'uuid'

@Entity()
export class Community {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    unique: true,
  })
  name!: string

  // Address fields
  @Column({
    name: 'street',
    nullable: true,
  })
  street?: string

  @Column({
    name: 'city',
    nullable: true,
  })
  city?: string

  @Column({
    name: 'country',
    nullable: true,
  })
  country?: string

  @Column({
    name: 'zipCode',
    nullable: true,
  })
  zipCode?: string

  constructor(
    args: {
      city?: string | null | undefined
      country?: string | null | undefined
      name?: string
      street?: string | null | undefined
      zipCode?: string | null | undefined
    } = {},
  ) {
    this.id = uuid()
    if (args.city) this.city = args.city
    if (args.country) this.country = args.country
    if (args.name) this.name = args.name
    if (args.street) this.street = args.street
    if (args.zipCode) this.zipCode = args.zipCode
  }
}
