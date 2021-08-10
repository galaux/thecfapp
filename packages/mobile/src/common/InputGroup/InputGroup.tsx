import { Aligns, Directions } from '../../types'
import { InputGroupWrapper } from './InputGroup.styled'
import React, { FC } from 'react'

const InputGroup: FC = ({ children }) => (
  <InputGroupWrapper direction={Directions.row} align={Aligns.center}>
    {children}
  </InputGroupWrapper>
)

export default InputGroup
