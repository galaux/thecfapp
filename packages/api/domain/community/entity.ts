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

  constructor(
    args: {
      name?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.name) this.name = args.name
  }
}
