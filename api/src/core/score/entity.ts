import {
  Column,
  Entity,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
} from 'typeorm'
import { ScoreType } from '../../generated/graphql'
import { User } from '../../core/user'
import { Wod } from '../../core/wod/entity'
import { v4 as uuid } from 'uuid'

@Entity()
export class Score {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column()
  type!: ScoreType

  @Column({
    name: 'user_id',
    type: 'uuid',
  })
  userId!: string

  @ManyToOne((_: any) => User)
  @JoinColumn({ name: 'user_id' })
  user?: User

  @Column()
  value!: number

  @Column({
    name: 'wod_id',
    type: 'uuid',
  })
  wodId!: string

  @ManyToOne((_: any) => Wod)
  @JoinColumn({ name: 'wod_id' })
  wod?: Wod

  constructor(
    args: {
      type?: ScoreType
      value?: number
      userId?: string
      wodId?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.type) this.type = args.type
    if (args.userId) this.userId = args.userId
    if (args.value) this.value = args.value
    if (args.wodId) this.wodId = args.wodId
  }
}
