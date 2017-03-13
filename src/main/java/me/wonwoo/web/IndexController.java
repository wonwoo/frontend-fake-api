package me.wonwoo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.wonwoo.core.domain.Fake;
import me.wonwoo.core.repository.FakeRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable String id) {
    return ResponseEntity.ok(fakeRepository.findOne(id));
  }

  @PostMapping("/fakes")
  public ResponseEntity<?> save(@RequestBody @Valid FakeForm fakeForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    if (fakeRepository.findByUriAndMethod(fakeForm.getUri(), fakeForm.getMethod())
      .isPresent()) {
      throw new DuplicateException(fakeForm);
    }
    return ResponseEntity.ok().body(fakeRepository.save(new Fake(fakeForm.getUri(),
      HttpMethod.resolve(fakeForm.getMethod()),
      HttpStatus.valueOf(fakeForm.getStatusCode()),
      isValidJson(fakeForm.getData()))));
  }

  @PutMapping("/fakes/{id}")
  public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid FakeForm fakeForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    final Fake fake = new Fake(fakeForm.getUri(),
      HttpMethod.resolve(fakeForm.getMethod()),
      HttpStatus.valueOf(fakeForm.getStatusCode()),
      isValidJson(fakeForm.getData()));
    fake.setId(id);
    return ResponseEntity.ok().body(fakeRepository.save(fake));
  }

  @DeleteMapping("/fakes/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    fakeRepository.delete(id);
    return ResponseEntity.ok().build();
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
