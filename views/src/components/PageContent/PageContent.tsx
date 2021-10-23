import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import Text from '../../atoms/Text';
import Div from '../../common/Div';
import { Colors } from '../../styles/theme';
import { Directions, FontSize, Justifys, Size } from '../../types';
import { Content, PageContentWrapper } from './PageContent.styled';

const PageContent: FC = ({ children }) => {
  const { t } = useTranslation();
  return (
    <PageContentWrapper justify={ Justifys.between } direction={ Directions.column }>
      <Content>
        {children}
      </Content>
      <Div pad={ { horizontal: Size.Medium, vertical: Size.Large } }>
        <Text color={ Colors.white } size={ FontSize.Xxsmall }>
          {t('copyright_text')}
        </Text>
      </Div>
    </PageContentWrapper>
  );
};

export default PageContent;
