# Springboot Rest API upload media

Project example upload video using springboot v2 via Rest API, then compressed and generated thumbnails of videos.

System Required 

- maven
- Java 8
- [ffmpeg](https://ffmpeg.org)

## Run application

prepared database using postgresql:

```bash
# create schema user
create user uploader with superuser login password 'uploader';

# crete schema database
create database uploader;
```

run springboot project:

```bash
mvn clean spring-boot:run
```

## Run Application with docker-componse

Build image using maven command:

```bash
mvn clean install -DskipTests
```

it's will build the image, then you can run the container with docker-compose

```docker
docker-compose up -d
```


## example Rest API

```bash
curl -X POST \
  http://localhost:8080/api/media/upload/video \
  -H 'Content-Type: application/octet-stream' \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F 'content=@/Users/user/Movies/file-video.mvk'
```

