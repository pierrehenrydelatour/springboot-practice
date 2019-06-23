package com.zxy.service.search;

import com.zxy.model.Question;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import java.util.List;

@Component(value="ss")
public class SearchService {

    @Autowired
    private QuestionRepository qr;

    public List<Question> searchQuestion(String key){
        return qr.findQuestionsByTextLikeOrTitleLike(key,key);
    }

    public List<Question> testSearch(String keywords,int offset,int limit){
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(keywords);
        Pageable pageable =  PageRequest.of(offset,limit, Sort.by(Sort.Direction.DESC, "id"));
        Page<Question> page = qr.search(builder,pageable);
        System.out.println("总条数："+page.getTotalElements());
        System.out.println("总页数："+page.getTotalPages());
        return  page.getContent();
    }

}
