import Head from "next/head";

export default function Layout({ children }) {
  return (
    <div>
      <Head>
        <link rel="icon" href="/favicon.ico" />
        <title>Next App</title>
      </Head>

      <main>{children}</main>
    </div>
  );
}
