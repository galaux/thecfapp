import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm'
import { v4 as uuid } from 'uuid'

@Entity()
export class Workout {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column()
  description!: string

  @Column({
    unique: true,
  })
  name!: string

  constructor(
    args: {
      description?: string
      name?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.description) this.description = args.description
    if (args.name) this.name = args.name
  }
}
