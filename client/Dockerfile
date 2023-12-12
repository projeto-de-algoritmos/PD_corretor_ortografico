FROM node:lts AS build

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:1.25

# copy the build folder from the build stage to the nginx html folder
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80