import React, { FC } from 'react';
import Div from '../../common/Div';
import { Size } from '../../types';
import CommunityCard from '../CommunityCard/CommunityCard';
import { CommunityListProps } from './CommunityList.types';

const CommunityList: FC<CommunityListProps> = ({ communities }) => (
  <Div margin={ { bottom: Size.Xxxlarge } }>
    {
      communities.map((community) => (
        <CommunityCard
          key={ community.id }
          community={ community }
        />
      ))
    }
  </Div>
);

export default CommunityList;
