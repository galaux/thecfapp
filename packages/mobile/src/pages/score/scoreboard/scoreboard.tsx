import { RouteComponentProps, useParams } from '@reach/router'
import React from 'react'
import { ScoreboardPanel } from '../../../components/ScoreboardPanel'
import { fetchPolicy } from '../../../fetch-policy'
import { useWodWithScoresQuery } from './scoreboard.generated'

export const ScoreboardPage: React.FC<RouteComponentProps> = () => {
  const props = useParams()
  const { wodId } = props

  const { loading, error, data } = useWodWithScoresQuery({
    fetchPolicy: fetchPolicy('wod-with-scores'),
    variables: { wodId },
  })

  return <ScoreboardPanel loading={loading} error={error} wod={data?.wod} />
}
