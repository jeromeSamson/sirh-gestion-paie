package dev.paie.spring;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "dev.paie.service", "dev.paie.util", "dev.paie.spring" })
@Import(DataSourceMySQLConfig.class)
@EnableTransactionManagement
@EnableJpaRepositories("dev.paie.repository")
public class JpaConfig {
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	@Bean
	// Cette configuration nécessite une source de données configurée.
	// Elle s'utilise donc en association avec un autre fichier de configuration
	// définissant un bean DataSource.

	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		// vendorAdapter.setGenerateDdl(true);
		// activer les logs SQL
		vendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		// alternative au persistence.xml
		factory.setPackagesToScan("dev.paie.entite");
		factory.setDataSource(dataSource);
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57InnoDBDialect");
		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

}