import { responsiveFont, ResponsiveFonts } from '../../mixins/responsiveFont';
import { HeadingLevel } from './Heading.types';
import { heading } from '../../styles/theme';

export const getHeadingSize = (headingTag: string) => {
  const headingTheme = heading.level[(parseFloat(headingTag.replace(/\D/g, '')) as HeadingLevel)!];
  const responsiveItem = headingTheme.responsive as ResponsiveFonts;
  let result = '';

  if (headingTheme.font.weight) {
    result += `font-weight: ${headingTheme.font.weight};`;
  }

  if (responsiveItem) {
    result += responsiveFont(responsiveItem);
  }
  return result;
};
