import { edgeSize } from '../styles/theme';

export enum Size {
  Xxxsmall = 'xxxsmall',
  Xxsmall = 'xxsmall',
  Xsmall = 'xsmall',
  Small = 'small',
  Medium = 'medium',
  Large = 'large',
  Xlarge = 'xlarge',
  Xxlarge = 'xxlarge',
}

export type EdgeSizeTypes = {
  [k in Size]: string;
};

export const EDGESIZE_MAP: EdgeSizeTypes = {
  [Size.Xxxsmall]: edgeSize[Size.Xxxsmall],
  [Size.Xxsmall]: edgeSize[Size.Xxsmall],
  [Size.Xsmall]: edgeSize[Size.Xsmall],
  [Size.Small]: edgeSize[Size.Small],
  [Size.Medium]: edgeSize[Size.Medium],
  [Size.Large]: edgeSize[Size.Large],
  [Size.Xlarge]: edgeSize[Size.Xlarge],
  [Size.Xxlarge]: edgeSize[Size.Xxlarge],
};

export type AllSides = 'vertical' | 'horizontal' | 'right' | 'left' | 'bottom' | 'top';
export enum None { none = '0' }
export type Sides = Record<AllSides, Size | None>;
export type SideTypes = Size | None | Partial<Sides>;

export enum TextAlign {
  Left = 'left',
  Right = 'right',
  Center = 'center',
}
