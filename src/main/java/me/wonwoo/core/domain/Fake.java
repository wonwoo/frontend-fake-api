package me.wonwoo.core.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@Data
@Document
public class Fake {
  @Id
  private String id;
  private final String uri;
  private final HttpMethod method;
  private final HttpStatus statusCode;
  private final String data;

}
