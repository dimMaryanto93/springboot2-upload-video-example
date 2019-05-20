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

or via docker-compose

```docker
docker-compose up -d
```

run springboot project:

```bash
mvn clean spring-boot:run
```

example Rest API

```bash
curl -X POST \
  http://localhost:8080/api/media/upload/video \
  -H 'Content-Type: application/octet-stream' \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F 'content=@/Users/user/Movies/file-video.mvk'
```

