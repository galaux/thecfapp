## About Next.js

Next.js choices regarding SSR, SSG with data, SSG without data and CSR

```
------------------------|---------------------------|---------------
Pre-rendering           |  Static Site Generation   |  Server Side
(Server-side)           |---------------------------|   Rendering
Optional                | With Data  | Without Data |
------------------------|------------|--------------|---------------
Client Side Rendering   |    ✓ (1)   |      ✓ (3)   |      ✓ (5)
(Optional)              |    ✗ (2)   |      ✗ (4)   |      ✗ (6)
------------------------|------------|--------------|---------------
```

With Next.js, one can choose between these 6 options (plus even craft some hybrid solution)

- SSR:
  - Next.js advises against starting with SSR because can be heavy for client (basically: this is a full blown web app, not a client side app)
  - can still use Client Side Rendering if need be
- SSG:
  - is the option Next.js advises to start with
  - the generated result is a set of assets (HTML+JS) that already contains some or potentially even all _business_ data inside
    There are 2 flavours:
    - with data: meaning _at build time, when the static content is generated, it needs data from the server_
    - without data: meaning _at build time, when the static content is generated, it needs no data from the server_
- Client Side Rendering:
  - **Regardless of whether the result is generated in SSR or SSG**, the resulting set of assets that runs on the client can also render some data that it fetches itself
  - Next.js advises to use their [SWR](https://nextjs.org/docs/basic-features/data-fetching#swr) which does caching of HTTP with React Hooks
    but in our case, we already use Apollo Client which provides caching at the GraphQL level and does it using React Hooks too

## Our application

I am convinced our use case is one of a mobile (web) app, not a server side web app:

- I don't see any data we could pre-fetch before building the app
  Every data we want to render has a meaning in the context of the user running the application which is run time info.
  We are building a client side app with web technologies, not a server side web app where we would be interested in maximizing the SRE.
- we only need some sort of SSG with all content data using something like Client Side Rendering – case (3)

For this reason, I am convinced the recent switch to SSR was a mistake and that we should at a minimum switch back to SSG.
We don't need Next's SRE nor SWR so Next doesn't seem to have much benefit anymore compare to a plain app created with `create-react-app`

As explained nicely in [this article](https://medium.com/twostoryrobot/a-recipe-for-offline-support-in-react-apollo-571ad7e6f7f4)
and also [« How to build a Progressive Web App with React & Apollo Client »](https://rubygarage.org/blog/pwa-with-react-apollo)
if one creates a React App (be it a plain `create-react-app` or a Next.js app), all data will be lost every time the user refreshes the app.
To turn this app into a Progressive Web App (PWA – full blown **offline** app), one needs to:

- enable a [service worker](https://create-react-app.dev/docs/making-a-progressive-web-app/).
  This will cache the assets and at a minimum, enable the app to start even without any internet connection
- persist the Apollo Client cache with [`apollo-cache-persist`](https://github.com/apollographql/apollo-cache-persist)
  so that it is still available if user refreshes the page/app.
  Watch-out: this requires mutations to be handled specifically
  - short term solution: when one detects we are offline, just don't allow mutations
  - long term solution: use _Optimistic response_

Side benefit: this kind of application can be served from a CDN (see [Netlify](https://www.netlify.com/) that has a CI plugged to GitHub able to deploy on git push)

## This repo

I'm gathering this in [a PoC client-side offline-capable application that uses web technologies](https://github.com/TheCFApp/react-app-pwa)
(a [PWA](https://web.dev/progressive-web-apps/))

TODO on the PWA React "mobile" project:

- [✓] create a clean new project called `mobile` with plugin `cra-template-pwa-typescript`
- [✓] add UI with that single page I've coded so far
  - [✓] add Chakra-ui
- [✓] enable worker to check that app can still be used when offline
- [✓] setup and use a router as PoC
- [✓] plug it to GraphQL using an Apollo client
  - [✓] setup an Apollo client connection to local GraphQL API
  - [✓] use `apollo-cache-persist`
  - [✓] add a mutation – `addScore` – and don't forget to [write the results in the cache](https://www.apollographql.com/docs/react/data/mutations/#making-all-other-cache-updates)
- [ ] integrate as a Yarn workspace in `theccfapp` repo
  - [ ] …
  - [ ] add [React ESLint pluging for hooks](https://www.npmjs.com/package/eslint-plugin-react-hooks) TODO
- [ ] handle [GraphQL error and loading](https://www.apollographql.com/docs/react/data/mutations/#tracking-loading-and-error-states)
- [ ] check we already have all features described in [this article](https://medium.com/twostoryrobot/a-recipe-for-offline-support-in-react-apollo-571ad7e6f7f4) (retry, …)
- [ ] setup tests for UI

TODO nice to have Storybook integration:

- [ ] configure StoryBook
- [ ] configure Figma plugin for StoryBook
- [ ] check if one can configure an client Apollo in storybook via a [Storybook Toolbars Addon](https://github.com/storybookjs/storybook/tree/next/addons/toolbars)
      by following [this guy's explanation](https://github.com/abhiaiyer91/apollo-storybook-decorator/issues/139#issuecomment-647152356)

TODO Later:

- [ ] Manage [local state](https://www.apollographql.com/docs/react/local-state/local-state-management/)
- [ ] see if we want to handle optimistic response for mutation or maybe leave it for later

## How to decouple components and pages

In order to keep components and pages decoupled, we need to follow this pattern:

```
                  UserFields
                  /         \
                 /           \
Query GetUserProfile       UserProfilePanelFragment
Fragment UserProfile
         |                           |
         |                           |
  UserProfilePage             UserProfilePanel
      (page)                    (component)
```

File hierarchy:

```
src
├── pages/user/profile
│   ├── user.generated.tsx
│   ├── user.graphql                  // the query and its result fragment
│   └── user-profile.tsx              // the code of the page
└── components/UserProfilePanel
    ├── user-profile-panel.generated.tsx
    ├── user-profile-panel.graphql    // the component's fragment
    └── UserProfilePanel.tsx          // the code of the component
```

- Page `UserProfilePage` uses GraphQL query `GetUserProfile` which returns a `UserProfile`
  - these are page related and so live on `src/pages`
  - `UserProfile` declares a composition of `UserFields` fields
  - page `UserProfilePage` declares an instance of the component `UserProfilePanel` to which it passes the result of `GetUser` which is a `UserProfile`
- Component `UserProfilePanel` takes a `UserProfilePanelFragment`
  - they are component related an so live on `src/components`
  - `UserProfilePanelFragment` also declares a composition of `UserFields` fields

Page's `UserProfile` and component's `UserProfilePanel` are two **different but compatible** fragments on the same GraphQL _root_ schema element. As TypeScript is dynamic, it is able to see that they are compatible at run time (see their respective generated definitions).
