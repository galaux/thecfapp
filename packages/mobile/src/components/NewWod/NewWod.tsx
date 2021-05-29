import {
  CommunityForNewWodFragment as Community,
  WorkoutForNewWodFragment as Workout,
} from './new-wod.generated'
import {
  Button,
  Select,
  Text,
  VStack,
  FormControl,
  FormLabel,
} from '@chakra-ui/react'
import { Moment } from 'moment'
import React, { useState } from 'react'
import { SingleDatePicker } from 'react-dates'

interface Props {
  initiallySelectedCommunityId: string
  communities: Community[]
  workouts: Workout[] | undefined
  createWod: (communityId: string, workoutId: string, date: string) => void
}

export const NewWod: React.FC<Props> = (props: Props) => {
  const { initiallySelectedCommunityId, communities, workouts, createWod } =
    props

  const [communityId, setCommunityId] = useState<string>(
    initiallySelectedCommunityId,
  )
  const [date, setDate] = useState<Moment | null>(null)
  const [dateFocused, setDateFocused] = useState<boolean>(false)
  const [workoutId, setWorkoutId] = useState<string>()

  function onClickCreate() {
    if (workoutId && date) {
      /* moment.js devs explain their lib is made to represent a point in time,
         not a "calendar day" [0]. Using `moment.format('YYYY-MM-DD')` does the
         job but as `api` uses Joda-ts, it could make sense to also use it in
         `mobile`. FIXME also use Joda-ts for mobile?
         [0] https://github.com/moment/moment/issues/3455#issuecomment-248044231 */
      /* FIXME Pass proper dates (moment or Joda-ts) and not strings as `date`
         (nota: on a higher level, we actually NEED to coerce `Date`s at the
         GraphQL boundary!): */
      createWod(communityId, workoutId, date.format('YYYY-MM-DD'))
    }
  }

  return (
    <VStack p='2%'>
      <Text>Create a WOD</Text>

      <FormControl id='community' isRequired>
        <FormLabel color='_gray'>Community</FormLabel>
        <Select
          value={communityId}
          onChange={(e) => setCommunityId(e.target.value)}
        >
          {communities.map((community) => (
            <option key={community.id} value={community.id}>
              {community.name}
            </option>
          ))}
        </Select>
      </FormControl>

      <FormControl id='Date' isRequired>
        <FormLabel color='_gray'>Date</FormLabel>
        {/* <SingleDatePickerWrapper>
            TODO extract our own date picker component
            http://airbnb.io/react-dates/?path=/story/singledatepicker-sdp--default
            https://github.com/airbnb/react-dates/blob/master/examples/SingleDatePickerWrapper.jsx */}
        <SingleDatePicker
          block
          date={date}
          focused={dateFocused}
          id='new-wod-date'
          isOutsideRange={() => false}
          onDateChange={(d) => setDate(d)}
          onFocusChange={({ focused }) => setDateFocused(focused)}
          orientation='vertical'
          small
        />
        {/* </SingleDatePickerWrapper> */}
      </FormControl>

      <FormControl id='Workout' isRequired>
        <FormLabel color='_gray'>Workout</FormLabel>
        <Select
          placeholder='Select a workout …'
          value={workoutId}
          onChange={(e) => setWorkoutId(e.target.value)}
        >
          {workouts?.map((workout) => (
            <option key={workout.id} value={workout.id}>
              {workout.name}
            </option>
          ))}
        </Select>
      </FormControl>

      <Button
        colorScheme='blue'
        disabled={!Boolean(communityId && date && workoutId)}
        onClick={onClickCreate}
      >
        Create
      </Button>
    </VStack>
  )
}
