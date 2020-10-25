import * as React from 'react';
import { render, RenderResult } from '@testing-library/react';

const Providers: React.FC = ({ children }) => {
  return <>{children}</>;
  // return (
  //   <ThemeProvider>
  //     {children}
  //   </ThemeProvider>
  // )
};

const renderUi = (ui: React.ReactElement, options = {}): RenderResult =>
  render(ui, { wrapper: Providers, ...options });

export * from '@testing-library/react';
export { renderUi as render };
