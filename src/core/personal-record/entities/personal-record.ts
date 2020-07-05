import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { ORMPersonalRecordType } from './personal-record-type'
import { v4 as uuid } from 'uuid'

@Entity()
class PersonalRecord {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    name: 'typeId',
    type: 'uuid',
  })
  typeId!: string

  /* We are never going to use this relation but we declare it here so that
     TypeORM creates the foreign key constraints when generating the schema. */
  @ManyToOne((_: any) => ORMPersonalRecordType)
  type?: ORMPersonalRecordType

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

export { PersonalRecord as ORMPersonalRecord }
