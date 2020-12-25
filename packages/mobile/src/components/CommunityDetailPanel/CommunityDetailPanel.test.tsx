import { render, screen } from '@testing-library/react'
import { v4 as uuid } from 'uuid'
import { ApolloError } from '@apollo/client'
import faker from 'faker'
import { CommunityForCommunityDetailPanelFragment } from './community-detail-panel.generated'
import { CommunityDetailPanel } from './CommunityDetailPanel'
import { mapRandomTimes } from '../../test-utils'
import { fakeWodForWodCard } from '../WodCard/test-utils'

describe('User WODs panel', () => {
  const error = new ApolloError({ errorMessage: 'BOOM' })

  function fakeCommunityForCommunityDetailPanel(): CommunityForCommunityDetailPanelFragment {
    return {
      id: uuid(),
      name: faker.random.words(5),
      memberCount: faker.random.number(1000),
      address: faker.random.boolean()
        ? undefined
        : {
            street: faker.address.streetAddress(),
            zipCode: faker.address.zipCode(),
            city: faker.address.city(),
            country: faker.address.country(),
          },
      wods: mapRandomTimes(fakeWodForWodCard, { min: 1, max: 5 }),
    }
  }

  const community = fakeCommunityForCommunityDetailPanel()

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

  it('renders the community detail that has no WOD', () => {
    const communityWithNoWod = fakeCommunityForCommunityDetailPanel()
    communityWithNoWod.wods = []
    render(
      <CommunityDetailPanel
        loading={false}
        error={undefined}
        community={communityWithNoWod}
      />,
    )
    // Header
    expect(screen.getByText(communityWithNoWod.name)).toBeInTheDocument()
    // For simplicity, addresses are generated in this test with all 4 fields set
    if (communityWithNoWod.address?.street) {
      expect(
        screen.getByText(new RegExp(communityWithNoWod.address.street)),
      ).toBeInTheDocument()
    }
    // TODO better check of correct number of WODs performed
    expect(screen.getByText(/WODPERFORMED/i)).toBeInTheDocument()
    // TODO better check correct number of member in "member stamp"
    expect(screen.getByText(communityWithNoWod.memberCount)).toBeInTheDocument()
    expect(screen.getByText(/New WOD/)).toBeInTheDocument()
    // TODO check clicking on "New WOD" sends to correct page
  })

  it('renders the community detail', () => {
    render(
      <CommunityDetailPanel
        loading={false}
        error={undefined}
        community={community}
      />,
    )

    // Header
    expect(screen.getByText(community.name)).toBeInTheDocument()
    // For simplicity, addresses are generated in this test with all 4 fields set
    if (community.address?.street) {
      expect(
        screen.getByText(new RegExp(community.address.street)),
      ).toBeInTheDocument()
    }
    // TODO better check of correct number of WODs performed
    expect(screen.getByText(/WODPERFORMED/i)).toBeInTheDocument()
    // TODO better check correct number of member in "member stamp"
    expect(screen.getByText(community.memberCount)).toBeInTheDocument()
    expect(screen.getByText(/New WOD/)).toBeInTheDocument()
    // TODO check clicking on "New WOD" sends to correct page

    // Body
    const wod = community.wods[0]
    expect(screen.getByText(wod.workout.name)).toBeInTheDocument()
    // TODO number of scores
    expect(
      screen.getByText(RegExp(wod.workout.description)),
    ).toBeInTheDocument()
    // TODO Score of logged user (if has one)
    // TODO better check of the date once the UI display is correctly done
    expect(screen.getByText(/dec/i)).toBeInTheDocument()
    // TODO Presence of btn to scoreboard
    expect(
      screen.getByRole('button', { name: /scoreboard/i }),
    ).toBeInTheDocument()
  })
})
