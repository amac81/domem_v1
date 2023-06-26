package pt.estgp.domem.configuration;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@ComponentScan("pt.estgp.domem")
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	
	public class MyHandshakeHandler extends DefaultHandshakeHandler {

		@Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            Principal principal = request.getPrincipal();
            if (principal == null) {
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ANONYMOUS"));
                principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
            }
            return principal;
        }
    }
	
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/wsdomem").setHandshakeHandler(new MyHandshakeHandler()).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		registry.setApplicationDestinationPrefixes("/domemstate","/apipilight");
		
		registry.enableSimpleBroker("/domemwsmsgbroker/","/queue");
		
	}
	
	
	
	
	
}