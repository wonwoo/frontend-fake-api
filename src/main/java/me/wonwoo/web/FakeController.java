package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.core.domain.Fake;
import me.wonwoo.core.repository.FakeRepository;
import me.wonwoo.support.FakeParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("${fake.prefix:/api}")
public class FakeController {

  private final FakeRepository fakeRepository;

  @RequestMapping(value = "/**", produces = "application/json", method = {GET, POST, PUT, PATCH, DELETE})
  public ResponseEntity<String> map(FakeParam fakeParam) {
    final Fake fake = fakeRepository.findByUriAndMethod(fakeParam.getUri(), fakeParam.getMethod())
      .orElseThrow(() -> new NotFoundException(fakeParam));
    return ResponseEntity.status(fake.getStatusCode()).body(fake.getData());
  }
}
