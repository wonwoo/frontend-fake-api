package me.wonwoo.core.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
@Data
@RequiredArgsConstructor
@Document
public class Fake {
  @Id
  private String id;
  private final String uri;
  private final String method;
  private final String response;
}
