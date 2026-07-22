# 1. OpenJDK 21 slim 경량화 이미지 사용
FROM openjdk:21-slim

# 2. 작업 디렉토리 생성 및 이동
WORKDIR /app

# 3. Gradle 빌드 시 생성된 execution jar 파일을 컨테이너 내 app.jar로 복사
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 4. Spring Boot 기본 애플리케이션 포트 명시
EXPOSE 8080

# 5. 컨테이너 실행 시 Spring Boot 앱 구동
ENTRYPOINT ["java", "-jar", "app.jar"]