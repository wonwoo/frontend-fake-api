package me.wonwoo.core.repository;

import java.util.Optional;
import me.wonwoo.core.domain.Fake;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
public interface FakeRepository extends MongoRepository<Fake, String> {

  Optional<Fake> findByUriAndMethod(String uri, String method);

}
