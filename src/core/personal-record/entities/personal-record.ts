import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { PersonalRecordType } from './personal-record-type'
import { v4 as uuid } from 'uuid'

@Entity()
export class PersonalRecord {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column()
  name!: string

  @ManyToOne((_) => PersonalRecordType, 'id')
  type!: string

  @Column()
  value!: number

  constructor(
    args: {
      name?: string
      type?: string
      value?: number
    } = {},
  ) {
    this.id = uuid()
    if (args.name) this.name = args.name
    if (args.type) this.type = args.type
    if (args.value) this.value = args.value
  }
}
