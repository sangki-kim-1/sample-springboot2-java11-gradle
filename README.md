# 샘플용 자바11 스프링부트2 프로젝트
> server port: 8080
> actuator port: 8081  

## 1. 로컬 빌드 - 실행
### 1. build
```shell
./gradlew clean build
```

### 2. run jar
```shell
java -jar build/libs/sample-spring-boot-2-java-gradle-0.0.1-SNAPSHOT.jar
```

## 2. 도커 빌드 - 실행
### 1. docker build
```shell
docker build -t sample-spring-boot-2-java-gradle .
```

### 2. docker run
```shell
docker run -d -p 8080:8080 -p 8081:8081 sample-spring-boot-2-java-gradle
```

## 99. 참고
### 1. 프로메테우스 - 그라파나(via. docker-compose)
[모니터링 도커 컴포즈](.docker/monitoring/README.md)

### 2. DB(via. docker-compose)
[DB 도커 컴포즈](.docker/db/README.md)
```