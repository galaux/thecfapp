import Label from '../../atoms/Label'
import Div from '../../common/Div'
import { Directions, InputType } from '../../types'
import { InputStyled } from './Input.styled'
import React, { FC } from 'react'

export type InputProps = {
  label?: string
  id: string
  onChange: (value: string) => void
  value: string
  disabled?: boolean
  type?: InputType
}

const Input: FC<InputProps> = ({
  label,
  id,
  onChange,
  value,
  disabled = false,
  type = InputType.Text,
  ...rest
}) => (
  <Div direction={Directions.column}>
    {label ? (
      <Label htmlFor={id} disabled={disabled}>
        {label}
      </Label>
    ) : null}
    <InputStyled
      {...rest}
      onChange={(event) => onChange(event.target.value)}
      value={value}
      type={type}
      id={id}
      disabled={disabled}
    />
  </Div>
)

export default Input
