/* Modifications to this code snippet should be reported to Git. */
function remove(id, callback) {
  /* Not yet implemented: we should not be deleting users rather than setting
   * a status to "disabled" which we don't support yet. Currently, the only way
   * a user deletion could be triggered is through Auth0 UI. Calling code
   * snippet on Auth0 side should throw an exception right away.
   */
  return callback(new Error("Not yet implemented"));
}
