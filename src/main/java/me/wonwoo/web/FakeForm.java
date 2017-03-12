package me.wonwoo.web;

import lombok.Data;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@Data
public class FakeForm {

  private String uri;
  private String method;
  private int statusCode;
  private String data;

}
