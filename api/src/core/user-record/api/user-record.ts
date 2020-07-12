import { Context } from '~types'
import { CreateUserRecordInput } from '~generated/graphql'

import { ORMUserRecord } from '../entities/user-record'

async function userRecords({ conn }: Context): Promise<ORMUserRecord[]> {
  return conn.getRepository(ORMUserRecord).find()
}

async function createUserRecord(
  { conn }: Context,
  input: CreateUserRecordInput,
): Promise<ORMUserRecord> {
  return conn.getRepository(ORMUserRecord).save(
    new ORMUserRecord({
      typeId: input.typeId,
      value: input.value,
    }),
  )
}

export { createUserRecord, userRecords }
