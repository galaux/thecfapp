import { fireEvent, render, screen } from '@testing-library/react'
import { ApolloError } from '@apollo/client'
import { fakeWorkoutFieldsFragment, mapRandomTimes } from '../../test-utils'
import { fakeScoreForWodCard } from '../WodCard/test-utils'
import { NewScorePanel } from './NewScorePanel'
import { WodForNewScorePanelFragment } from './new-score-panel.generated'
import { fakeWodFieldsFragment } from '../../test-utils'
import faker from 'faker'
import { withoutConsoleErrors } from '../../test-utils'

function fakeWodForNewScorePanel(): WodForNewScorePanelFragment {
  return {
    ...fakeWodFieldsFragment(),
    scores: mapRandomTimes(fakeScoreForWodCard),
    workout: fakeWorkoutFieldsFragment(),
  }
}

describe('User WODs panel', () => {
  const wod = fakeWodForNewScorePanel()
  const createScoreSpy = jest.fn()

  beforeEach(jest.resetAllMocks)

  it('renders the initial panel', () => {
    render(
      <NewScorePanel
        wod={ wod }
        createScore={ createScoreSpy }
        createLoading={ false }
        createError={ undefined }
      />,
    )

    expect(
      screen.getByText(
        (_: HTMLElement, node: Node) =>
          node.textContent === wod.workout.description,
      ),
    ).toBeInTheDocument()
    expect(
      screen.getByRole('heading', {
        name: /add my score/i,
      }),
    ).toBeInTheDocument()
    expect(screen.getByRole('spinbutton')).toBeInTheDocument()
    expect(
      screen.getByRole('button', {
        name: /add my score/i,
      }),
    ).toBeInTheDocument()
    expect(createScoreSpy).not.toHaveBeenCalled()
  })

  it('invokes the create function when button is clicked', () => {
    render(
      <NewScorePanel
        wod={ wod }
        createScore={ createScoreSpy }
        createLoading={ false }
        createError={ undefined }
      />,
    )
    const input = screen.getByRole('spinbutton')
    const value = faker.random.number({ min: 1, max: 1000 })
    fireEvent.change(input, { target: { value } })
    fireEvent.click(screen.getByRole('button', { name: /add my score/i }))
    expect(createScoreSpy).toHaveBeenCalledWith(wod.id, value)
  })

  it('displays the spinner when passed so', () => {
    render(
      <NewScorePanel
        wod={ wod }
        createScore={ createScoreSpy }
        createLoading={ true }
        createError={ undefined }
      />,
    )
    expect(screen.getByText(/loading\.\.\./i)).toBeInTheDocument()
  })

  it('displays the error when passed one', () => {
    withoutConsoleErrors(() => {
      render(
        <NewScorePanel
          wod={ wod }
          createScore={ createScoreSpy }
          createLoading={ false }
          createError={ new ApolloError({ errorMessage: 'BOOM' }) }
        />,
      )
      expect(createScoreSpy).not.toHaveBeenCalled()
      expect(
        screen.getByText(/Oups, something went wrong/i),
      ).toBeInTheDocument()
    })
  })
})
