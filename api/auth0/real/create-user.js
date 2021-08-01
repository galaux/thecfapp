function create(user, callback) {
  const request = require('request');

  request.post({
    url: 'https://' + configuration.AUTHORITY + '/api/user',
    auth: {
      username: configuration.STATIC_AUTH_ID,
      password: configuration.STATIC_AUTH_PASSWORD
    },
    json: user
    //for more options check:
    //https://github.com/mikeal/request#requestoptions-callback
  }, function(err, response, body) {
    if (err) return callback(err);

    callback(null);
  });
}
