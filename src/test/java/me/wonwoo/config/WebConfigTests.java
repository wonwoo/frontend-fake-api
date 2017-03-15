package me.wonwoo.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 3. 15..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebConfigTests {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void configTest() {
    assertThat(applicationContext.getBeansOfType(HandlerMethodArgumentResolver.class)
      .get("fakeHandlerMethodArgumentResolver")).isNotNull();
  }
}