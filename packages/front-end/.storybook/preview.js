import FontStyles from '../src/styles/fonts/fontStyles'
import GlobalStyle from '../src/styles/theme'

export const decorators = [
  (Story) => (
    <>
      <FontStyles />
      <GlobalStyle />
      <Story />
    </>
  ),
]

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
}
