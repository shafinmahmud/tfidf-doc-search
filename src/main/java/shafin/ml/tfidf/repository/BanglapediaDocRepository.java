package shafin.ml.tfidf.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import shafin.ml.tfidf.model.BanglapediaDoc;


//@Repository
public interface BanglapediaDocRepository extends MongoRepository<BanglapediaDoc, String>{

	Page<BanglapediaDoc> findAll(Pageable pageable);
}
