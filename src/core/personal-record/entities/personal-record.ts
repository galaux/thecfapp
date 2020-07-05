import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { PersonalRecordType } from './personal-record-type'
import { v4 as uuid } from 'uuid'

@Entity()
export class PersonalRecord {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @ManyToOne((_: any) => PersonalRecordType, 'id')
  type!: string // TODO change name for `typeId`

  @Column()
  value!: number

  constructor(
    args: {
      type?: string
      value?: number
    } = {},
  ) {
    this.id = uuid()
    if (args.type) this.type = args.type
    if (args.value) this.value = args.value
  }
}
