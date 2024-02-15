# 캐시지정시 주의할점에 대해서..

### Cashable with FindAll()
> WARNING: Also, I don't generally recommend that you cache the results of a Repository.
> findAll() call, especially in production! 
> While this might work just fine locally given a limited data set, 
> the CrudRepository.findAll() method returns all results in the data structure 
> in the backing data store (e.g. the Person Table in an RDBMS) 
> for that particular object/data type (e.g. Person) by default, 
> unless you are employing paging or some LIMIT on the result set returned. 
> When it comes to caching, always think a high degree of reuse on relatively
> infrequent data changes; these are good candidates for caching.


### 참고
> https://docs.spring.io/spring-framework/docs/5.0.2.RELEASE/spring-framework-reference/integration.html#cache

> https://bcp0109.tistory.com/385

> https://docs.spring.io/spring-boot/docs/3.0.5/reference/html/io.html#io.caching



