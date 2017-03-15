package me.wonwoo.config;

import me.wonwoo.socket.DefaultWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Created by wonwoo on 2017. 3. 15..
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketHandler(), "/checked")
      .addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
  }
  @Bean
  public WebSocketHandler webSocketHandler() {
    return new DefaultWebSocketHandler();
  }
}