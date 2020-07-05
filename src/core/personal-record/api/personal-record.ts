import { Context } from '../../../types'
import { CreatePersonalRecordInput } from '../../../generated/graphql'
import { ORMPersonalRecord } from '../entities/personal-record'

async function personalRecords({
  conn,
}: Context): Promise<ORMPersonalRecord[]> {
  return conn.getRepository(ORMPersonalRecord).find()
}

async function createPersonalRecord(
  { conn }: Context,
  input: CreatePersonalRecordInput,
): Promise<ORMPersonalRecord> {
  return conn.getRepository(ORMPersonalRecord).save(
    new ORMPersonalRecord({
      typeId: input.typeId,
      value: input.value,
    }),
  )
}

export { createPersonalRecord, personalRecords }
