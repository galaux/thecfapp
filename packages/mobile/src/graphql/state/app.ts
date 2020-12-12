// import { makeVar } from '@apollo/client'

// const loggedUserIdVar = makeVar<string | undefined>()

const KEY_LOGGED_USER_ID = 'loggedUserId'

export function loggedUserId(id?: string): string {
  if (id !== undefined) {
    localStorage.setItem(KEY_LOGGED_USER_ID, id)
    return id
  }
  const loggedUserId = localStorage.getItem(KEY_LOGGED_USER_ID)
  if (!loggedUserId) {
    throw new Error(
      'Logged user ID should not be null at this point of the application.',
    )
  }
  return loggedUserId
}
