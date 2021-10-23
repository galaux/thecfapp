import { Router, RouterProps } from '@reach/router';
import { FC } from 'react';

const LayoutRouterComponent: FC = ({ children }) => <>{children}</>;

const LayoutRouter: FC<Omit<RouterProps, 'primary'>> = ({
  children,
  ...restProps
}) => (
  <Router primary={ false } component={ LayoutRouterComponent } { ...restProps }>
    {children}
  </Router>
);

export default LayoutRouter;
