module.exports = {
  stories: ['../src/**/*.stories.*'],
  addons: [
    '@storybook/preset-create-react-app',
    '@storybook/addon-actions',
    '@storybook/addon-links',
    '@storybook/addon-viewport/register',
    '@storybook/addon-actions/register',
    '@storybook/addon-storysource',
    '@storybook/addon-links/register',
    '@storybook/addon-docs'
  ],
};
