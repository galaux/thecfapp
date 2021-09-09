import { ReactNode, CSSProperties } from 'react';
import { Colors, FontWeights, HeadingSize } from '../../styles/theme';
import { SideTypes, TextAlign } from '../../types';

export type HeadingLevel = 1 | 2 | 3 | 4 | 5 | 6;
export type HeadingTag = 'h1' | 'h2' | 'h3' | 'h4' | 'h5' | 'h6';

export type HeadingStyledProps = {
  margin?: SideTypes;
  pad?: SideTypes;
  color?: Colors;
  textAlign?: TextAlign;
  as: HeadingTag;
  size?: HeadingSize;
  weight?: FontWeights;
  uppercase?: boolean;
};

export type HeadingProps = Omit<HeadingStyledProps, 'as'> & {
  className?: string;
  id?: string;
  children: ReactNode;
  style?: CSSProperties;
  level: HeadingLevel;
};
