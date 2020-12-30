import { render, screen } from '@testing-library/react'
import { v4 as uuid } from 'uuid'
import { Score, ScoringType, Workout } from '../../types'
import { UserWodsPanel } from './UserWodsPanel'
import { UserWodPanelFragment as Wod } from './user-wod-panel.generated'
import { CommunityForSwipeListFragment as Community } from '../WodSwipeList/wod-swipe-list.generated'
import { ApolloError } from '@apollo/client'
import faker from 'faker'
import { range } from 'lodash'
import { withoutConsoleErrors } from '../../test-utils'

describe('User WODs panel', () => {
  function fakeWorkout(): Workout {
    return {
      id: uuid(),
      name: faker.fake('{{name.firstName}} {{name.lastName}}'),
      description: faker.lorem.text(),
      scoringType: ScoringType.Repetitions,
    }
  }

  function fakeCommunity(): Community {
    return {
      id: uuid(),
      name: faker.random.words(5),
    }
  }

  function fakeWod() {
    return {
      id: uuid(),
      workout: fakeWorkout(),
      date: new Date('2020-12-12'),
      scores: [] as Score[],
      community: fakeCommunity(),
    } as Wod
  }

  const wods = range(faker.random.number({ min: 1, max: 5 })).map((_) =>
    fakeWod(),
  )

  // TODO
  /* it('displays the menu icon', () => {
   *   render(<UserWodsPanel loading={true} error={undefined} wods={[wod]} />)
   *   expect(
   *     container.querySelector(
   *       '#sandbox > div:nth-child(2) > div > div:nth-child(1) > div > svg',
   *     ),
   *   ).toBeInTheDocument()
   * }) */

  it('renders loading', () => {
    render(<UserWodsPanel loading={true} error={undefined} wods={wods} />)
    expect(screen.getByText(/loading\.\.\./i)).toBeInTheDocument()
  })

  it('renders errors', () => {
    withoutConsoleErrors(() => {
      render(
        <UserWodsPanel
          loading={false}
          error={new ApolloError({ errorMessage: 'BOOM' })}
          wods={wods}
        />,
      )
      expect(
        screen.getByText(/Oups, something went wrong/i),
      ).toBeInTheDocument()
    })
  })

  it('renders the WOD list', () => {
    render(<UserWodsPanel loading={false} error={undefined} wods={wods} />)
    // TODO check correct number of scores in "score stamp"
    // SwipeableViews only displays the first element
    const firstWod = wods[0]
    expect(screen.getByText(firstWod.workout.name)).toBeInTheDocument()
    expect(
      screen.getByText(
        (_: HTMLElement, node: Node) =>
          node.textContent === firstWod.workout.description,
      ),
    ).toBeInTheDocument()
    // TODO better check of the date once the UI display is correctly done
    expect(screen.getByText(/dec/i)).toBeInTheDocument()
  })
})
