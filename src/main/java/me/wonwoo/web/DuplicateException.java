package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wonwoo on 2017. 3. 12..
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RequiredArgsConstructor
public class DuplicateException extends RuntimeException {

  private final FakeForm fakeForm;

  @Override
  public String getMessage() {
    return "duplicate url : [" + fakeForm.getUri() + "]" + " method : [" + fakeForm.getMethod() + "]";
  }
}
