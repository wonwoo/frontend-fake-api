package me.wonwoo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import me.wonwoo.core.domain.Fake;
import me.wonwoo.core.repository.FakeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by wonwoo on 2017. 3. 11..
 */

@Controller
@RequiredArgsConstructor
public class IndexController {

  private final FakeRepository fakeRepository;
  private final ObjectMapper objectMapper;

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("fakes", fakeRepository.findAll());
    return "index";
  }

  //FIXME test
  @PostMapping("/test")
  public void save(@RequestBody FakeForm fakeForm) {
    fakeRepository.save(new Fake(fakeForm.getUri(), fakeForm.getMethod(), isValidJson(fakeForm.getData())));
  }

  private String isValidJson(final String json) {
    try {
      objectMapper.readTree(json);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return json;
  }
}
