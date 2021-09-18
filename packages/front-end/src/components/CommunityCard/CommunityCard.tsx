import { useNavigate } from '@reach/router';
import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import Button from '../../atoms/Button';
import Card from '../../atoms/Card';
import Link from '../../atoms/Link';
import Div from '../../common/Div';
import { Aligns, ButtonType, ButtonVariant, Directions, Justifys, Size } from '../../types';
import MemberCountChip from '../MemberCountChip';
import { Address, CommunityName } from './CommunityCard.styled';
import { CommunityCardProps } from './CommunityCard.types';

const CommunityCard: FC<CommunityCardProps> = ({ community }) => {
  const { t } = useTranslation();
  const navigate = useNavigate();

  const handleJoin = () => {
    navigate(`/community/${community.id}`);
  };

  return (
    <Card>
      <Div direction={ Directions.column }>
        <Div
          direction={ Directions.row }
          align={ Aligns.start }
          margin={ { bottom: Size.Xxsmall } }
          justify={ Justifys.between }
        >
          <CommunityName>{community.name}</CommunityName>
          <MemberCountChip count={ community.memberCount } />
        </Div>
        {
          community.address ? (
            <Div direction={ Directions.column } margin={ { bottom: Size.Xxsmall } }>
              {
                community.address.street ? (
                  <Address>{community.address.street}</Address>
                ) : null
              }
              {
                community.address.zipCode || community.address.city ? (
                  <Address>
                    {community.address.zipCode}
                    {' '}
                    {community.address.city}
                  </Address>
                ) : null
              }
              {
                community.address.zipCode ? (
                  <Address>{community.address.country}</Address>
                ) : null
              }
            </Div>
          ) : null
        }
      </Div>
      <Div justify={ Justifys.end }>
        {
          community.isMember ? (
            <Link
              variant={ ButtonVariant.Primary }
              to={ `/community/${community.id}` }
            >
              {t('communityCard_button_seeCommunity')}
            </Link>
          ) : (
            <Button type={ ButtonType.button } onClick={ handleJoin }>
              {t('communityCard_button_join')}
            </Button>
          )
        }
      </Div>
    </Card>
  );
};

export default CommunityCard;
