import peerDepsExternal from "rollup-plugin-peer-deps-external";
import resolve from "@rollup/plugin-node-resolve";
import commonjs from "@rollup/plugin-commonjs";
import typescript from "rollup-plugin-typescript2";
import postcss from "rollup-plugin-postcss";
import url from '@rollup/plugin-url';
// import copy from 'rollup-plugin-copy';

// const packageJson = require("./package.json");

export default [
  // {
  //   input: 'src/i18n.js',
  //   output: {
  //     file: 'lib/i18n/i18n.js',
  //     format: 'cjs',
  //     // FIXME is that what we want?
  //     exports: "auto"
  //   }
  // },
  {
    input: "index.ts",
    output: [
      {
        file: "../mobile/src/js/views.js",
        format: "cjs",
        sourcemap: true
      }
    ],
    plugins: [
      peerDepsExternal(),
      resolve(),
      commonjs(),
      typescript({ useTsconfigDeclarationDir: true }),
      postcss({
        extensions: ['.css']
      }),
      url({
        include: [
          // '**/*.png',
          '**/*.woff',
          '**/*.woff2'
        ],
        limit: Infinity,
      }),
      // copy({
      //   targets: [
      //     {
      //       src: 'src/locales',
      //       dest: 'lib'
      //     }
      //   ]
      // })
    ]
  }
];
