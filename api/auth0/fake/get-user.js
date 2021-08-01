/* Modifications to this code snippet should be reported to Git. */

/* Check user against hard-coded users without calling an actual CFApp API.
 *
 * This fake login is useful for setups where Auth0 cannot know in advance the
 * URL of the API such as Heroku "review apps". It should obviously not be used
 * anywhere where security is a concern.
 *
 * These fake users are also bootstrapped in all review apps.
 */
function getByEmail(email, callback) {
  const users = {
    "g0+cfapp@alaux.net": "8c99beba-12da-44c8-aeb2-e44f82ca27cd",
    "g1+cfapp@alaux.net": "b6b37b12-db24-41c5-ab52-4740dcc3ba0f",
    "g2+cfapp@alaux.net": "e43f5c9e-1c7b-45e4-906a-25c52dbd2600",
    "g3+cfapp@alaux.net": "c49d942d-b00c-45aa-8ec5-2c1931b8a006"
  };

  if (!users[email]) return callback();

  callback(null, {
    user_id: users[email],
    email: email
  });
}
