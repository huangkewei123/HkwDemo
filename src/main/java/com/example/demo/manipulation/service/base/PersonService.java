package com.example.demo.manipulation.service.base;

import com.example.demo.manipulation.entity.base.Person;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Created by yuhao.wang on 2017/6/19.
 */
public interface PersonService {

    List<Person> findAll();

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 每页显示记录数
     * @return
     */
    Page<Person> findByPage(int pageNo, int pageSize);

    void insert(Person person);

    public Person findPersonById(Integer id);

    public String cacheEvict(Integer id);

    public void update(Person person);

    public void delete(Integer id);
}