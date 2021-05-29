import { Colors, FontWeights } from '../../styles/theme'
import { SideTypes, TextAlign } from '../../types'
import { ReactNode, CSSProperties } from 'react'

export type HeadingLevel = 1 | 2 | 3 | 4 | 5 | 6

export type HeadingStyledProps = {
  level: HeadingLevel
  margin?: SideTypes
  pad?: SideTypes
  color?: Colors
  textAlign?: TextAlign
  as?: string
  weight?: FontWeights
}

export type HeadingProps = HeadingStyledProps & {
  className?: string
  id?: string
  children: ReactNode
  style?: CSSProperties
}
