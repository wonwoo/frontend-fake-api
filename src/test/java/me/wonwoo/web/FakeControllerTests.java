package me.wonwoo.web;

import java.util.Collections;
import java.util.Optional;
import me.wonwoo.core.domain.Fake;
import me.wonwoo.core.repository.FakeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by wonwoo on 2017. 3. 11..
 */

@RunWith(SpringRunner.class)
@WebMvcTest(FakeController.class)
public class FakeControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FakeRepository fakeRepository;

  @Test
  public void getOkTest() throws Exception {
    given(fakeRepository.findByUriAndMethod(any(), any()))
      .willReturn(Optional.of(new Fake("/test", HttpMethod.GET, HttpStatus.OK, "{\"id\":\"wonwoo\"}")));
    mockMvc.perform(get("/api/test"))
      .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is("wonwoo")));
    verify(fakeRepository, atLeastOnce()).findByUriAndMethod("/test", "GET");
  }

  @Test
  public void postCreatedTest() throws Exception {
    given(fakeRepository.findByUriAndMethod(any(), any()))
      .willReturn(Optional.of(new Fake("/test", HttpMethod.POST, HttpStatus.CREATED, "{\"id\":\"wonwoo\"}")));
    mockMvc.perform(get("/api/test"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is("wonwoo")));
    verify(fakeRepository, atLeastOnce()).findByUriAndMethod("/test", "GET");
  }
}