import { mediaBreakpoints, Breakpoints } from '../styles/mediaQuery'

type ResponsiveFont = {
  size: string
  height: string
}

export type ResponsiveFonts = {
  [breakpoint: string]: ResponsiveFont
}

// Utility function for making responsive font
export const responsiveFont = (responsiveItem: ResponsiveFonts) => {
  let result = ''
  Object.entries(responsiveItem).forEach(([breakpoint]) => {
    if (breakpoint === 'default') {
      result += `
        font-size: ${responsiveItem[breakpoint].size};
        line-height: ${responsiveItem[breakpoint].height};
      `
    } else {
      result += `
        @media (min-width: ${
          mediaBreakpoints[breakpoint as Breakpoints] / 16
        }em) {
          font-size: ${responsiveItem[breakpoint].size};
          line-height: ${responsiveItem[breakpoint].height};
        }
      `
    }
  })
  return result
}
