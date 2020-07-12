import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm'
import { v4 as uuid } from 'uuid'

@Entity()
class UserRecordType {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    unique: true,
  })
  name!: string

  constructor(
    args: {
      name?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.name) this.name = args.name
  }
}

export { UserRecordType as ORMUserRecordType }
