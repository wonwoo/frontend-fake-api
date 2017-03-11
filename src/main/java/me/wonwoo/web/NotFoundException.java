package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.support.FakeParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

  private final FakeParam fakeParam;

  @Override
  public String getMessage() {
    return "not found url : [" + fakeParam.getUri() + "]" + " method : [" + fakeParam.getMethod() + "]";
  }
}
