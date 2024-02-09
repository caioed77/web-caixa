/** @type {import('tailwindcss').Config} */
const colors = require("tailwindcss/colors");
export default {
    content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
    theme: {
        extend: {
            colors: {               
                main: "#075985",
                main_hover: "#0284c7",
                fundo: "#F9FAFC",
            },
        },
    },
    plugins: [],
};
