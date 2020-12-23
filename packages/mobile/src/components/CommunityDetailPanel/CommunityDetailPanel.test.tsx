import { render, screen } from '@testing-library/react'
import { v4 as uuid } from 'uuid'
import { ApolloError } from '@apollo/client'
import faker from 'faker'
import { CommunityDetailPanelFragment as Community } from './community-detail-panel.generated'
import { CommunityDetailPanel } from './CommunityDetailPanel'

describe('User WODs panel', () => {
  const error = new ApolloError({ errorMessage: 'BOOM' })

  function fakeCommunity(): Community {
    return {
      id: uuid(),
      name: faker.random.words(5),
      address: {
        street: faker.address.streetAddress(),
        zipCode: faker.address.zipCode(),
        city: faker.address.city(),
        country: faker.address.country(),
      },
      memberCount: 9, // TODO
      wods: [], // TODO
    }
  }

  const community = fakeCommunity()

  it('renders loading', () => {
    render(
      <CommunityDetailPanel
        loading={true}
        error={undefined}
        community={community}
      />,
    )
    expect(screen.getByText(/loading\.\.\./i)).toBeInTheDocument()
  })

  it('renders errors', () => {
    render(
      <CommunityDetailPanel
        loading={false}
        error={error}
        community={community}
      />,
    )
    expect(screen.getByText(/Oups, something went wrong/i)).toBeInTheDocument()
  })

  it('renders the community detail', () => {
    render(
      <CommunityDetailPanel
        loading={false}
        error={undefined}
        community={community}
      />,
    )
    screen.debug()
    screen.logTestingPlaygroundURL()
    // Header
    expect(screen.getByText(community.name)).toBeInTheDocument()
    // For simplicity, addresses are generated in this test with all 4 fields set
    expect(
      screen.getByText(new RegExp(community.address?.street)),
    ).toBeInTheDocument()
    // TODO better check of correct number of WODs performed
    expect(screen.getByText(/WODPERFORMED/i)).toBeInTheDocument()
    // TODO better check correct number of member in "member stamp"
    expect(screen.getByText(community.memberCount)).toBeInTheDocument()
    expect(screen.getByText(/New WOD/)).toBeInTheDocument()
    // TODO check clicking on "New WOD" sends to correct page

    // Body
    // TODO WOD list
    // TODO name
    // TODO number of scores
    // TODO description
    // TODO Score of logged user (if has one)
    // TODO date
    // TODO Presence of btn to scoreboard

    /* expect(screen.getByText(firstWod.workout.description)).toBeInTheDocument()
     * // TODO better check of the date once the UI display is correctly done
     * expect(screen.getByText(/dec/i)).toBeInTheDocument() */
  })
})
