import React from 'react';
import { render, screen } from 'utils/test';
import { Layout } from './Layout';

describe('Layout', () => {
  it('renders', () => {
    render(<Layout>foo</Layout>);
    expect(screen.getByText('foo')).toBeInTheDocument();
  });
});
