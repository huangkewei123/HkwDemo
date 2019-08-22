package com.example.demo.manipulation.service.base.impl;

import com.example.demo.manipulation.entity.base.Person;
import com.example.demo.manipulation.mapper.base.PersonMapper;
import com.example.demo.manipulation.service.base.PersonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@Service
/*@Transactional(readOnly = true)*/
public class PersonServiceImpl implements PersonService {

    private Logger logger = Logger.getLogger("PersonService");

    @Resource
    private PersonMapper personMapper;


    @Override
    public Page<Person> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return personMapper.findByPage();
    }

    @Override
    @Transactional
    public void insert(Person person) {
        personMapper.insert(person);
    }


    // 因为配置文件继承了CachingConfigurerSupport，所以没有指定key的话就是用默认KEY生成策略生成,我们这里指定了KEY
    // value属性指定Cache名称
    // 使用key属性自定义key
    // condition属性指定发生的条件(如上示例表示只有当user的id为偶数时才会进行缓存)
    @Cacheable(value = {"personInfo","my-redis-cache1"}, key = "'findPerson' + #id" )
    @Override
    public Person findPersonById(Integer id) {
        //logger.info("aaaaaaaaaaaaaaaa"+JSON.toJSONString(personMapper.selectByPrimaryKey(id)));
        return personMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @CachePut也可以声明一个方法支持缓存功能。
     * 与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
     * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * 每次都会执行方法，并将结果存入指定的缓存中
     */
    @Cacheable(value = "persons")
    @Override
    public List<Person> findAll() {
        return personMapper.findAll();
    }

    @Override
    @CacheEvict(value = {"personInfo","persons"}, key = "'findPerson' + #person.getId()",allEntries = true)
    public void update(Person person ){
        personMapper.updateByPrimaryKeySelective(person);
    }
    ////------------------------------------------
    @Override
    @CacheEvict(value = {"personInfo","persons"}, key = "'findPerson' + #id" ,allEntries = true )
    public void delete(Integer id){
        personMapper.deleteByPrimaryKey(id);
    }

    /**
     * @Caching注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解。
     * 其拥有三个属性：cacheable、put和evict，分别用于指定@Cacheable、@CachePut和@CacheEvict。
     */
    @Caching(cacheable = @Cacheable("users"), evict = { @CacheEvict("cache2"),
            @CacheEvict(value = "cache3", allEntries = true) })
    public Person find(Integer id) {
        return personMapper.selectByPrimaryKey(id);
    }

    @Override
    // @CacheEvict(value="propertyInfo",allEntries=true) 清空全部
    // 删除指定缓存
    @CacheEvict(value = "personInfo", key = "'findPerson' + #id")
    // 其他属性
    // allEntries是boolean类型，表示是否需要清除缓存中的所有元素。默认为false，表示不需要。当指定了allEntries为true时，Spring Cache将忽略指定的key。
    // 清除操作默认是在对应方法成功执行之后触发的，即方法如果因为抛出异常而未能成功返回时也不会触发清除操作。
    // 使用beforeInvocation可以改变触发清除操作的时间，当我们指定该属性值为true时，Spring会在调用该方法之前清除缓存中的指定元素。
    public String cacheEvict(Integer id) {
        logger.info("删除缓存" + id);
        return null;
    }
}