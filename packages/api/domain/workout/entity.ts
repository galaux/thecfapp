import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm'
import { ScoringType } from '../../generated/graphql'
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

  @Column()
  scoringType!: ScoringType

  constructor(
    args: {
      description?: string
      name?: string
      scoringType?: ScoringType
    } = {},
  ) {
    this.id = uuid()
    if (args.description) this.description = args.description
    if (args.name) this.name = args.name
    if (args.scoringType) this.scoringType = args.scoringType
  }
}
