import * as React from 'react';
import { Story, Meta } from '@storybook/react';

import { Topbar } from './Topbar';

export default {
  title: 'Topbar',
  component: Topbar,
} as Meta;

const Wrapper: React.FC = (props) => (
  <div style={{ backgroundColor: 'rgba(0,0,0,0.1)' }} {...props} />
);

const Template: Story = (props) => (
  <Wrapper>
    <Topbar {...props} />
  </Wrapper>
);

export const Default = Template.bind({});
