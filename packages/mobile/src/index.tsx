import App from './App'
import reportWebVitals from './reportWebVitals'
import * as serviceWorkerRegistration from './serviceWorkerRegistration'
import { Auth0Provider } from '@auth0/auth0-react'
import React from 'react'
import ReactDOM from 'react-dom'

declare global {
  interface Window {
    SERVER_DATA: {
      JWT_DOMAIN: string
      JWT_CLIENT_ID: string
    }
  }
}

const AppShell: React.FC = () => {
  /* TODO should hold the base UI shell: this component exists so that we
     display the style of our app as early as possible and **then** let the
     time consuming clients (Auth0, GQL) start to load. */
  const jwtDomain =
    process.env.REACT_APP_JWT_DOMAIN ?? window.SERVER_DATA.JWT_DOMAIN
  const jwtClientId =
    process.env.REACT_APP_JWT_CLIENT_ID ?? window.SERVER_DATA.JWT_CLIENT_ID
  return (
    <Auth0Provider
      domain={jwtDomain}
      clientId={jwtClientId}
      redirectUri={window.location.origin}
    >
      <App />
    </Auth0Provider>
  )
}

ReactDOM.render(
  <React.StrictMode>
    <AppShell />
  </React.StrictMode>,
  document.getElementById('root'),
)

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.register()

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
