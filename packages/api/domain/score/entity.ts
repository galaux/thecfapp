import {
  Column,
  Entity,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
} from 'typeorm'
import { User } from '../../domain/user'
import { Wod } from '../../domain/wod/entity'
import { v4 as uuid } from 'uuid'

@Entity()
export class Score {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({
    name: 'user_id',
    type: 'uuid',
  })
  userId!: string

  @ManyToOne(() => User)
  @JoinColumn({ name: 'user_id' })
  user?: User

  @Column()
  value!: number

  @Column({
    name: 'wod_id',
    type: 'uuid',
  })
  wodId!: string

  @ManyToOne(() => Wod)
  @JoinColumn({ name: 'wod_id' })
  wod?: Wod

  constructor(
    args: {
      value?: number
      userId?: string
      wodId?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.userId) this.userId = args.userId
    if (args.value) this.value = args.value
    if (args.wodId) this.wodId = args.wodId
  }
}
