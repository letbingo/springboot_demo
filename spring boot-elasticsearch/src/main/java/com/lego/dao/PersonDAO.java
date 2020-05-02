package com.lego.dao;

import com.lego.pojo.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonDAO extends ElasticsearchRepository<Person, Integer> {

}