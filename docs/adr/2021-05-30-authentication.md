## Decision related to authentication

Why go for Auth0 and not manual login/pwd and manual OAuth (Facebook, Google,
Twitter, …)?

TL;DR: Both login/pwd and OAuth are way more intricate to implement manually
than it appears!

- manual login/pwd:
  - storing of pwd on our DB is trivial:
    - hash using Argon2
    - pass the pwd from FE to BE un-hashed then hash on the server side to
      prevent hash from being intercepted
  - complications:
    - need to implement a whole email (login) validation flow because requires:
      - a server email capabilities: naive implem or potentially the whole
        shebang with SPF, DKIM, DMARC and TLS certificates?
      - generating unique links with expiration date
      - flow to reset password by sending new unique link
- manual OAuth:
  - OAuth2 and its different flows is rather simple but my try on branch
    `guillaume/fb-oauth-login` is kindof stuck:
    - has an _Implicit flow_ working (though not secured becaue not done
      through HTTPS)
      - implicit flow is _deprecated_, I must use a regular flow and add PKCE
        (not complicated per-se, but just yet another small thing to implement)
    - I currently use the long lived token and pass it to my API as auth token
      - I should actually pass the short lived token to my API and use the long
        lived token to regularly get a refresh token
      - this whole process of regularly silently polling FB/Google/… to get a
        refreshed short lived token must be coded
    - I'm not entirely sure what the exact flow should be between front/FB/api
      as we are on a SPA. Potential flows:
      - 1st solution:
        - front redirects user to FB
        - which then redirects to our API's /auth with a `code`
        - where (on the backend) we turn the code into a token
        - then provide front with a temporary (different) short lived token
          used to immediately call us
        - which enables us to return the actual FB token
        - (this whole _different short lived token_ ensure we don't pass the
          real FB token through HTTP parameters where one can intercept it)
      - 2dn solution:
        - front redirects user to FB
        - which then redirects to our API's /auth with a `code`
        - but we don't do anything backend side and let front end get this code
        - FE calls FB API to turn the code into a token but this requires CORS
          - I don't have a sufficient understanding of CORS
          - it seems FB should return an "allow" parameter for the whole CORS
            sheband to work but do they?
            - there are fields in the FB developer site to specify some of our
              URLs but nothing specifically mentioning "allowed origins"/"cors"
            - Not sure they even support the feature! Can't even find mentions
              of this.
      - 3rd solution:
        - people on internet suggest a way to run an HTTP query without
          requiring CORS by using a form validation
        - sounds hacky and so suspicuous regard to security!
    - a lot of resources on internet discourage storing the token on local
      storage because of XSS enabling _any other JS app to read it_
      - not sure I understand how any other application/website/tab could read
        _our_ part of the local storage.  These are segregated specifically for
        these security concerns!  Still, this warning is emitted by OWASP! They
        know what they are saying.
      - many recommend to store it in a cookie with `secure=?` etc so that the
        app has no acces to it
        - this ensures our app actually doesn't have access to the token at all
        - Doesn't this prevent refreshing the short lived token with the long
          lived one?
        - Doesn't it prevent a "long session": ie, we are a SPA, not a web page
          where you might want short lived sessions
          - I might have the correct idea there:
            - I guess banks should use these cookie tokens so that every time
              user closes tab (s)he is logged out
            - comming back to our app? Ok then re-login. That heavy but we are
              talking about a bank app.
            - in our case we are not securing a bank app so we would be ok with
              storing short term token in local storage?
        - in any case this "threat" of having the token stolen from local
          storage stems from the lack of XSS protection.  We obviously need to
          protect ourselves from XSS! TODO

### Note worthy related to Google OIDC
  - Google
    - uses Open ID Connect (OIDC) which seems like a light layer on top of
      OAuth2 and _simpler_
    - I think I remember seeing a screenshot of the Google developer _admin
      console_ where one could explicitly specify "origins" (allows the more
      straightforward and potentially more security compliant "solution 2" of
      OAuth2 – see above)

## Decision to go for Auth0

Going for Auth0 provides a lot of simplicity by taking care of all the nitty
gritty details listed above:

- whole email (re)validation flow (so don't have to even think about _how to
  send an email_)
- has a nice/simple/complete/straightforward react hook library with a context
  and all https://auth0.com/docs/quickstart/spa/react
