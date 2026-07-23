package kr.ink94.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvironmentVariableChecker implements ApplicationRunner {

    private final Environment environment;

    public EnvironmentVariableChecker(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("=== Docker 환경변수 확인 ===");
        log.info("MARIADB_DATABASE={}", environment.getProperty("MARIADB_DATABASE"));
        log.info("MARIADB_PORT={}", environment.getProperty("MARIADB_PORT"));
        log.info("MARIADB_USER={}", environment.getProperty("MARIADB_USER"));
        log.info("MARIADB_PASSWORD={}", environment.getProperty("MARIADB_PASSWORD"));
        log.info("MARIADB_ROOT_PASSWORD={}", environment.getProperty("MARIADB_ROOT_PASSWORD"));
        log.info(
                "Active profiles={}",
                String.join(", ", environment.getActiveProfiles())
        );
    }

    private boolean isPresent(String value) {
        return value != null && !value.isBlank();
    }
}