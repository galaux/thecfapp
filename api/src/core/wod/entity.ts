import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { v4 as uuid } from 'uuid'

import { Workout } from '~core/workout'

@Entity()
export class Wod {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    type: 'timestamp with time zone',
    nullable: false,
  })
  date!: Date

  @Column({
    name: 'workoutId',
    type: 'uuid',
  })
  workoutId!: string

  @ManyToOne((_: any) => Workout)
  workout!: Workout

  constructor(
    args: {
      date?: Date
      workoutId?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.date) this.date = args.date
    if (args.workoutId) this.workoutId = args.workoutId
  }
}
