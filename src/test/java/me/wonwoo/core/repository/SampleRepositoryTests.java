package me.wonwoo.core.repository;

import java.util.List;
import me.wonwoo.core.domain.Fake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class SampleRepositoryTests {

  @Autowired
  private FakeRepository fakeRepository;

  @Test
  public void contextLoads() {
    final Fake fake = fakeRepository.save(new Fake("/test","GET", "{\"id\":\"wonwoo\"}"));
    assertThat(fake.getId()).isNotNull();
    assertThat(fake.getMethod()).isEqualTo("GET");
    assertThat(fake.getResponse()).isEqualTo("{\"id\":\"wonwoo\"}");
  }

  @Test
  public void findByName() {
    fakeRepository.save(new Fake("/test","GET", "{\"id\":\"wonwoo\"}"));
    final Fake fake = fakeRepository.findByUriAndMethod("/test", "GET").get();
    assertThat(fake.getId()).isNotNull();
    assertThat(fake.getMethod()).isEqualTo("GET");
    assertThat(fake.getResponse()).isEqualTo("{\"id\":\"wonwoo\"}");
  }
}