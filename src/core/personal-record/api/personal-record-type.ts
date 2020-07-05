import { PersonalRecordType } from '../entities/personal-record-type'
import { Context } from '../../../types'
import {
  PersonalRecordType as GQLRecordType,
  CreatePersonalRecordTypeInput,
} from '../../../generated/graphql'
import { PersonalRecordType as ORMRecordType } from '../entities/personal-record-type'

function personalRecordTypeReducer(entity: ORMRecordType): GQLRecordType {
  return {
    id: entity.id,
    name: entity.name,
  }
}

async function personalRecordTypeById(
  { conn }: Context,
  id: string,
): Promise<GQLRecordType> {
  return conn
    .getRepository(PersonalRecordType)
    .findOneOrFail(id)
    .then(personalRecordTypeReducer)
}

async function personalRecordTypes({
  conn,
}: Context): Promise<GQLRecordType[]> {
  return conn
    .getRepository(PersonalRecordType)
    .find()
    .then((types) => types.map(personalRecordTypeReducer))
}

async function createPersonalRecordType(
  context: Context,
  input: CreatePersonalRecordTypeInput,
): Promise<GQLRecordType> {
  return context.conn
    .getRepository(ORMRecordType)
    .save(new ORMRecordType({ name: input.name }))
    .then(personalRecordTypeReducer)
}

export { createPersonalRecordType, personalRecordTypeById, personalRecordTypes }
