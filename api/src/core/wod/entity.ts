import {
  Column,
  Entity,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
} from 'typeorm'
import { LocalDate } from '@js-joda/core'
import { Workout } from '../../core/workout'
import { v4 as uuid } from 'uuid'

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
    name: 'workout_id',
    type: 'uuid',
  })
  workoutId!: string

  @ManyToOne((_: any) => Workout)
  @JoinColumn({ name: 'workout_id' })
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
