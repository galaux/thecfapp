https://github.com/FundingCircle/ring-gatekeeper
https://github.com/lumberdev/auth0-clojure

Let's go with Auth0 but just pay attention not to couple ourselves to any
solution.

Obviously we want to prevent creation of multiple accounts per user but that
should be fairly simple to avoid by just paying extra care when user requests a
login or to create an account:
- if user says (s)he want sto **create an account** and performs a successfull
  OAuth/OpenConnect authentication, then create a new account
- if user says (s)he wants to **log in** then performs a successfull
  OAuth/OpenConnect authentication and we can't find external handle on our DB
  then carrefully explain user that if we go on we are going to **create** an
  account which is different from _using a **new auth method** to log to an
  existing account_ (and give link on _how to add a new auth method to an
  already existing account_)


TODO
  - read/understand OAuth and OpenConnect
