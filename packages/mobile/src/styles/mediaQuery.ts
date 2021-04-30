import { css } from 'styled-components';

export enum Breakpoints {
  fallback = 'fallback',
  xs = 'xs',
  sm = 'sm',
  md = 'md',
  lg = 'lg',
}

type MediaBreakpoints = {
  [key in Breakpoints]: number;
};
export const mediaBreakpoints: MediaBreakpoints = {
  [Breakpoints.fallback]: 0,
  [Breakpoints.xs]: 576,
  [Breakpoints.sm]: 768,
  [Breakpoints.md]: 992,
  [Breakpoints.lg]: 1276,
};

type Media = {
  [key in Breakpoints]: typeof css;
};

export const media: Media = Object.values(Breakpoints).reduce((acc, label) => {
    /* eslint-disable  @typescript-eslint/no-explicit-any */
    acc[label] = (first: any, ...args: any[]) => css`
    @media (min-width: ${mediaBreakpoints[label] / 16}em) {
      ${css(first, ...args)}
    }
  `;
    return acc;
  },
  {} as Media);
