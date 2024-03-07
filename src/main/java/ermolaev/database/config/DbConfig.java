package ermolaev.database.config;

import ermolaev.database.DatabaseChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class DbConfig {
    private final String DRIVER_NAME;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;
    private final String URL;

    @Autowired
    public DbConfig(@Value("${driver.name}") String DRIVER_NAME,
                    @Value("${name}") String DB_USERNAME,
                    @Value("${password}") String DB_PASSWORD,
                    @Value("${URL}") String URL) {
        this.DRIVER_NAME = DRIVER_NAME;
        this.DB_USERNAME = DB_USERNAME;
        this.DB_PASSWORD = DB_PASSWORD;
        this.URL = URL;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDriverClassName(DRIVER_NAME);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        DatabaseChecker.checkConnection(jdbcTemplate);
        return jdbcTemplate;
    }
}
