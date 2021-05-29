import { BorderRadius, Colors } from '../../styles/theme'
import { Directions, Justifys, Size } from '../../types'
import { CardWrapper } from './Card.styled'
import React, { FC } from 'react'

export type CardProps = {
  className?: string
}

const Card: FC<CardProps> = ({ children, className }) => (
  <CardWrapper
    className={className}
    pad={Size.Medium}
    backgroundColor={Colors.gray80}
    radius={BorderRadius.Small}
    justify={Justifys.between}
    direction={Directions.column}
  >
    {children}
  </CardWrapper>
)

export default Card
