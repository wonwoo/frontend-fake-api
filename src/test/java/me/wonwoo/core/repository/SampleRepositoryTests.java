package me.wonwoo.core.repository;

import me.wonwoo.core.domain.Fake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
    final Fake fake = fakeRepository.save(new Fake("/test", HttpMethod.GET, HttpStatus.OK, "{\"id\":\"wonwoo\"}"));
    assertThat(fake.getId()).isNotNull();
    assertThat(fake.getMethod()).isEqualTo("GET");
    assertThat(fake.getHttpStatus()).isEqualTo(HttpStatus.OK);
    assertThat(fake.getData()).isEqualTo("{\"id\":\"wonwoo\"}");
  }

  @Test
  public void findByName() {
    fakeRepository.save(new Fake("/test", HttpMethod.GET, HttpStatus.OK, "{\"id\":\"wonwoo\"}"));
    final Fake fake = fakeRepository.findByUriAndMethod("/test", "GET").get();
    assertThat(fake.getId()).isNotNull();
    assertThat(fake.getMethod()).isEqualTo("GET");
    assertThat(fake.getHttpStatus()).isEqualTo(HttpStatus.OK);
    assertThat(fake.getData()).isEqualTo("{\"id\":\"wonwoo\"}");
  }
}