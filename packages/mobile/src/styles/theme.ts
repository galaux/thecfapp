import { createGlobalStyle } from 'styled-components';
import { hexToRgb } from '../mixins/hexToRgb';

export enum Colors {
  primary = 'primary',
  secondary = 'secondary',
  gray6 = 'gray6',
  gray5 = 'gray5',
  gray4 = 'gray4',
  gray3 = 'gray3',
  gray2 = 'gray2',
  gray1 = 'gray1',
  white = 'white',
  boxShadow = 'boxShadow',
}

export const colors = {
  [Colors.primary]: '#333333',
  [Colors.secondary]: '#333000',
  [Colors.gray6]: '#EEF0F5',
  [Colors.gray5]: '#1F252E',
  [Colors.gray4]: '#57667E',
  [Colors.gray3]: '#A9B4C5',
  [Colors.gray2]: '#DCE0E7',
  [Colors.gray1]: '#F0F2F5',
  [Colors.white]: '#FFFFFF',
  [Colors.boxShadow]: '#FFFFFF',
};

export const edgeSize = {
  xxxsmall: '0.25rem',
  xxsmall: '0.5rem',
  xsmall: '0.75rem',
  small: '1rem',
  medium: '1.5rem',
  large: '2rem',
  xlarge: '3rem',
  xxlarge: '4rem',
};

export enum BorderRadius {
  Xsmall = 'xsmall',
  Small = 'small',
  Medium = 'medium',
  Large = 'large',
  Xlarge = 'xlarge',
  Round = 'round',
}

export const borderRadius = {
  [BorderRadius.Xsmall]: '.25rem',
  [BorderRadius.Small]: '.5rem',
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
  Medium = 'medium',
  Large = 'large',
  Down = 'down',
  NavBar = 'navBar',
  Reverse= 'reverse',
}

export const boxShadow = {
  [BoxShadow.Medium]: `0px 4px 60px 0px rgba(${hexToRgb(colors.boxShadow)},0.15)`,
  [BoxShadow.Large]: `0px 15px 46px 0px rgba(${hexToRgb(colors.boxShadow)},0.24)`,
  [BoxShadow.Down]: `0px 12px 8px -4px rgba(${hexToRgb(colors.boxShadow)},0.05)`,
  [BoxShadow.NavBar]: `0 6px 6px rgba(${hexToRgb(colors.gray2)},0.25)`,
  [BoxShadow.Reverse]: `0px -3px 19px 0px rgba(${hexToRgb(colors.gray5)},0.13)`,
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
  }
`;

export default GlobalStyle;
