import { fireEvent, render, screen } from '@testing-library/react'
import { FindCommunityPanel } from './FindCommunityPanel'
import faker from 'faker'
import { ApolloError } from '@apollo/client'
import {
  withoutConsoleErrors,
  fakeCommunityFieldsFragment,
  fakeCommunityAddressFieldsFragment,
  mapRandomTimes,
} from '../../test-utils'
import { CommunityForFindCommunityPanelFragment } from './find-community-panel.generated'

describe('Find community panel', () => {
  const findCommunitiesSpy = jest.fn()
  const joinCommunitySpy = jest.fn()
  const leaveCommunitySpy = jest.fn()

  beforeEach(() => jest.resetAllMocks)

  const foreignCommunity = {
    ...fakeCommunityForFindCommunityPanelFragment(),
    isMember: false,
  }

  const memberCommunity = {
    ...fakeCommunityForFindCommunityPanelFragment(),
    isMember: true,
  }

  it('renders initial state', () => {
    render(
      <FindCommunityPanel
        loading={false}
        lookedupCommunities={undefined}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    expect(screen.getByRole('textbox')).toBeInTheDocument()
    screen.logTestingPlaygroundURL()
    // TODO check button is displayed
    /* container.querySelector('#sandbox > div:nth-child(2) > div > div:nth-child(2) > div > div > div > button > svg') */
  })

  it('performs user query', () => {
    render(
      <FindCommunityPanel
        loading={false}
        lookedupCommunities={undefined}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    const value = faker.random.words(5)
    fireEvent.change(screen.getByRole('textbox'), { target: { value } })
    fireEvent.click(screen.getByRole('button'))
    expect(findCommunitiesSpy).toHaveBeenCalledWith(value)
  })

  it('displays loading state', () => {
    render(
      <FindCommunityPanel
        loading={true}
        lookedupCommunities={undefined}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    expect(screen.getByText(/loading\.\.\./i)).toBeInTheDocument()
  })

  it('displays errors', () => {
    withoutConsoleErrors(() => {
      render(
        <FindCommunityPanel
          loading={false}
          error={new ApolloError({ errorMessage: 'BOOM' })}
          lookedupCommunities={undefined}
          findCommunities={findCommunitiesSpy}
          joinCommunity={joinCommunitySpy}
          leaveCommunity={leaveCommunitySpy}
        />,
      )
      expect(
        screen.getByText(/Oups, something went wrong/i),
      ).toBeInTheDocument()
    })
  })

  function fakeCommunityForFindCommunityPanelFragment(): CommunityForFindCommunityPanelFragment {
    return {
      ...fakeCommunityFieldsFragment(),
      address: fakeCommunityAddressFieldsFragment(),
      isMember: faker.random.boolean(),
      memberCount: faker.random.number({ min: 1, max: 10 }),
    }
  }

  it('displays found communities regular fields', () => {
    const communities = mapRandomTimes(
      fakeCommunityForFindCommunityPanelFragment,
    )
    render(
      <FindCommunityPanel
        loading={false}
        error={undefined}
        lookedupCommunities={communities}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    // Has form to submit search
    expect(screen.getByText(/find community/i)).toBeInTheDocument()
    expect(screen.getByRole('textbox')).toBeInTheDocument()
    /* container.querySelector('#sandbox > div:nth-child(2) > div > div:nth-child(2) > div > div:nth-child(1) > div > button > svg > circle') */

    // Has list of passed communities
    for (const community of communities) {
      expect(
        screen.getByRole('heading', {
          name: new RegExp(community.name),
        }),
      )
      if (community.address) {
        const address = communities[0].address
        expect(screen.getByText(new RegExp(address.street)))
        expect(screen.getByText(new RegExp(address.zipCode)))
        expect(screen.getByText(new RegExp(address.city)))
        expect(screen.getByText(new RegExp(address.country)))
      }
    }
  })

  it('displays a "Join" button if user is not a member', () => {
    render(
      <FindCommunityPanel
        loading={false}
        error={undefined}
        lookedupCommunities={[foreignCommunity]}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    expect(
      screen.getByRole('button', {
        name: /join/i,
      }),
    )
  })

  it('displays a "Leave" button if user is not a member', () => {
    render(
      <FindCommunityPanel
        loading={false}
        error={undefined}
        lookedupCommunities={[memberCommunity]}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    expect(
      screen.getByRole('button', {
        name: /leave/i,
      }),
    )
  })

  it('display a special message when there are no communities', () => {
    render(
      <FindCommunityPanel
        loading={false}
        error={undefined}
        lookedupCommunities={[]}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    expect(screen.getByText(/looks like this community doesn't exist yet\./i))
    expect(
      screen.getByRole('button', {
        name: /create your community/i,
      }),
    )
  })

  it('removes user from community when clicking on "Leave"', () => {
    render(
      <FindCommunityPanel
        loading={false}
        error={undefined}
        lookedupCommunities={[memberCommunity]}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    fireEvent.click(screen.getByRole('button', { name: /leave/i }))
    expect(leaveCommunitySpy).toHaveBeenCalled()
  })

  it('adds user to community when clicking on "Join"', () => {
    render(
      <FindCommunityPanel
        loading={false}
        error={undefined}
        lookedupCommunities={[foreignCommunity]}
        findCommunities={findCommunitiesSpy}
        joinCommunity={joinCommunitySpy}
        leaveCommunity={leaveCommunitySpy}
      />,
    )
    fireEvent.click(screen.getByRole('button', { name: /join/i }))
    expect(joinCommunitySpy).toHaveBeenCalled()
  })
})
