package me.wonwoo.core.domain;

import java.util.Date;
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
  private String uri;
  private HttpMethod method;
  private HttpStatus statusCode;
  private String data;
  private Date date;

  Fake(){

  }

  public Fake(String uri, HttpMethod method, HttpStatus statusCode, String data) {
    this.uri = uri;
    this.method = method;
    this.statusCode = statusCode;
    this.data = data;
  }

  public Fake(String uri, HttpMethod method, HttpStatus statusCode, String data, Date date) {
    this.uri = uri;
    this.method = method;
    this.statusCode = statusCode;
    this.data = data;
    this.date = date;
  }
}
