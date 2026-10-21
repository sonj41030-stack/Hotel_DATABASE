package com.jhonaiker.hotel_reservas.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
@Profile("oracle")
@RequiredArgsConstructor
public class PlsqlInitializer implements ApplicationRunner {

    // Orden de ejecución: funciones, procedimientos, package y triggers
    private static final String[] SCRIPTS = {
            "plsql/01_funciones.sql",
            "plsql/02_procedimientos.sql",
            "plsql/03_package.sql",
            "plsql/04_triggers.sql"
    };

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String ruta : SCRIPTS) {
            String contenido = new ClassPathResource(ruta)
                    .getContentAsString(StandardCharsets.UTF_8)
                    .replace("\r", "");

            // Cada bloque PL/SQL termina con una línea que solo tiene "/"
            for (String bloque : contenido.split("(?m)^/\\s*$")) {
                String sql = bloque.trim();
                if (!sql.isEmpty()) {
                    jdbcTemplate.execute(sql);
                }
            }
            log.info("Script PL/SQL ejecutado: {}", ruta);
        }

        // Oracle no lanza excepción si un objeto compila con errores; hay que consultarlos
        List<String> errores = jdbcTemplate.queryForList(
                "SELECT name || ' (línea ' || line || '): ' || text FROM user_errors ORDER BY name, sequence",
                String.class);
        errores.forEach(e -> log.error("Error de compilación PL/SQL: {}", e));
    }
}