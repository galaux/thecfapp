import React from 'react';
import styled from 'styled-components';
import {
  Aligns,
  BorderProps,
  Directions,
  FontSize,
  HoverTypes,
  Justifys,
  Overflows,
  Positions,
  SideRadiusTypes,
  SideTypes,
} from '../../types';
import { getBorderRadius, getMargin, getPadding } from '../../utils/helpers';
import { borderWidth, BoxShadow, boxShadow, colors, Colors, text } from '../../styles/theme';

export type DivProps = React.ComponentPropsWithoutRef<'div'> & Partial<{
  size: string;
  width: string;
  height: string;
  maxWidth: string;
  minHeight: string;
  align: Aligns;
  backgroundColor: Colors;
  color: Colors;
  border: BorderProps;
  direction: Directions;
  flex: string;
  justify: Justifys;
  margin: SideTypes;
  pad: SideTypes;
  radius: SideRadiusTypes;
  hoverIndicator: HoverTypes;
  shadow: BoxShadow;
  wrap: string | boolean;
  overflow: Overflows;
  position: Positions;
  fontSize: FontSize;
}>;

export type StyledDivProps = React.ComponentPropsWithoutRef<'div'> & Partial<{
  $size: string;
  $width: string;
  $height: string;
  $maxWidth: string;
  $minHeight: string;
  $align: Aligns;
  $backgroundColor: Colors;
  $color: Colors;
  $border: BorderProps;
  $direction: Directions;
  $flex: string;
  $justify: Justifys;
  $margin: SideTypes;
  $pad: SideTypes;
  $radius: SideRadiusTypes;
  $hoverIndicator: HoverTypes;
  $shadow: BoxShadow;
  $wrap: boolean;
  $overflow: Overflows;
  $position: Positions;
  $fontSize: FontSize;
}>;

const transformedDivProps: string[] = [
  'size',
  'width',
  'height',
  'maxWidth',
  'minHeight',
  'align',
  'color',
  'backgroundColor',
  'border',
  'direction',
  'flex',
  'hoverIndicator',
  'justify',
  'margin',
  'pad',
  'radius',
  'shadow',
  'wrap',
  'overflow',
  'position',
  'fontSize',
];

const transformDivProps = (props: DivProps): StyledDivProps => Object.entries(props).reduce(
  (p, [key, value]) => ({
    ...p,
    [transformedDivProps.includes(key) ? `$${key}` : key]: value,
  }), {},
);

const StyledDiv = styled.div<StyledDivProps>`
  ${(props) => props.$size && `
    width: ${props.$size};
    height: ${props.$size};
  `}
  ${(props) => props.$width && `
    width: ${props.$width};
  `}
  ${(props) => props.$height && `
    height: ${props.$height};
  `}
  ${(props) => props.$maxWidth && `
    max-width: ${props.$maxWidth};
  `}
  ${(props) => props.$minHeight && `
    min-height: ${props.$minHeight};
  `}
  ${(props) => (props.$align || props.$direction || props.$flex || props.$justify || props.$wrap) && `
    display: flex;
  `}
  ${(props) => props.$align && `
    align-items: ${props.$align};
  `}
  ${(props) => props.$color && `
    color: ${colors[props.$color]};
  `}
  ${(props) => props.$backgroundColor && `
    background-color: ${colors[props.$backgroundColor]};
  `}
  ${(props) => props.$border && `
    border-style: ${(props.$border.style ? props.$border.style : 'solid')};
    border-width: ${(props.$border.width ? props.$border.width : borderWidth.medium)};
    border-color: ${(props.$border.color ? colors[props.$border.color] : colors.gray80)};
  `}
  ${(props) => props.$flex && `
    flex: ${props.$flex};
  `}
  ${(props) => props.onClick && `
    cursor: pointer;
  `}
  ${(props) => props.$direction && `
    flex-direction: ${props.$direction};
  `}
  ${(props) => props.$wrap && `
    flex-wrap: wrap;
  `}
  ${(props) => props.$justify && `
    justify-content: ${props.$justify};
  `}
  ${(props) => props.$overflow && `
    overflow: ${props.$overflow};
  `}
  ${(props) => props.$position && `
    position: ${props.$position};
  `}
  ${(props) => props.$fontSize && `
    font-size: ${text[props.$fontSize].size};
    line-height: ${text[props.$fontSize].height};
  `}

  ${(props) => getMargin(props.$margin)}
  ${(props) => getPadding(props.$pad)}
  ${(props) => getBorderRadius(props.$radius)}

  ${(props) => props.$shadow && `
    box-shadow: ${boxShadow[props.$shadow]};
  `}
  
  ${(props) => props.$hoverIndicator && props.$hoverIndicator.background && `
    &:hover {
      background: ${colors[props.$hoverIndicator.background]};
    }
  `}
`;

const Div = React.forwardRef<HTMLDivElement, DivProps>((
  { children, ...props },
  ref,
) => (
  <StyledDiv ref={ ref } { ...transformDivProps(props) }>
    { children }
  </StyledDiv>
));

export default Div;
