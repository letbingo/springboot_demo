package com.lego.web;

import com.lego.dao.PersonDAO;
import com.lego.pojo.Person;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonDAO personDAO;

    /**
     * 根据关键字全局搜索人员
     *
     * @param key 关键字
     * @return 人员列表
     */
    @GetMapping("/search")
    public List<Person> searchPerson(String key,
                                     @RequestParam(value = "index", defaultValue = "0") int index,
                                     @RequestParam(value = "size", defaultValue = "5") int size) {

        // BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
        //         .must(QueryBuilders.matchPhraseQuery("name", key));

        // 构建查询
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(key);
        QueryBuilder query = QueryBuilders.boolQuery().must(builder);

        // 分页、排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(index, size, sort);

        Iterable<Person> searchResult = personDAO.search(query, pageable);

        Iterator<Person> iterator = searchResult.iterator();
        List<Person> lstPeople = new ArrayList<>();
        while (iterator.hasNext()) {
            lstPeople.add(iterator.next());
        }
        return lstPeople;
    }

    /**
     * 添加人员
     *
     * @param person 人员信息
     * @return 插入结果
     */
    @PostMapping("/add")
    public int addPerson(@RequestBody Person person) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        person.setId(Integer.parseInt(sdf.format(new Date())));
        return personDAO.save(person).getId();
    }

    /**
     * 删除人员
     *
     * @param id 人员id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(personDAO.findById(id).get());
        return "delete";
    }

    /**
     * 清空人员
     *
     * @return
     */
    @DeleteMapping("/deleteall")
    public String deleteAllPerson() {
        personDAO.deleteAll();
        return "delete";
    }

    /**
     * 更新人员信息
     *
     * @param person 人员信息（包含id）
     * @return
     */
    @PostMapping("/update")
    public String updatePerson(@RequestBody Person person) {
        personDAO.save(person);
        return "update";
    }

    /**
     * 根据id获取一条信息
     *
     * @param id 人员id
     * @return
     */
    @RequestMapping("/{id}")
    public String getPerson(@PathVariable("id") int id) {
        // 最新版的spring-boot-starter-data-elasticsearch去掉了findOne，改成了findById
        Optional<Person> person = personDAO.findById(id);
        return person.get().toString();
    }
}