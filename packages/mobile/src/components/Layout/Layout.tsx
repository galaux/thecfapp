import Text from '../../atoms/Text'
import Div from '../../common/Div'
import { Colors } from '../../styles/theme'
import { Directions, FontSize, Justifys, Size } from '../../types'
import { COPYRIGHT_STR } from '../../utils'
import { LayoutWrapper } from './Layout.styled'
import * as React from 'react'

export const Layout: React.FC = ({ children }) => (
  <LayoutWrapper justify={Justifys.between} direction={Directions.column}>
    <Div>{children}</Div>
    <Div pad={{ horizontal: Size.Medium, vertical: Size.Large }}>
      <Text color={Colors.white} size={FontSize.Xxsmall}>
        {COPYRIGHT_STR}
      </Text>
    </Div>
  </LayoutWrapper>
)
