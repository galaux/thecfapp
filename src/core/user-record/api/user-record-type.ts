import { Context } from '../../../types'
import { CreateUserRecordTypeInput } from '../../../generated/graphql'
import { ORMUserRecordType } from '../entities/user-record-type'

async function userRecordTypeById(
  { conn }: Context,
  id: string,
): Promise<ORMUserRecordType> {
  return conn.getRepository(ORMUserRecordType).findOneOrFail(id)
}

async function userRecordTypes({
  conn,
}: Context): Promise<ORMUserRecordType[]> {
  return conn.getRepository(ORMUserRecordType).find()
}

async function createUserRecordType(
  context: Context,
  input: CreateUserRecordTypeInput,
): Promise<ORMUserRecordType> {
  return context.conn
    .getRepository(ORMUserRecordType)
    .save(new ORMUserRecordType({ name: input.name }))
}

export { createUserRecordType, userRecordTypeById, userRecordTypes }
