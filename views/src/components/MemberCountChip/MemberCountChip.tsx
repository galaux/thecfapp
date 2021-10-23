import React, { FC } from 'react';
import { Users } from 'react-feather';
import { useTranslation } from 'react-i18next';
import Text from '../../atoms/Text';
import { BorderRadius, Colors, FontWeights } from '../../styles/theme';
import { Aligns, Directions, FontSize, Justifys } from '../../types';
import { Count, MemberCountChipWrapper } from './MemberCountChip.styled';
import { MemberCountChipProps } from './MemberCountChip.type';

const MemberCountChip: FC<MemberCountChipProps> = ({
  count,
  color = Colors.denimDarken,
}) => {
  const { t } = useTranslation();
  return (
    <MemberCountChipWrapper
      backgroundColor={ color }
      direction={ Directions.row }
      align={ Aligns.center }
      radius={ BorderRadius.Xsmall }
      justify={ Justifys.between }
      color={ Colors.white }
    >
      {
        count <= 10
          ? (
            <Text
              size={ FontSize.Xxsmall }
              weight={ FontWeights.Medium }
              uppercase
              spacing="0.4px"
            >
              {t('userCount_chip_new')}
            </Text>
          ) : (
            <>
              <Users size={ 12 } />
              <Count>{count}</Count>
            </>
          )
      }
    </MemberCountChipWrapper>
  );
};

export default MemberCountChip;
