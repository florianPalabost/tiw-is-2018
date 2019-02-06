FROM node:8.11.2-alpine as node
RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

RUN apk update && apk add --no-cache make git
COPY package.json package-lock.json  /usr/src/app/
RUN npm install @angular/cli@7.0.7 -g


# Move our files into directory name "app"
RUN npm install
COPY . .

# Expose the port the app runs in
# EXPOSE 4200

# Build with $env variable from outside
RUN npm run build

# Build a small nginx image with static website

# RUN set -x ; \
#   addgroup -g 82 -S www-data ; \
# adduser -u 82 -D -S -G www-data www-data && exit 0 ;
# RUN apk --no-cache add shadow && usermod -aG www-data nginx
# RUN touch /var/run/nginx.pid && \
#   chown -R www-data:www-data /var/run/nginx.pid && \
#   chown -R www-data:www-data /var/cache/nginx

# USER www-data

# VOLUME /var/www
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]
FROM nginx:1.13.12-alpine
# ENV http_proxy http://proxy.univ-lyon1.fr:3128/
# ENV https_proxy http://proxy.univ-lyon1.fr:3128/
COPY --from=node /usr/src/app/dist/cinema /usr/share/nginx/html

COPY ./default.conf /etc/nginx/conf.d/default.conf