import next from 'next';

// TODO should this be read from the shared `AppConfig`?
export default next({
  dev: process.env.NODE_ENV !== 'production',
  dir: __dirname,
});
