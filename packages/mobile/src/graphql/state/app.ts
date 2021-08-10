// import { makeVar } from '@apollo/client'

// const loggedUserIdVar = makeVar<string | undefined>()

const KEY_LOGGED_USER_ID = 'loggedUserId'

export function noUserLoggedIn(): boolean {
  return localStorage.getItem(KEY_LOGGED_USER_ID) == null
}

/* DEPRECATED: use the following to get the user ID:
   ```
   const { user: extUser } = useContext(Auth0Context)
   const loggedUserId = extUserToInternalId(extUser)
   ```

   This function remains until we get rid of the last piece of code to use it.
  */
export function __loggedUserId(id?: string): string {
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
