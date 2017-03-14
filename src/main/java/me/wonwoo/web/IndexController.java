package me.wonwoo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.wonwoo.core.domain.Fake;
import me.wonwoo.core.repository.FakeRepository;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.data.domain.Pageable;
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
  public String home(Model model, Pageable pageable) {
    model.addAttribute("fakes", fakeRepository.findAll(pageable));
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
    final String uri = fakeForm.getUri().startsWith("/") ? fakeForm.getUri() : "/" + fakeForm.getUri();
    if (fakeRepository.findByUriAndMethod(uri, fakeForm.getMethod())
      .isPresent()) {
      throw new DuplicateException(fakeForm);
    }
    return ResponseEntity.ok().body(fakeRepository.save(new Fake(uri,
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
    JsonParser jsonParser = JsonParserFactory.getJsonParser();
    try {
      jsonParser.parseMap(json);
    } catch (IllegalArgumentException e) {
      try{
        jsonParser.parseList(json);
      }catch (IllegalArgumentException e1){
        throw new RuntimeException(e1);
      }
    }
    return json;
  }
}
