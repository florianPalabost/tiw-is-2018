FROM nginx:latest
# RUN rm -rf /usr/share/nginx/html/*
COPY default.conf /etc/nginx/conf.d/
RUN apk update && apk add bash
CMD ["nginx" "-g" "daemon off;"]