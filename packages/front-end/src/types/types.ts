import { BorderRadius, borderRadius, Colors, edgeSize } from '../styles/theme';

export enum Size {
  Xxxsmall = 'xxxsmall',
  Xxsmall = 'xxsmall',
  Xsmall = 'xsmall',
  Small = 'small',
  Medium = 'medium',
  Large = 'large',
  Xlarge = 'xlarge',
  Xxlarge = 'xxlarge',
  Xxxlarge = 'xxxlarge',
}

export type BorderProps = Partial<{
  color: Colors
  width: string
  style: string
  side: string
}>;

export type EdgeSizeTypes = {
  [k in Size]: string
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
  [Size.Xxxlarge]: edgeSize[Size.Xxxlarge],
};

export type AllSides =
  | 'vertical'
  | 'horizontal'
  | 'right'
  | 'left'
  | 'bottom'
  | 'top';
export enum None {
  none = '0',
}
export type Sides = Record<AllSides, Size | None>;
export type SideTypes = Size | None | Partial<Sides>;
export type SidesRadius = Record<AllSides, BorderRadius | None>;
export type SideRadiusTypes = BorderRadius | None | Partial<SidesRadius>;

export enum TextAlign {
  Left = 'left',
  Right = 'right',
  Center = 'center',
}

export enum FontSize {
  Xxsmall = 'xxsmall',
  Xsmall = 'xsmall',
  Small = 'small',
  Medium = 'medium',
  Large = 'large',
}

export enum Directions {
  row = 'row',
  column = 'column',
  rowReverse = 'row-reverse',
  columnReverse = 'column-reverse',
}

export type HoverTypes = {
  background?: Colors
};

export enum Justifys {
  around = 'space-around',
  between = 'space-between',
  center = 'center',
  end = 'flex-end',
  evenly = 'space-evenly',
  start = 'flex-start',
}

export enum Aligns {
  baseline = 'baseline',
  center = 'center',
  end = 'flex-end',
  start = 'flex-start',
  stretch = 'stretch',
}

export enum Overflows {
  visible = 'visible',
  hidden = 'hidden',
}

export enum Positions {
  relative = 'relative',
  absolute = 'absolute',
  fixed = 'fixed',
}

type BorderRadiusTypes = typeof borderRadius;

export const BORDER_RADIUS_MAP: BorderRadiusTypes = {
  [BorderRadius.Xxxsmall]: borderRadius[BorderRadius.Xxxsmall],
  [BorderRadius.Xxsmall]: borderRadius[BorderRadius.Xxsmall],
  [BorderRadius.Xsmall]: borderRadius[BorderRadius.Xsmall],
  [BorderRadius.Small]: borderRadius[BorderRadius.Small],
  [BorderRadius.Medium]: borderRadius[BorderRadius.Medium],
  [BorderRadius.Large]: borderRadius[BorderRadius.Large],
  [BorderRadius.Xlarge]: borderRadius[BorderRadius.Xlarge],
  [BorderRadius.Round]: borderRadius[BorderRadius.Round],
};

export enum ButtonType {
  submit = 'submit',
  reset = 'reset',
  button = 'button',
}

export enum ButtonVariant {
  Primary = 'primary',
  Secondary = 'secondary',
}

export enum LinkVariant {
  Default = 'default',
}

export enum InputType {
  Text = 'text',
  Password = 'password',
}
