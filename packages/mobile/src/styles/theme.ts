import { createGlobalStyle } from 'styled-components';
import { hexToRgb } from '../mixins/hexToRgb';

export enum Colors {
  primary = 'primary',
  secondary = 'secondary',
  denim = 'denim',
  gray30 = 'gray30',
  gray50 = 'gray50',
  gray80 = 'gray80',
  gray90 = 'gray90',
  white = 'white',
  boxShadow = 'boxShadow',
}

export const colors = {
  [Colors.primary]: '#333333',
  [Colors.secondary]: '#333000',
  [Colors.denim]: '#1160D6',
  [Colors.gray30]: '#C1BFC1',
  [Colors.gray50]: '#808080',
  [Colors.gray80]: '#2F2A2F',
  [Colors.gray90]: '#1A151A',
  [Colors.white]: '#FFFFFF',
  [Colors.boxShadow]: '#FFFFFF',
};

export const font = {
  family: 'Aaux Next, serif',
  color: 'white',
  weight: 300,
  defaultSize: 16,
};

export const edgeSize = {
  xxxsmall: '0.25rem',
  xxsmall: '0.5rem',
  xsmall: '0.75rem',
  small: '1rem',
  medium: '1.25rem',
  large: '1.5rem',
  xlarge: '2rem',
  xxlarge: '3rem',
};

export enum FontWeights {
  Regular = 400,
  Medium = 500,
  SemiBold = 600,
  Bold = 700,
};

export enum BorderRadius {
  Xxsmall = 'xxsmall',
  Xsmall = 'xsmall',
  Small = 'small',
  Medium = 'medium',
  Large = 'large',
  Xlarge = 'xlarge',
  Round = 'round',
}

export const borderRadius = {
  [BorderRadius.Xxsmall]: '.5rem',
  [BorderRadius.Xsmall]: '.625rem',
  [BorderRadius.Small]: '.75rem',
  [BorderRadius.Medium]: '1rem',
  [BorderRadius.Large]: '2rem',
  [BorderRadius.Xlarge]: '3rem',
  [BorderRadius.Round]: '50%',
};

export enum BorderWidth {
  Medium = 'medium',
  Large = 'large',
}

export const borderWidth = {
  [BorderWidth.Medium]: '1px',
  [BorderWidth.Large]: '2px',
};

export enum BoxShadow {
  Card = 'card',
}

export const boxShadow = {
  [BoxShadow.Card]: `0px 4px 8px rgba(${hexToRgb(colors.gray90)},0.12)`,
};

export const heading = {
  level: {
    1: {
      font: {
        weight: 500,
      },
      responsive: {
        default: {
          size: '1.75rem',
          height: '1.25',
        },
        md: {
          size: '3.25rem',
          height: '1.25',
        },
      },
    },
    2: {
      font: {
        weight: 500,
      },
      responsive: {
        default: {
          size: '1.5rem',
          height: '1.25',
        },
        md: {
          size: '2.5rem',
          height: '1.25',
        },
      },
    },
    3: {
      font: {
        weight: 500,
      },
      responsive: {
        default: {
          size: '1.25rem',
          height: '1.5',
        },
        md: {
          size: '1.75rem',
          height: '1.5',
        },
      },
    },
    4: {
      font: {
        weight: 500,
      },
      responsive: {
        default: {
          size: '1.125rem',
          height: '1.5',
        },
        md: {
          size: '1.25rem',
          height: '1.5',
        },
      },
    },
    5: {
      font: {
        weight: 500,
      },
      responsive: {
        default: {
          size: '1.125rem',
          height: '1.5',
        },
      },
    },
    6: {
      font: {
        weight: 400,
      },
      responsive: {
        default: {
          size: '1rem',
          height: '1.5',
        },
      },
    },
  },
};

export const text = {
  xxsmall: {
    size: '.75rem',
    height: '1.2',
  },
  xsmall: {
    size: '.875rem',
    height: '1.5',
  },
  small: {
    size: '1rem',
    height: '1.5',
  },
  medium: {
    size: '1.25rem',
    height: '1.5',
  },
  large: {
    size: '1.5rem',
    height: '1.6',
  },
};

export const GlobalStyle = createGlobalStyle`
  body {
    font-family: 'Aaux Next', sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
`;

export default GlobalStyle;
