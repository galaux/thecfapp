import * as React from 'react'
import { WodListItem } from './WodListItem'
import SwipeableViews from 'react-swipeable-views'
import { UserWodFragment } from './user-wods.generated'

type Props = {
  selectedCommunityId: string | null
  wods: UserWodFragment[]
}

export const WodList: React.FC<Props> = (props) => {
  const wods =
    props.selectedCommunityId === null
      ? props.wods
      : props.wods.filter(
          (wod) => wod.community.id === props.selectedCommunityId,
        )

  return (
    <SwipeableViews
      style={{ padding: '0 1.5rem' }}
      slideStyle={{ paddingRight: '1rem' }}
      enableMouseEvents
    >
      {wods.map((wod) => (
        <WodListItem key={wod.id} wod={wod} />
      ))}
    </SwipeableViews>
  )
}
