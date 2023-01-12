module.exports = {
  core: {
    builder: 'webpack5',
  },
  stories: ['../resources/public/js/stories/**/*.story.js'],
  "addons": [
    "@storybook/addon-essentials",
  ],
  staticDirs: ['../resources/public'],
};
