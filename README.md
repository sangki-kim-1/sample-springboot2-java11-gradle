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

## 3. 도커 컴포즈 - 실행
### 1. 도커 컴포즈 실행
> 도커 컴포즈는 빌드 없이 바로 실행됩니다.  
> 프로메테우스, 그라파나, MysQL 컨테이너 실행 완료 후 서버가 실행됩니다.   
> 자세한 설명은 아래 링크를 참고하세요.  
> [모니터링 도커 컴포즈](.docker/monitoring/README.md)  
> [DB 도커 컴포즈](.docker/db/README.md)
```shell
docker-compose up
```

### 2. 도커 컴포즈 이미지 삭제
도커 컴포즈는 도커 이미지를 사용한 컨테이너 관리 도구이기 때문에 도커 이미지 자체에 대한 변경을 감지할 수 없습니다.
따라서 컴포즈에 포함된 도커 이미지가 변경되는 경우(스프링 부트 파일) 이미지를 삭제하고 다시 빌드해야 합니다.
```shell
docker-compose build --no-cache
```

## 99. 참고
### 1. 프로메테우스 - 그라파나(via. docker-compose)
[모니터링 도커 컴포즈](.docker/monitoring/README.md)

### 2. DB(via. docker-compose)
[DB 도커 컴포즈](.docker/db/README.md)