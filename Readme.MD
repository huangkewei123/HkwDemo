#框架使用：SpringBoot2.0
#缓存使用：Redis
#数据层：mybatis
#权限控制：shiro
#数据库：mysql
#前端模板：thymeleaf
#分页：com.github.pagehelper

#预计前端会加入layUI

#Redis部分
关于Redis，使用注解方式进行缓存，所在目录com.example.demo.cache，暂时还只有CacheConfig起到作用，里面设置了缓存过期时间和空值无法存储，但是**空值存储会报错**，有待解决。
另外，FastJsonRedisSerializer类是为了将存入redis的数据**格式化成为json字符串**，但目前还未成功,还需解决（也就是说FastJsonRedisSerializer暂时没有起到效果）。

#shiro
现在暂时为关闭验证权限状态，如要打开只需要如下操作
打开com.example.demo.jee.security.ShiroConfig类，将filterChainDefinitionMap.put("/**", "anon");改成filterChainDefinitionMap.put("/**", "authc");即可。
shiro使用了自定义过滤器com.example.demo.jee.filter.MyFormAuthenticationFilter,实现了查询当前登录用户拥有的菜单，并放入session。没有实现“记住我”和“加盐”。
由于简单搭建，所以myFilter只是new了出来，将来完善，
没有加入登录过期时间。

#mycat
加入mycat中间件，实现读写分离，由于mycat为零入侵式，所以无多余配置，也无需代码
数据库主从复制是读写分离基础，另外参照资料配置

#thymeleaf
还在研究

#pagehelper
已加入,index为测试分页页面，采用ajax+layUI实现异步分页

#session
配置为15分钟过期

#过滤器、拦截器
实现了拦截器与过滤器配置，但是没有写逻辑

#加入爬虫，爬取漫画网站

#加入lombok
plugin中添加lombok插件

#附件实体添加
但是需要生成mapper和service