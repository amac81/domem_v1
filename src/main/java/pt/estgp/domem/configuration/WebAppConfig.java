package pt.estgp.domem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import pt.estgp.domem.converters.IdToAreaManutencaoConverter;
import pt.estgp.domem.converters.RoleToUserProfileConverter;
import pt.estgp.domem.interceptors.DomemAllInterceptor;
import pt.estgp.domem.interceptors.MaintenanceInterceptor;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "pt.estgp.domem")
public class WebAppConfig extends WebMvcConfigurerAdapter{

	@Autowired
	RoleToUserProfileConverter roleToUserProfileConverter;

	@Autowired
	IdToAreaManutencaoConverter idToAreaManutencaoConverter;
	
	@Bean
	public MaintenanceInterceptor maintenanceInterceptor() {
	    return new MaintenanceInterceptor();
	}
	
	
	/* interceptors	 
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new DomemAllInterceptor())			
		.addPathPatterns("/**");				

		/* intercepta tudo, depois verifica pelo ficheiro maintenance.properties
		 * se deve ou nao fazer redirect para View de manutencao*/
		registry.addInterceptor(maintenanceInterceptor())
		.addPathPatterns("/**")
		;
	}

	/**
	 * Configure ViewResolvers to deliver preferred views.
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	/**
	 * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	/**
	 * Configure Converter to be used.
	 * convert id da Role para user UserProfile e de idAreManutencao para objecto AreaManutencao
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(roleToUserProfileConverter);
		registry.addConverter(idToAreaManutencaoConverter);
	}

	/**
	 * Configure MessageSource to lookup any validation/error message in internationalized property files
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}    

}

