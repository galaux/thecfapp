import { Context } from '../../../types'
import {
  PersonalRecord as GQLRecord,
  CreatePersonalRecordInput,
} from '../../../generated/graphql'
import { PersonalRecord as ORMRecord } from '../entities/personal-record'

function personalRecordReducer(entity: ORMRecord): GQLRecord {
  return {
    id: entity.id,
    // type: PersonalRecordType
    typeId: entity.type, // TODO remove this field
    value: entity.value,
  }
}

async function personalRecords({ conn }: Context): Promise<GQLRecord[]> {
  return conn
    .getRepository(ORMRecord)
    .find()
    .then((prs) => prs.map(personalRecordReducer))
}

async function createPersonalRecord(
  context: Context,
  input: CreatePersonalRecordInput,
): Promise<GQLRecord> {
  return context.conn
    .getRepository(ORMRecord)
    .save(
      new ORMRecord({
        type: input.type,
        value: input.value,
      }),
    )
    .then(personalRecordReducer)
}

export { createPersonalRecord, personalRecords }
