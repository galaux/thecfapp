import { responsiveFont, ResponsiveFonts } from '../../mixins/responsiveFont';
import { headingDefaultSize, headingSize, HeadingSize } from '../../styles/theme';
import { HeadingTag } from './Heading.types';

export const getHeadingStyling = (size: HeadingSize) => {
  const headingTheme = headingSize[size];
  const responsiveItem = headingTheme.responsive as ResponsiveFonts;
  let result = '';

  if (headingTheme) {
    result += `font-weight: ${headingTheme.weight};`;
  }

  if (responsiveItem) {
    result += responsiveFont(responsiveItem);
  }
  return result;
};

export const getHeadingDefaultSize = (headingTag: HeadingTag) => {
  const size = headingDefaultSize[headingTag];
  return getHeadingStyling(size);
};

export const getHeadingSize = (size: HeadingSize) => getHeadingStyling(size);
