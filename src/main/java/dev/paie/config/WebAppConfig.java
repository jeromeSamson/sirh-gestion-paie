package dev.paie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@Import({ ServicesConfig.class, SecurityConfig.class })
@ComponentScan({ "dev.paie.web.controller", "dev.paie.service", "dev.paie.config.aspect" })
@ImportResource({ "classpath:cotisations-imposables.xml", "classpath:cotisations-non-imposables.xml",
		"classpath:entreprises.xml", "classpath:grades.xml", "classpath:profils-remuneration.xml" })
@EnableAspectJAutoProxy
public class WebAppConfig {

	@Bean
	public ViewResolver viewResolver() {
		return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
	}

}
