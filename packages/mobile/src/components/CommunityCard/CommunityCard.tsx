import React, { FC } from 'react'
import { CommunityCardFragment as Community } from './community-card.generated'
import { Link, useNavigate } from '@reach/router'
import UserCountChip from '../UserCountChip'
import { Aligns, ButtonType, Directions, Justifys, Size } from '../../types';
import Button from '../../atoms/Button';
import Div from '../../common/Div';
import Card from '../../atoms/Card';
import { Address, CommunityName } from './CommunityCard.styled';

interface CommunityCardProps {
  community: Community
  joinCommunity: () => void
}

export const CommunityCard: FC<CommunityCardProps> = ({ community, joinCommunity }) => {
  const navigate = useNavigate()

  const join = () => {
    joinCommunity();
    navigate(`/community/${community.id}`)
  }

  return (
    <Card>
      <Div direction={ Directions.column }>
        <Div direction={ Directions.row } align={ Aligns.start } margin={ {bottom: Size.Xxsmall} } justify={ Justifys.between }>
          <CommunityName>{community.name}</CommunityName>
          <UserCountChip count={ community.memberCount } />
        </Div>
        {community.address ? (
          <Div direction={ Directions.column } margin={ { bottom: Size.Xxsmall} }>
            {community.address.street ? <Address>{community.address.street}</Address> : null }
            {community.address.zipCode || community.address.city ? <Address>{community.address.zipCode} {community.address.city}</Address> : null }
            {community.address.zipCode ? <Address>{community.address.country}</Address> : null }
          </Div>
        ) : null}
      </Div>
      <Div justify={ Justifys.end }>
        {community.isMember ? (
          <Link to={ `/community/${community.id}` }>See the community</Link>
        ) : (
          <Button type={ ButtonType.button } onClick={ join }>Join</Button>
        )}
      </Div>
    </Card>
  )
}
