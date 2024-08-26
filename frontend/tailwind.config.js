/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/index.html", "./src/**/*.{ts,html}"],
  theme: {
    extend: {
      colors: {
        tw_black: "#141414",
        tw_white: "#FFF",
        tw_error: "#F68A8A",
        tw_success: "#9ED970",
      },
      screens: {
        max_2xl: { max: "1400px" },
        max_xl: { max: "1280px" },
        max_lg: { max: "1024px" },
        max_md: { max: "768px" },
        max_sm: { max: "640px" },
        max_xs: { max: "480px" },
        max_2xs: { max: "375px" },
      },
    },
  },
  plugins: [],
};
