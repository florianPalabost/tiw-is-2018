image:
  name: docker/compose:1.23.2 # update tag to whatever version you want to use.
  entrypoint: ["/bin/sh", "-c"]

services:
  - docker:dind

before_script:
  - docker info
  - docker-compose --version

buildJob:
  stage: build
  tags:
    - docker
  script:
    - docker-compose build