import { colors, edgeSize, FontWeights, text } from '../../styles/theme'
import React, { FC } from 'react'
import styled from 'styled-components'

const LabelWrapper = styled.label<{ $disabled: boolean }>`
  margin-bottom: ${edgeSize.xxsmall};
  font-size: ${text.xsmall.size};
  line-height: ${text.xsmall.height};
  font-weight: ${FontWeights.Medium};
  color: ${colors.gray50};
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  cursor: ${({ $disabled }) => ($disabled ? 'default' : 'pointer')};
`

export type LabelProps = {
  htmlFor: string
  className?: string
  disabled?: boolean
}

const Label: FC<LabelProps> = ({
  htmlFor,
  children,
  disabled = false,
  className,
}) => (
  <LabelWrapper className={className} htmlFor={htmlFor} $disabled={disabled}>
    {children}
  </LabelWrapper>
)

export default Label
