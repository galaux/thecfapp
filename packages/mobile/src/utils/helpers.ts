import {
  BORDER_RADIUS_MAP,
  EDGESIZE_MAP,
  SideRadiusTypes,
  SideTypes,
} from '../types'

function getSides(prop: SideTypes, cssPropName: 'padding' | 'margin'): string {
  let css = ''

  if (typeof prop === 'string') {
    css += `
    ${cssPropName}: ${prop === '0' ? '0' : EDGESIZE_MAP[prop]};
      `
  } else {
    if (prop.vertical) {
      css += `
      ${cssPropName}-top: ${
        prop.vertical === '0' ? '0' : EDGESIZE_MAP[prop.vertical]
      };
      ${cssPropName}-bottom: ${
        prop.vertical === '0' ? '0' : EDGESIZE_MAP[prop.vertical]
      };
      `
    }
    if (prop.horizontal) {
      css += `
      ${cssPropName}-left: ${
        prop.horizontal === '0' ? '0' : EDGESIZE_MAP[prop.horizontal]
      };
      ${cssPropName}-right: ${
        prop.horizontal === '0' ? '0' : EDGESIZE_MAP[prop.horizontal]
      };
      `
    }
    if (prop.top) {
      css += `${cssPropName}-top: ${
        prop.top === '0' ? '0' : EDGESIZE_MAP[prop.top]
      };`
    }
    if (prop.bottom) {
      css += `${cssPropName}-bottom: ${
        prop.bottom === '0' ? '0' : EDGESIZE_MAP[prop.bottom]
      };`
    }
    if (prop.right) {
      css += `${cssPropName}-right: ${
        prop.right === '0' ? '0' : EDGESIZE_MAP[prop.right]
      };`
    }
    if (prop.left) {
      css += `${cssPropName}-left: ${
        prop.left === '0' ? '0' : EDGESIZE_MAP[prop.left]
      };`
    }
  }
  return css
}

export function getPadding(padding?: SideTypes) {
  if (!padding) return ''
  return getSides(padding, 'padding')
}

export function getMargin(margin?: SideTypes) {
  if (!margin) return ''
  return getSides(margin, 'margin')
}

export function getBorderRadius(radius?: SideRadiusTypes): string {
  if (!radius) return ''
  let css = ''

  if (typeof radius === 'string') {
    css += `
    border-radius: ${radius === '0' ? '0' : BORDER_RADIUS_MAP[radius]};
    `
  } else {
    if (radius.top) {
      css += `
      border-top-left-radius: ${
        radius.top === '0' ? '0' : BORDER_RADIUS_MAP[radius.top]
      };
      border-top-right-radius: ${
        radius.top === '0' ? '0' : BORDER_RADIUS_MAP[radius.top]
      };
      `
    }

    if (radius.bottom) {
      css += `
      border-bottom-left-radius: ${
        radius.bottom === '0' ? '0' : BORDER_RADIUS_MAP[radius.bottom]
      };
      border-bottom-right-radius: ${
        radius.bottom === '0' ? '0' : BORDER_RADIUS_MAP[radius.bottom]
      };
      `
    }

    if (radius.left) {
      css += `
      border-top-left-radius: ${
        radius.left === '0' ? '0' : BORDER_RADIUS_MAP[radius.left]
      };
      border-bottom-left-radius: ${
        radius.left === '0' ? '0' : BORDER_RADIUS_MAP[radius.left]
      };
      `
    }

    if (radius.right) {
      css += `
      border-top-right-radius: ${
        radius.right === '0' ? '0' : BORDER_RADIUS_MAP[radius.right]
      };
      border-bottom-right-radius: ${
        radius.right === '0' ? '0' : BORDER_RADIUS_MAP[radius.right]
      };
      `
    }
  }

  return css
}
