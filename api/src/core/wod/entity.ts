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
import { Community } from '../community'

@Entity()
export class Wod {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    name: 'community_id',
    type: 'uuid',
  })
  communityId!: string

  @ManyToOne((_: any) => Community)
  @JoinColumn({ name: 'community_id' })
  community?: Community

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
      communityId?: string
      date?: LocalDate
      workoutId?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.communityId) this.communityId = args.communityId
    if (args.date) this.date = args.date
    if (args.workoutId) this.workoutId = args.workoutId
  }
}
