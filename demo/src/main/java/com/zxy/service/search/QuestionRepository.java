package com.zxy.service.search;

import com.zxy.model.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component(value="qr")
public interface QuestionRepository extends ElasticsearchRepository<Question,Integer> {
    public List<Question> findQuestionsByTextLikeOrTitleLike(String text,String title);
}
