import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'
import { Community } from '~core/community'
import { User } from '~core/user/entity'
import { v4 as uuid } from 'uuid'

// FIXME add constraint to prevent adding a user twice to the same community

@Entity()
export class UserCommunity {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @ManyToOne((_: any) => User)
  user!: User

  @Column({
    name: 'userId',
    type: 'uuid',
  })
  userId!: string

  @ManyToOne((_: any) => Community)
  community!: Community

  @Column({
    name: 'communityId',
    type: 'uuid',
  })
  communityId!: string

  constructor(
    args: {
      userId?: string
      communityId?: string
    } = {},
  ) {
    this.id = uuid()
    if (args.userId) this.userId = args.userId
    if (args.communityId) this.communityId = args.communityId
  }
}
