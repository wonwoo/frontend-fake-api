package me.wonwoo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontendFakeApiApplicationTests {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void applicationTest() {
    assertThat(this.applicationContext.getBeansOfType(SpringDataDialect.class)).isNotEmpty();
  }
}
