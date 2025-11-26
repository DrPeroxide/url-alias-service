# url-alias-webui

Web interface for the URL Alias service, developed with Vue 3 and Vite.

## Developer Guide

> [!IMPORTANT] Prerequisites
>
> - NPM 11 (or your preferred package manager)
> - Node 25
> - The url-alias-api is available on port 8080 with `docker compose -f ../compose.yaml up api -d`

1. Install project dependencies with `npm install`.
2. Run the Vite web server in development mode with `npm run dev`.
3. Open the UI with a modern broswer via the Local link displayed in the output.
4. Close the server with `CTRL + C` when done.

The webapp will update automatically as you make changes to the source,
 though you should still refresh your browser when checking your changes.

### Structure

The source code is stored under [/src](./src) and consists of a main.js file, an App.vue file and a number of vue components under [/components](./src/components).

Environment variables, such as the URL of the REST api, are contained in [.env](./env).


### Testing

Unit tests are stored under [/test](./test) and can be ran using `npm run test`.

This will run vitest, which will in turn run all unit tests before waiting for changes. When changes are made to the source or test code, the tests will automatically re run.