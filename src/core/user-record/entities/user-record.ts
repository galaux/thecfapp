import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { ORMUserRecordType } from './user-record-type'
import { v4 as uuid } from 'uuid'

@Entity()
class UserRecord {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    name: 'typeId',
    type: 'uuid',
  })
  typeId!: string

  /* We are never going to use this relation but we declare it here so that
     TypeORM creates the foreign key constraints when generating the schema. */
  @ManyToOne((_: any) => ORMUserRecordType)
  type?: ORMUserRecordType

  @Column()
  value!: number

  constructor(
    args: {
      typeId?: string
      value?: number
    } = {},
  ) {
    this.id = uuid()
    if (args.typeId) this.typeId = args.typeId
    if (args.value) this.value = args.value
  }
}

export { UserRecord as ORMUserRecord }
