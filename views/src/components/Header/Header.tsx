import React, { FC } from 'react';
import Heading from '../../atoms/Heading';
import Div from '../../common/Div';
import { Colors, FontWeights, HeadingSize } from '../../styles/theme';
import { Size } from '../../types';
import NavBar from '../NavBar';
import { Content, HeaderWrapper } from './Header.styled';
import { HeaderProps } from './Header.types';

const Header: FC<HeaderProps> = ({ children, title, subtitle }) => (
  <HeaderWrapper>
    <NavBar />
    <Content>
      <Heading
        level={ 2 }
        color={ Colors.gray50 }
        size={ HeadingSize.Xxxsmall }
        weight={ FontWeights.Regular }
        margin={ { bottom: Size.Xxxsmall } }
      >
        {subtitle}
      </Heading>
      <Heading
        level={ 1 }
        size={ HeadingSize.Xsmall }
        uppercase
        margin={ { bottom: Size.Xxsmall } }
      >
        {title}
      </Heading>
      <Div>{children}</Div>
    </Content>
  </HeaderWrapper>
);

export default Header;
