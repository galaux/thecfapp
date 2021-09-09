import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';
import { hexToRgb } from '../mixins/hexToRgb';

export enum Colors {
  primary = 'primary',
  secondary = 'secondary',
  denim = 'denim',
  denimDarken = 'denimDarken',
  gray10 = 'gray10',
  gray30 = 'gray30',
  gray50 = 'gray50',
  gray60 = 'gray60',
  gray80 = 'gray80',
  gray90 = 'gray90',
  white = 'white',
  boxShadow = 'boxShadow',
  background = 'background',
  error = 'error',
}

export const colors = {
  [Colors.primary]: '#333333',
  [Colors.secondary]: '#333000',
  [Colors.denim]: '#1160D6',
  [Colors.denimDarken]: '#204582',
  [Colors.gray10]: '#DADADB',
  [Colors.gray30]: '#C1BFC1',
  [Colors.gray50]: '#808080',
  [Colors.gray60]: '#484848',
  [Colors.gray80]: '#2F2A2F',
  [Colors.gray90]: '#1A151A',
  [Colors.white]: '#FFFFFF',
  [Colors.boxShadow]: '#FFFFFF',
  [Colors.background]: '#1D1D22',
  [Colors.error]: '#F42626',
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
  xxlarge: '2.5rem',
};

export enum FontWeights {
  Regular = 400,
  Medium = 500,
  SemiBold = 600,
  Bold = 700,
}

export enum BorderRadius {
  Xxxsmall = 'xxxsmall',
  Xxsmall = 'xxsmall',
  Xsmall = 'xsmall',
  Small = 'small',
  Medium = 'medium',
  Large = 'large',
  Xlarge = 'xlarge',
  Round = 'round',
}

export const borderRadius = {
  [BorderRadius.Xxxsmall]: '.25rem',
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
  MediumLight = 'mediumLight',
  MediumDark = 'mediumDark',
}

export const boxShadow = {
  [BoxShadow.MediumLight]: `0px 4px 8px rgba(${hexToRgb(colors.gray90)},0.12)`,
  [BoxShadow.MediumDark]: `0px 4px 8px rgba(${hexToRgb(colors.gray90)},1)`,
};

export const text = {
  xxsmall: {
    size: '.75rem',
    height: '1.4',
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

export enum HeadingSize {
  Xxxsmall = 'xxxsmall',
  Xxsmall = 'xxsmall',
  Xsmall = 'xsmall',
  XsmallLight = 'xsmall-light',
  Small = 'small',
  SmallLight = 'small-light',
  Medium = 'medium',
  Large = 'large',
  Xlarge = 'xlarge',
}

export const headingSize = {
  [HeadingSize.Xxxsmall]: {
    weight: 600,
    responsive: {
      default: {
        size: '0.875rem',
        height: '1.5',
      },
    },
  },
  [HeadingSize.Xxsmall]: {
    weight: 600,
    responsive: {
      default: {
        size: '1rem',
        height: '1.5',
      },
    },
  },
  [HeadingSize.Xsmall]: {
    weight: 600,
    responsive: {
      default: {
        size: '1.125rem',
        height: '1.5',
      },
    },
  },
  [HeadingSize.XsmallLight]: {
    weight: 400,
    responsive: {
      default: {
        size: '1.125rem',
        height: '1.5',
      },
    },
  },
  [HeadingSize.Small]: {
    weight: 600,
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
  [HeadingSize.SmallLight]: {
    weight: 600,
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
  [HeadingSize.Medium]: {
    weight: 600,
    responsive: {
      default: {
        size: '1.25rem',
        height: '1.5',
      },
      md: {
        size: '1.75rem',
        height: '1.25',
      },
    },
  },
  [HeadingSize.Large]: {
    weight: 600,
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
  [HeadingSize.Xlarge]: {
    weight: 600,
    responsive: {
      default: {
        size: '2.25rem',
        height: '1.25',
      },
      md: {
        size: '3.25rem',
        height: '1.25',
      },
    },
  },
};

export const headingDefaultSize = {
  h1: HeadingSize.Xlarge,
  h2: HeadingSize.Large,
  h3: HeadingSize.Medium,
  h4: HeadingSize.Small,
  h5: HeadingSize.Xsmall,
  h6: HeadingSize.Xxsmall,
};

export const GlobalStyle = createGlobalStyle`
  ${reset}
  
  html, body, #root {
    height: 100%;
  }
  #root{
    >div {
      height: 100%;
    }
  }
  body {
    font-family: 'Aaux Next', sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
  *, *:before, *:after {
    box-sizing: border-box;
  }
`;

export default GlobalStyle;
