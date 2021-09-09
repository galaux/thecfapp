import { createGlobalStyle } from 'styled-components';
import AauxNextBdWoff from './aauxnext-bd-webfont.woff';
import AauxNextBdWoff2 from './aauxnext-bd-webfont.woff2';
import AauxNextMdWoff from './aauxnext-md-webfont.woff';
import AauxNextMdWoff2 from './aauxnext-md-webfont.woff2';
import AauxNextRgWoff from './aauxnext-rg-webfont.woff';
import AauxNextRgWoff2 from './aauxnext-rg-webfont.woff2';
import AauxNextSmWoff from './aauxnext-sm-webfont.woff';
import AauxNextSmWoff2 from './aauxnext-sm-webfont.woff2';

const FontStyles = createGlobalStyle`
  @font-face {
    font-family: 'Aaux Next';
    src: url(${AauxNextRgWoff2}) format('woff2'),
         url(${AauxNextRgWoff}) format('woff');
    font-weight: 400;
    font-style: normal;
  }

  @font-face {
    font-family: 'Aaux Next';
    src: url(${AauxNextMdWoff2}) format('woff2'),
    url(${AauxNextMdWoff}) format('woff');
    font-weight: 500;
    font-style: normal;
  }
  
  @font-face {
    font-family: 'Aaux Next';
    src: url(${AauxNextSmWoff2}) format('woff2'),
    url(${AauxNextSmWoff}) format('woff');
    font-weight: 600;
    font-style: normal;
  }
  
  @font-face {
    font-family: 'Aaux Next';
    src: url(${AauxNextBdWoff2}) format('woff2'),
    url(${AauxNextBdWoff}) format('woff');
    font-weight: 700;
    font-style: normal;
  }
`;

export default FontStyles;
