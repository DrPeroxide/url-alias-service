FROM node:lts-alpine AS build
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM node:lts-alpine AS run
RUN npm install -g http-server
COPY --from=build dist/ app/
EXPOSE 8080
CMD [ "http-server", "app" ]