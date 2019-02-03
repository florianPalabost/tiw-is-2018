# The builder from node image
FROM node:alpine as builder

# build-time variables 
# prod|sandbox its value will be come from outside 
ARG env=prod

RUN apk update && apk add --no-cache make git

# Move our files into directory name "app"
WORKDIR /usr/src/front
COPY package.json package-lock.json  /usr/src/front/
RUN npm install @angular/cli@6.0.8 -g
RUN cd /usr/src/front && npm install
COPY .  /usr/src/front

# Expose the port the app runs in
EXPOSE 4200

# Build with $env variable from outside
RUN cd /usr/src/front && npm run build

# Build a small nginx image with static website
FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /usr/src/front/dist /usr/share/nginx/html
RUN set -x ; \
  addgroup -g 82 -S www-data ; \
adduser -u 82 -D -S -G www-data www-data && exit 0 ;
RUN apk --no-cache add shadow && usermod -aG www-data nginx
RUN touch /var/run/nginx.pid && \
  chown -R www-data:www-data /var/run/nginx.pid && \
  chown -R www-data:www-data /var/cache/nginx

USER www-data

VOLUME /var/www
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]