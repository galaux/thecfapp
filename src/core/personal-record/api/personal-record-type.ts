import { Context } from '../../../types'
import {
  CreatePersonalRecordTypeInput,
  PersonalRecordType as GQLRecordType,
} from '../../../generated/graphql'
import { ORMPersonalRecordType } from '../entities/personal-record-type'

async function personalRecordTypeById(
  { conn }: Context,
  id: string,
): Promise<GQLRecordType> {
  return conn.getRepository(ORMPersonalRecordType).findOneOrFail(id)
}

async function personalRecordTypes({
  conn,
}: Context): Promise<GQLRecordType[]> {
  return conn.getRepository(ORMPersonalRecordType).find()
}

async function createPersonalRecordType(
  context: Context,
  input: CreatePersonalRecordTypeInput,
): Promise<GQLRecordType> {
  return context.conn
    .getRepository(ORMPersonalRecordType)
    .save(new ORMPersonalRecordType({ name: input.name }))
}

export { createPersonalRecordType, personalRecordTypeById, personalRecordTypes }
