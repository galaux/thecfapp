// Transform px to rem
import { font } from '../styles/theme';

export const pxToRem = (px: number) => {
  const result = (px / font.defaultSize).toFixed(2);
  return result || null;
};
