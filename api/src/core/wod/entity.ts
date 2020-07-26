import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { LocalDate } from '@js-joda/core'
import { v4 as uuid } from 'uuid'

import { Workout } from '~core/workout'

@Entity()
export class Wod {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    type: 'date',
    nullable: false,
  })
  date!: LocalDate

  @Column({
    name: 'workoutId',
    type: 'uuid',
  })
  workoutId!: string

  @ManyToOne((_: any) => Workout)
  workout!: Workout

  constructor(
    args: {
      date?: LocalDate
      workoutId?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.date) this.date = args.date
    if (args.workoutId) this.workoutId = args.workoutId
  }
}
