package ca.sheridancollege.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DatabaseConfig {
	/**
	 * a method that will return a NamedParameterJdbcTemplate Bean
	 * @param dataSource will be injected by Spring 
	 * @return  the instance of NamedParemeterJdbcTemplate
	 */
	@Bean
	public NamedParameterJdbcTemplate
	namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
