## Solution

[This project][auth0-sso] looks to have implemented exactly the solution I had
implemented when exploring the ["plain JS"][plain-js] (which is in fact the
_official_ Auth0 snippet of code) and should serve as a perfect base for what
we want (as we already have the backend REST API to validate etc).

Watch-out as it uses Bidi for routing (but we might need routing anyway).

## Auth0 Lock

Watch-out: [Auth0 Lock feature is described as legacy by
Auth0[auth0-lock-is-legacy]:

> Lock SDK Libraries
> Do you use Lock, Auth0's legacy self-hosted login experience? You can still
> rely on these libraries for self-hosted login.
> Lock for Android      Bug fixes only
> Lock for iOS          Bug fixes only
> Lock for Web          Bug fixes only
[This article][using-auth0-lock] uses [Auth0's Lock feature][auth0-lock]. Not
sure what this is but I get from the official description that this is an
_embeddable login_ so maybe we wouldn't have to redirect users to Auth0's
website which would be neat! Major con: as explained [on their
page][auth0-lock]:

> Embedded login for web uses Cross Origin Authentication. […] Using Custom
> Domains with Auth0 is a paid feature.
But would one of the following 2 libraries provide a way to host our own REST
API as a sort of proxy to Auth0 and thus circumvent the problem?


## Backend libraries

Libraries [auth0-clojure][auth0-clojure] and [auth0-ring][auth0-ring]
(watch-out: outdated?) look like backend libraries to provide a REST API to act
as a proxy for Auth0 (Do I have this right!?). We don't need it at first but if
this could enable us to emulate some sort of custom domain, maybe we could then
use [Auth0 Lock][auth0-lock] and then have an embedded login.


## References

- [auth0-sso]: https://gitlab.com/jeff.terrell/auth0-cljs-re-frame/-/tree/main/src/auth0_sso
- [plain-js]: https://github.com/TheCFApp/thecfapp/tree/guillaume/auth-with-auth0-plain-js
- [using-auth0-lock]: http://randomlurker.eu/clojurescript/re-frame/2017/05/22/re-frame-auth0-authentication.html
- [auth0-lock]: https://auth0.com/docs/libraries/lock
- [auth0-clojure]: https://github.com/lumberdev/auth0-clojure
- [auth0-ring]: https://github.com/cjohansen/auth0-ring
- [auth0-lock-is-legacy]: https://auth0.com/docs/libraries
