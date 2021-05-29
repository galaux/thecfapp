// eslint-disable-next-line import/no-extraneous-dependencies
import { Colors } from '../../styles/theme'
import { TextAlign } from '../../types'
import Heading from './Heading'
import { HeadingProps } from './Heading.types'
import { Meta, Story } from '@storybook/react'
import React from 'react'

export default {
  title: 'atoms/Heading',
  argTypes: {
    level: {
      type: { name: 'number', required: true },
      defaultValue: 1,
    },
    textAlign: {
      control: {
        type: 'select',
        options: Object.values(TextAlign),
      },
      defaultValue: TextAlign.Left,
    },
    color: {
      control: {
        type: 'select',
        options: Object.values(Colors),
      },
    },
    margin: {
      type: { name: 'object', required: false },
      defaultValue: {},
    },
    pad: {
      type: { name: 'object', required: false },
      defaultValue: {},
    },
  },
} as Meta

const Template: Story<HeadingProps> = (props) => (
  <Heading {...props}>Heading</Heading>
)

export const Basic = Template.bind({})

Basic.args = {}
