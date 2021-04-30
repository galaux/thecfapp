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
