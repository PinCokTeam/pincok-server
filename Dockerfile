# 베이스 이미지 (Java 21)
FROM eclipse-temurin:21-jdk-alpine

# 컨테이너 안에서 작업할 디렉토리
WORKDIR /app

# Gradle wrapper 포함 프로젝트 전체 복사
COPY . .

# Gradle 빌드 (jar 생성)
RUN ./gradlew clean build -x test

# jar 파일 복사
# build/libs 폴더 안에 있는 jar 이름 확인 후 맞춰주세요
ARG JAR_FILE=build/libs/pincock-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 포트 설정 (Spring Boot 기본 8080)
EXPOSE 8080

# 컨테이너 실행 시 실행할 명령
ENTRYPOINT ["java", "-jar", "app.jar"]
