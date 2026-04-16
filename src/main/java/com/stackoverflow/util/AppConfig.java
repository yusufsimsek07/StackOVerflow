package com.stackoverflow.util;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
    name = "java:app/jdbc/PostgresDS",
    className = "org.postgresql.ds.PGSimpleDataSource",
    url = "jdbc:postgresql://localhost:5432/stackoverflow_db",
    user = "yusuf",
    password = ""
)
@ApplicationScoped
public class AppConfig {
    // Glassfish bu sınıfı okuduğunda postgresDS'i otomatik oluşturur.
}
