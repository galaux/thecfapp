function getByEmail(email, callback) {
  // This script should retrieve a user profile from your existing database,
  // without authenticating the user.
  // It is used to check if a user exists before executing flows that do not
  // require authentication (signup and password reset).
  //
  // There are three ways this script can finish:
  // 1. A user was successfully found. The profile should be in the following
  // format: https://auth0.com/docs/users/normalized/auth0/normalized-user-profile-schema.
  //     callback(null, profile);
  // 2. A user was not found
  //     callback(null);
  // 3. Something went wrong while trying to reach your database:
  //     callback(new Error("my error message"));

  const request = require('request');

  request.get({
    url: 'https://' + configuration.AUTHORITY + '/api/user/' + email,
    auth: {
      username: configuration.STATIC_AUTH_ID,
      password: configuration.STATIC_AUTH_PASSWORD
    },
    //for more options check:
    //https://github.com/mikeal/request#requestoptions-callback
  }, function(err, response, body) {
    if (err) return callback(err);
    if (response.statusCode === 422) return callback(new Error(body));
    if (response.statusCode === 204) return callback();
    const user = JSON.parse(body);

    callback(null, {
      user_id: user.user_id.toString(),
      email: user.email
    });
  });
}
