import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { v4 as uuid } from 'uuid'

import { ScoreType } from '~generated/graphql'
import { User } from '~core/user'
import { Wod } from '~core/wod/entity'

@Entity()
export class Score {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column()
  type!: ScoreType

  @Column({
    name: 'userId',
    type: 'uuid',
  })
  userId!: string

  @ManyToOne((_: any) => User)
  user?: User

  @Column()
  value!: number

  @Column({
    name: 'wodId',
    type: 'uuid',
  })
  wodId!: string

  @ManyToOne((_: any) => Wod)
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
