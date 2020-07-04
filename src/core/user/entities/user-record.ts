import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { UserRecordType } from './user-record-type'
import { v4 as uuid } from 'uuid'

@Entity()
export class UserRecord {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column()
  name!: string

  @ManyToOne((_) => UserRecordType, 'id')
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
