import * as React from 'react'
import { WodForSwipeListFragment as Wod } from './wod-swipe-list.generated'
import { WodCard } from '../WodCard'
import SwipeableViews from 'react-swipeable-views'

type Props = {
  selectedCommunityId: string | null
  wods: Wod[]
}

export const WodSwipeList: React.FC<Props> = (props) => {
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
        <WodCard key={wod.id} wod={wod} />
      ))}
    </SwipeableViews>
  )
}
