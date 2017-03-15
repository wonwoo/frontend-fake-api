package me.wonwoo.web;


import java.util.Optional;
import me.wonwoo.core.domain.Fake;
import me.wonwoo.core.repository.FakeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO test
 * Created by wonwoo on 2017. 3. 13..
 */
@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FakeRepository fakeRepository;

  @Test
  public void findByIdTest() throws Exception {
    given(fakeRepository.findOne(anyString()))
      .willReturn((new Fake("/test", HttpMethod.GET, HttpStatus.OK, "{\"id\":\"wonwoo\"}")));
    mockMvc.perform(get("/{id}", "testid"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.uri", is("/test")))
      .andExpect(jsonPath("$.method", is("GET")))
      .andExpect(jsonPath("$.statusCode", is("OK")))
      .andExpect(jsonPath("$.data", is("{\"id\":\"wonwoo\"}")));
    verify(fakeRepository, atLeastOnce()).findOne("testid");
  }

  @Test
  public void saveTest() throws Exception {
    given(fakeRepository.findByUriAndMethod(any(), any()))
      .willReturn(Optional.empty());

    final Fake value = new Fake("/test", HttpMethod.GET, HttpStatus.OK, "{\"id\":\"wonwoo\"}");
    given(fakeRepository.save(any(Fake.class))).willReturn(value);
    mockMvc.perform(post("/fakes")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"uri\":\"/test\",\"method\":\"GET\",\"statusCode\":\"OK\",\"data\":\"{\\\"id\\\":\\\"wonwoo\\\"}\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.uri", is("/test")))
      .andExpect(jsonPath("$.method", is("GET")))
      .andExpect(jsonPath("$.statusCode", is("OK")))
      .andExpect(jsonPath("$.data", is("{\"id\":\"wonwoo\"}")));
    verify(fakeRepository, atLeastOnce()).findByUriAndMethod("/test", "GET");
    verify(fakeRepository, atLeastOnce()).save(any(Fake.class));
  }

  @Test
  public void saveDuplicateExceptionTest() throws Exception {
    final Fake value = new Fake("/test", HttpMethod.GET, HttpStatus.OK, "{\"id\":\"wonwoo\"}");
    given(fakeRepository.findByUriAndMethod(any(), any()))
      .willReturn(Optional.of(value));
    mockMvc.perform(post("/fakes")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"uri\":\"/test\",\"method\":\"GET\",\"statusCode\":\"OK\",\"data\":\"{\\\"id\\\":\\\"wonwoo\\\"}\"}"))
      .andExpect(status().isBadRequest());
    verify(fakeRepository, atLeastOnce()).findByUriAndMethod("/test", "GET");
  }

  @Test
  public void updateTest() throws Exception{
    final Fake value = new Fake("/test", HttpMethod.GET, HttpStatus.OK, "{\"id\":\"wonwoo\"}");
    given(fakeRepository.save(any(Fake.class))).willReturn(value);
    mockMvc.perform(put("/fakes/{id}", "testId")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"uri\":\"/test\",\"method\":\"GET\",\"statusCode\":\"OK\",\"data\":\"{\\\"id\\\":\\\"wonwoo\\\"}\"}"))
      .andExpect(jsonPath("$.uri", is("/test")))
      .andExpect(jsonPath("$.method", is("GET")))
      .andExpect(jsonPath("$.statusCode", is("OK")))
      .andExpect(jsonPath("$.data", is("{\"id\":\"wonwoo\"}")));
    verify(fakeRepository, atLeastOnce()).save(any(Fake.class));
  }

  @Test
  public void deleteTest() throws Exception{
    doNothing().when(fakeRepository).delete(anyString());
    mockMvc.perform(delete("/fakes/{id}", "testId"))
      .andExpect(status().isOk());
    verify(fakeRepository, atLeastOnce()).delete("testId");
  }
}