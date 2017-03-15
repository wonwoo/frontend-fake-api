package me.wonwoo.config;

import java.util.List;
import me.wonwoo.support.FakeHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(fakeHandlerMethodArgumentResolver(null));
  }

  @Bean
  public HandlerMethodArgumentResolver fakeHandlerMethodArgumentResolver(@Value("${fake.prefix:/api}") String prefix) {
    return new FakeHandlerMethodArgumentResolver(prefix);
  }
}