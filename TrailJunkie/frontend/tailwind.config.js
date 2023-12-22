/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        'tj-bg': '#faf7ed',
        'tj-green': '#123331',
        'tj-light-brown': '#b99a7b',
        'tj-lightish-brown': '#91634d',
        'tj-dark-brown': '#664a3d',
        'tj-turquoise': '#167a78',
      },
      fontFamily: {
        'Outfit': ['Outfit', 'sans-serif']
      },
    },
  },
  plugins: [],
}