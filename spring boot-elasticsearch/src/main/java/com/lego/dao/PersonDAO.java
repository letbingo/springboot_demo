package com.lego.dao;
 
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
 
import com.lego.pojo.Person;
 
public interface PersonDAO extends ElasticsearchRepository<Person,Integer>{
 
}