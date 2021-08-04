function login(email, password, callback) {
  const request = require('request');

  request.post({
    url: 'https://' + configuration.AUTHORITY + '/api/login',
    auth: {
      username: configuration.STATIC_AUTH_ID,
      password: configuration.STATIC_AUTH_PASSWORD
    },
    json: {
      email: email,
      password: password
    }
    //for more options check:
    //https://github.com/mikeal/request#requestoptions-callback
  }, function(err, response, body) {
    if (err) return callback(err);
    if (response.statusCode === 204) return callback();
    const user = body;

    callback(null, {
      user_id: user.user_id.toString(),
      // nickname: user.nickname,
      email: user.email
    });
  });
}
