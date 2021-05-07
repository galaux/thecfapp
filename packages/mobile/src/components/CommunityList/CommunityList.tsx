import React, { PropsWithChildren } from 'react'
import {
  CommunityCard,
  CommunityCardFragment as Community,
} from '../CommunityCard'

interface Props {
  communities: Community[]
  joinCommunity: (id: string) => void
}

export const CommunityList: React.FC<PropsWithChildren<Props>> = (props) => {
  const { communities, joinCommunity, children } = props

  return communities.length !== 0 ? (
    <>
      {communities.map((community) => (
        <CommunityCard
          key={ community.id }
          community={ community }
          joinCommunity={ () => joinCommunity(community.id) }
        />
      ))}
    </>
  ) : (
    <>{children}</>
  )
}
