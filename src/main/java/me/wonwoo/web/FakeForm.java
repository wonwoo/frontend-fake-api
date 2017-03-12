package me.wonwoo.web;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@Data
public class FakeForm {

  @NotBlank
  private String uri;

  @NotBlank
  private String method;

  @NotNull
  private int statusCode;

  @NotBlank
  private String data;

}
