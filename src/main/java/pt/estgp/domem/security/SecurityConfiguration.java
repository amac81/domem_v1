package pt.estgp.domem.security;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Entity
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
	
	/*
	 * configuracao dos acessos as views de acordo com as ROLES
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//TODO: VERIFICAR INCOERENCIAS !!!!!!
		
		http
			.csrf().csrfTokenRepository(csrfTokenRepository()).and()
		
			.authorizeRequests()			
			.antMatchers("/home","/erro","/manut/*","/chat","/energmon/*")
			.access("hasRole('SUPER') or hasRole('ADMIN') or hasRole('USER') or hasRole('MANUT')")
			
			.antMatchers("/users/editaruser","/users/loggedusers","/users/userslist","/users/newuser/*", "/users/delete-user-*")
			.access("hasRole('SUPER') or hasRole('ADMIN')")

			.antMatchers("/domotic/*")
			.access("hasRole('SUPER') or hasRole('ADMIN')")
			
			.antMatchers("/users/edit-user-*","/tarefas/novatarefa","/tarefas/listtarefas")				
			.access("hasRole('SUPER') or hasRole('ADMIN') or hasRole('USER') or hasRole('MANUT')")
			
			.and().formLogin().loginPage("/login")
			.defaultSuccessUrl("/home")
			.loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password").and()
			
			.logout().logoutSuccessUrl("/login?logout").deleteCookies("remove").invalidateHttpSession(true)
            .permitAll()
            
            .and().exceptionHandling().accessDeniedPage("/accessdenied");
			

		http
		.sessionManagement()		
		.maximumSessions(-1).sessionRegistry(sessionRegistry()).expiredUrl("/expired").and()
		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);	
	}
	
	@Autowired
	@Qualifier("sessionRegistry")
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
    
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}
	
	
	private CsrfTokenRepository csrfTokenRepository() 
	{ 
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
	    repository.setSessionAttributeName("_csrf");
	    return repository; 
	}
	

}
