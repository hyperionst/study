

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL은 메타모델 없이 쿼리를 쉽게 만들 수 있게 도와주는 Kotlin 라이브러리입니다.
이미 Annotation Processor Tool (APT)을 이용해서 쿼리를 쉽게 만들도록 도와주는 라이브러리는 많이 있습니다.
하지만 APT를 이용하면 클래스명이나 필드명이 변경되었을 때마다 다시 컴파일 해야 하는 불편함이 있습니다.
Kotlin JDSL은 이런 불편함을 해소하면서 쉽게 쿼리를 만들 수 있도록 KClass와 KProperty 기반의 Domain-Specific Language를 제공합니다.

Kotlin JDSL은 새로운 DB 라이브러리가 아닌 DB 라이브러리를 사용할 때 도움을 주기 위해서 만들어진 라이브러리이기 때문에 직접 쿼리를 실행하거나 DB 라이브러리를 래핑하는 등의 작업은 하지 않습니다.

## Supports

### JPQL

```kotlin
val context = JpqlRenderContext()

val query = jpql {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class),
        join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
    ).groupBy(
        path(Author::authorId),
    ).orderBy(
        count(Author::authorId).desc(),
    )
}

val `the most prolific author` = entityManager.createQuery(query, context).apply {
    maxResults = 1
}
```

-------
# JPQL with Kotlin JDSL

## Requirements

Kotlin JDSL을 사용하기 위해서는 Java 8 (혹은 그 이상) and Kotlin 1.9 (그 이상)이 요구됩니다.

## Configure the repositories

Kotlin JDSL dependency를 추가하기 전에 maven repository가 추가 되어야 합니다.

### Release

Kotlin JDSL의 release는 모두 [Maven central repository](https://central.sonatype.com/search?q=g%3Acom.linecorp.kotlin-jdsl)에 업로드 됩니다.
이를 이용하기 위해서는 maven repository가 빌드 스크립트에 추가 되어 있어야 합니다.


{% tab title="Gradle (Kotlin)" %}

```kotlin
repositories {
    mavenCentral()
}
```


### Snapshot

Kotlin JDSL의 snapshot은 모두 OSS snapshot repository에 업로드 됩니다.
이를 이용하기 위해서는 OSS snapshot repository가 빌드 스크립트에 추가 되어 있어야 합니다.

{% tab title="Gradle (Kotlin)" %}

```kotlin
repositories {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}
```



## Add Kotlin JDSL dependencies

### Core dependencies

Kotlin JDSL을 실행시키기 위해서는 다음 dependency들이 필수로 요구됩니다.

- jpql-dsl: JPQL 쿼리를 만들 수 있게 도와주는 DSL
- jpql-render: DSL로 만든 쿼리를 String으로 변환시켜주는 라이브러리


```kotlin
dependencies {
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.3.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.3.0")
}
```

### Support dependencies

Kotlin JDSL은 DSL로 생성된 쿼리를 실행시킬 수 있는 Support dependency를 제공합니다.
각 JPA 제공자에 맞춰 다음 dependency들 중에서 선택하여 사용할 수 있습니다.

- hibernate-support: Hibernate를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- eclipselink-support: EclipseLink를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-batch-support: Spring Batch와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-data-jpa-support: Spring Data Jpa와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.
- hibernate-reactive-support: Hibernate Reactive와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.

#### Javax

javax 패키지를 사용하는 경우 다음 dependency들 중에서 선택하여 사용할 수 있습니다.

- hibernate-javax-support: Hibernate를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- eclipselink-javax-support: EclipseLink를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-batch-javax-support: Spring Batch와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-data-jpa-javax-support: Spring Data Jpa와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.
- hibernate-reactive-javax-support: Hibernate Reactive와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.

## Build a query

`jpql()`에서 `select()`를 호출하는 것으로 [select statement](statements.md#select-statement)를 만들 수 있습니다.

```kotlin
val query = jpql {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class)
    )
}
```

유사하게 Kotlin JDSL은 다른 statement를 위한 함수도 지원합니다: [update statement](statements.md#update-statement), [delete statement](statements.md#delete-statement).
더 많은 예제를 보고 싶으시면 GitHub에 [examples](https://github.com/line/kotlin-jdsl/tree/main/example)을 참고해주세요.

추가로 [custom DSL](custom-dsl.md)을 통해 본인만의 DSL을 만들 수도 있습니다.

## Execute the query

쿼리를 만든 뒤에는 `RenderContext`를 이용해 쿼리를 실행할 수 있습니다.
예를 들어 `JpqlRenderContext`로 다음과 같이 실행이 가능합니다.

```kotlin
val context = JpqlRenderContext()

val renderer = JpqlRenderer()

val rendered = renderer.render(query, context)

val jpaQuery: Query = entityManager.createQuery(rendered.query).apply {
    rendered.params.forEach { (name, value) ->
        setParameter(name, value)
    }
}

val result = jpaQuery.resultList
```

`RenderContext`는 쿼리를 String으로 랜더링할 수 있는 요소들을 가지고 있습니다.
Kotlin JDSL은 `RenderContext`의 default 구현체로 `JpqlRenderContext`를 제공합니다.

`JpqlRenderer`는 `RenderContext`를 이용해 쿼리를 String으로 랜더링합니다.
`JpqlRenderer`는 String으로 랜더링된 `query`와 쿼리에 포함된 `parameters`를 가지고 있는 `JpqlRendered`를 반환합니다.
`JpqlRenderer`는 상태를 가지지 않기 때문에 객체의 재사용이 가능하며 멀티 쓰레드 환경에서 사용하기에 안전합니다.

`RenderContext`를 만드는 비용은 비싸기 때문에 한번만 만들고 이를 재활용하는 것을 추천드립니다.
`RenderContext`는 immutable 객체로 멀티 쓰레드 환경에서 사용하기에 안전합니다.


[Kotlin JDSL Support](#support-dependencies)는 위 실행 과정을 간략화 시킨 `EntityManager`의 extension function들을 제공합니다.
이를 이용해 쿼리를 쉽게 실행할 수 있습니다.

```kotlin
val context = JpqlRenderContext()

val jpaQuery: Query = entityManager.createQuery(query, context)

val result = jpaQuery.resultList
```
-------
# Statements

JPQL은 select, update, delete statement를 지원합니다.
Kotlin JDSL은 이 statement들을 만들 수 있는 DSL을 제공합니다.

## Select statement

`jpql()`에서 `select()`를 호출하는 것으로 select statement를 만들 수 있습니다.

```kotlin
val query = jpql {
    select(
        path(Employee::employeeId),
    ).from(
        entity(Employee::class),
        join(Employee::departments),
    ).where(
        type(entity(Employee::class)).eq(FullTimeEmployee::class)
    ).groupBy(
        path(Employee::employeeId),
    ).having(
        count(Employee::employeeId).greaterThan(1L),
    ).orderBy(
        count(Employee::employeeId).desc(),
        path(Employee::employeeId).asc(),
    )
}
```

### Select clause

select statement의 select clause를 만들기 위해, `select()`를 이용할 수 있습니다.
`select()`는 [`Expression`](expressions.md)을 파라미터로 받아 프로젝션을 표현합니다.
만약 하나의 `Expression`만 `select()`에 넘어온다면 타입 추론으로 select statement의 타입을 결정하지만 하나 이상의 `Expression`이 넘어온다면 타입 명시가 필요합니다.

```kotlin
// It can infer the result type.
select(path(Author::authorId))

// It cannot infer the result type.
select(path(Author::authorId), path(Author::name))

// This allows it to know the result type.
select<CustomEntity>(path(Author::authorId), path(Author::name))
```

#### DTO projection

DTO 클래스와 클래스의 생성자를 `selectNew()`에 넘기는 것으로 DTO 프로젝션을 만들 수 있습니다.

```kotlin
data class Row(
    val departmentId: Long,
    val count: Long,
)

selectNew<Row>(
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
)
```

### From clause

select statement의 from clause를 만들기 위해, `from()`을 이용할 수 있습니다.
`from()`은 [Entity](entities.md)와 [Join](statements.md#join)을 파라미터로 받아 어떤 entity를 통해 조회가 되는지 표현합니다.

```kotlin
from(
    entity(Author::class),
    join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
)
```

#### Join

조회되는 entity를 조인하기 위해, `join()`과 `fetchJoin()`을 사용할 수 있습니다.
Join에는 2종류가 있으며 일반 Join과 연관관계 Join이 있습니다.
두 Join은 연관관계가 있는 entity를 조인하는지 없는 entity를 조인하는지에 따라 구별됩니다.

```kotlin
@Entity
// ...
class Book(
    // ...

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    val authors: MutableSet<BookAuthor>,
)

@Entity
// ...
class BookAuthor(
    @Id
    @Column(name = "author_id")
    val authorId: Long,
) {
    @Id
    @ManyToOne
    @JoinColumn(name = "isbn")
    lateinit var book: Book
}

@Entity
// ...
class Author(
    @Id
    @Column(name = "author_id")
    val authorId: Long,

    // ...
)

from(
    entity(Book::class),
    join(Book::authors), // Association Join
    join(Author::class).on(path(BookAuthor::authorId).eq(path(Author::authorId))), // Join
)
```

`join()` 이후에 `as()`를 호출하는 것으로 조인될 entity에 alias를 부가할 수 있습니다.
만약 동일한 타입의 entity를 여러개 from clause에 포함시킬 때 이 기능을 이용할 수 있습니다.

```kotlin
from(
    entity(Book::class),
    join(Book::authors).`as`(entity(BookAuthor::class, "author")),
)
```

### Where clause

select statement의 where clause를 만들기 위해, `where()`를 사용할 수 있습니다.
`where()`은 [Predicate](predicates.md)를 파라미터로 받아 조회 데이터의 제약을 표현합니다.
`where()`와 `and()`의 축약어로 `whereAnd()`를 사용할 수 있습니다.
마찬가지로 `where()`와 `or()`의 축약어로 `whereOr()`을 사용할 수 있습니다.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

### Group by clause

select statement의 group by clause를 만들기 위해, `groupBy()`를 사용할 수 있습니다.
`groupBy() 는 [Expression](expressions.md)을 파라미터로 받아 데이터의 그룹핑을 표현합니다.

```kotlin
groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

select statement의 having clause를 만들기 위해, `having()`을 사용할 수 있습니다.
`having()`은 [Expression](expressions.md)을 파라미터로 받아 추가적인 조회 데이터의 제약을 표현합니다.
`having()`과 `and()`의 축약어로 `havingAnd()`를 사용할 수 있습니다.
마찬가지로 `having()`과 `or()`의 축약어로 `havingOr()`을 사용할 수 있습니다.

```kotlin
having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

select statment의 order by clause를 만들기 위해, `orderBy()`를 사용할 수 있습니다.
`orderBy()`는 [Sort](sorts.md)를 파라미터로 받아 데이터의 정렬을 표현합니다.

```kotlin
orderBy(
    path(Book::isbn).asc(),
)
```

----

## Update statement

`jpql()`에서 `update()`를 호출하는 것으로 update statement를 만들 수 있습니다.

```kotlin
val query = jpql {
    update(
        entity(Book::class)
    ).set(
        path(Book::price)(BookPrice::value),
        BigDecimal.valueOf(100)
    ).set(
        path(Book::salePrice)(BookPrice::value),
        BigDecimal.valueOf(80)
    ).where(
        path(Book::isbn).eq(Isbn("01"))
    )
}
```

### Update clause

update statment의 update clause를 만들기 위해, `update()`를 사용할 수 있습니다.
`update()`는 [Entity](entities.md)를 파라미터로 받아 수정될 entity를 표현합니다.

```kotlin
update(
    entity(Employee::class),
)
```

### Set clause

update statement의 set clause를 만들기 위해, `set()`을 사용할 수 있습니다.
`set()`은 [Expression](expressions.md)을 파라미터로 받아 할당을 표현합니다.
`set()`을 여러번 호출하는 것으로 여러 개를 할당할 수 있습니다.

```kotlin
set(
    path(Book::price)(BookPrice::value),
    BigDecimal.valueOf(100)
).set(
    path(Book::salePrice)(BookPrice::value),
    BigDecimal.valueOf(80)
)
```

### Where clause

update statement의 where clause를 만들기 위해, `where()`를 사용할 수 있습니다.
`where()`은 [Predicate](predicates.md)를 파라미터로 받아 조회 데이터의 제약을 표현합니다.
`where()`와 `and()`의 축약어로 `whereAnd()`를 사용할 수 있습니다.
마찬가지로 `where()`와 `or()`의 축약어로 `whereOr()`을 사용할 수 있습니다.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

## Delete statement

`jpql()`에서 `deleteFrom()`를 호출하는 것으로 delete statement를 만들 수 있습니다.

```kotlin
val query = jpql {
    deleteFrom(
        entity(Book::class),
    ).where(
        path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
    )
}
```

### Delete from clause

delete statement의 delete clause를 만들기 위해, `deleteFrom()`을 사용할 수 있습니다.
`deleteFrom()`은 [Entity](entities.md)를 파라미터로 받아 삭제할 entity를 표현합니다.

```kotlin
deleteFrom(
    entity(Book::class),
)
```

### Where clause

delete statement의 where clause를 만들기 위해, `where()`를 사용할 수 있습니다.
`where()`은 [Predicate](predicates.md)를 파라미터로 받아 조회 데이터의 제약을 표현합니다.
`where()`와 `and()`의 축약어로 `whereAnd()`를 사용할 수 있습니다.
마찬가지로 `where()`와 `or()`의 축약어로 `whereOr()`을 사용할 수 있습니다.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```
------
# Entities

Kotlin JDSL은 JPQL의 entity를 표현하기 위해 `Entity` 인터페이스를 가지고 있습니다.
`Entity`를 만들기 위해, `entity()`를 사용할 수 있습니다.

```kotlin
entity(Book::class)
```

## Alias

모든 `Entity`는 alias를 가지고 있습니다.
만약 `entity()`에 alias를 명시하지 않으면 Kotlin JDSL이 class 명을 통해 자동으로 alias를 생성합니다.
`Entity`는 alias를 통해 구분이 되기 때문에 만약 동일한 타입의 `Entity`를 하나 이상 사용한다면 이 `Entity`들을 구별하기 위해서 alias가 필요합니다.

```kotlin
entity(Book::class)
entity(Book::class, Book::class.simpleName!!)

entity(Book::class, alias = "book1")
entity(Book::class, alias = "book2")
```

## Expression

`Entity`는 [select clause](statements.md#select-clause) 나 [predicate](predicates.md) 등에서 [`Expression`](expressions.md)으로 사용될 수 있습니다.

```kotlin
// SELECT b FROM Book AS b WHERE b.isbn.value = :param1
jpql {
    select(
        entity(Book::class, "b"),
    ).from(
        entity(Book::class, "b"),
    ).where(
        entity(Book::class, "b")(Book::isbn)(Ibsn::value).eq("01"),
    )
}
```

## Treat

`Entity`의 타입을 자식 타입으로 변경하기 위해, `treat()`를 사용할 수 있습니다.

```kotlin
entity(Employee::class).treat(FullTimeEmployee::class)
```

-----
# Paths

Kotlin JDSL은 JPQL의 path expression을 표현하기 위해서 `Path` 인터페이스를 가지고 있습니다.
`Path`를 만들기 위해, `path()` 와 `invoke()`를 사용할 수 있습니다.

```kotlin
// Book.isbn.value
path(Book::isbn).path(Isbn::value)
path(Book::isbn)(Isbn::value)

// b.isbn.value
entity(Book::class, "b").path(Book::isbn).path(Isbn::value)
entity(Book::class, "b")(Book::isbn)(Isbn::value)
```

## Java entity

`path()` 와 `invoke()`는 `KProperty1` 또는 `KFuction1`를 인자로 받습니다.
`KFunction1`의 경우, getter만 public인 Java로 선언한 entity를 사용할 때 유용합니다.

```java
@Entity
public class Book {
    @Id
    private Long id;

    private String title;

    public String getTitle() {
        return title;
    }
}
```

```kotlin
// compile error
path(Book::title)

// Book.title
path(Book::getTitle)
```

Kotlin JDSL은 getter 이름에서 프로퍼티 이름을 추론하기 위해 다음 규칙을 따릅니다.

- `is`로 시작하는 경우 이름 그대로 사용합니다.
- `get`으로 시작하는 경우 `get`을 제거하고 이후 첫 글자를 소문자로 변경합니다.
- 그 외의 경우, 이름 그대로 사용합니다.

```kotlin
// Book.isAvailable
path(Book::isAvailable)

// Book.available
path(Book::getAvailable)
```

위 규칙 대신 나만의 규칙을 사용하고 싶다면, `JpqlPropertyIntrospector`를 구현하고 이를 이를 `RenderContext`에 추가해야 합니다.
더 자세한 내용은 [Custom DSL](./custom-dsl.md)을 참고하세요.
Spring을 사용하고 있다면 [Spring supports](./spring-supports.md)도 참고하세요.

```kotlin
class MyIntrospector : JpqlPropertyIntrospector() {
    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? {
        if (property is KFunction1<*, *>) {
            // 나만의 규칙으로 이름을 추론합니다
            val name = "test"
            return MyProperty(name)
        }
        return null
    }

    private data class MyProperty(
        override val name: String,
    ) : JpqlPropertyDescription
}

val myModule = object : JpqlRenderModule {
    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        context.prependIntrospector(MyIntrospector())
    }
}

val myContext = JpqlRenderContext().registerModule(myModule)
```

## Expression

`Path`는 [select clause](statements.md#select-clause) 나 [predicate](predicates.md) 등에서 [`Expression`](expressions.md)으로 사용될 수 있습니다.

```kotlin
// SELECT Book.isbn FROM Book AS Book WHERE Book.isbn.value = :param1
jpql {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    ).where(
        path(Book::isbn)(Ibsn::value).eq("01"),
    )
}
```

## Treat

`Path`의 타입을 자식 타입으로 변경하기 위해, `treat()`를 사용할 수 있습니다.

```kotlin
path(EmployeeDepartment::employee).treat(FullTimeEmployee::class)
```

-------
# Expressions

Kotlin JDSL은 JPQL의 expression를 표현하기 위해 `Expression` 인터페이스를 가지고 있습니다.

## Alias

`Expression`의 `as()`를 호출하는 것으로 `Expression`에 alias를 걸 수 있습니다.
`expression()`을 이용하면 `Expression`의 참조를 만들 수 있습니다.
참조는 alias를 통해서 구분되며 동일한 alias를 가지고 있는 `Expression`을 참조할 수 있습니다.
이를 통해 `Expression`에 alias를 걸고 alias가 걸린 `Expression`을 다른 clause에서 참조할 수 있습니다.

```kotlin
val bookPrice = expression(BigDecimal::class, "price")

select(
    path(Book::price)(BookPrice::value).`as`(bookPrice)
).from(
    entity(Book::class)
).where(
    bookPrice.eq(BigDecimal.valueOf(100))
)

// OR

select(
    path(Book::price)(BookPrice::value).`as`(expression("price"))
).from(
    entity(Book::class)
).where(
    expression(BigDecimal::class, "price").eq(BigDecimal.valueOf(100))
)
```

### Type Cast

어떤 경우에는 `Expression`의 타입을 원하는 타입으로 변경하고 싶을 때가 있을 것입니다.
이를 위해 Kotlin JDSL은 `as()`를 통해서 unsafe type casting을 지원합니다.

{% hint style="info" %}
This is a shortened form of `as Expression<T>`, so it may not work as expected.
{% endhint %}

```kotlin
avg(path(FullTimeEmployee::annualSalary)(EmployeeSalary::value)).`as`(BigDecimal::class)
```

## Arithmetic operations

산술 연사자를 만들기 위해서는 다음 함수들을 사용할 수 있습니다.

* \+ (plus)
* \- (minus)
* \* (times)
* / (div)

```kotlin
path(Book::price).plus(path(Book::salePrice))
plus(path(Book::price), path(Book::salePrice))

path(Book::price).minus(path(Book::salePrice))
minus(path(Book::price), path(Book::salePrice))

path(Book::price).times(path(Book::salePrice))
times(path(Book::price), path(Book::salePrice))

path(Book::price).div(path(Book::salePrice))
div(path(Book::price), path(Book::salePrice))
```

### Parentheses

확장 함수가 아닌 일반 함수를 호출하는 것으로 산술 연산자에 연산 순서를 위한 소괄호를 추가할 수 있습니다.
확장 함수의 경우 연산 순서가 모호해서 소괄호를 추가할 수 없습니다.

```kotlin
// 일반 함수: (Book.price - Book.salePrice) * (100)
times(
    path(Book::price).minus(path(Book::salePrice)),
    BigDecimal.valueOf(100),
)

// 확장 함수: Book.price - Book.salePrice * 100
path(Book::price).minus(path(Book::salePrice)).times(BigDecimal.valueOf(100))
```

## Values

값을 만들기 위해, `value()`를 사용할 수 있습니다.
모든 값은 쿼리 파라미터로 치환되며, 이 파라미터는 변경할 수 없습니다.

>만약 KClass가 `value()`에 전달되면 이는 [Entity](entities.md)로 인식됩니다.

```kotlin
// SELECT Book.isbn FROM Book as Book WHERE Book.price = ?
select(
    path(Book::isbn)
).from(
    entity(Book::class)
).where(
    path(Book::price).eq(value(BigDecimal.valueOf(100)))
)
```

### Params

쿼리 파라미터를 만들기 위해, `value()` 대신 `param()`을 사용할 수 있습니다.
`param()`으로 만들어진 파라미터는 변경이 가능합니다.

```kotlin
val context = JpqlRenderContext()

val query = jpql {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::price).eq(param("price"))
    )
}

val queryParams = mapOf(
    "price" to BigDecimal.valueOf(100)
)

entityManager.createQuery(query, queryParams, context)
```

### Literals

literal을 만들기 위해, `value()` 대신 `xxxLiteral()`을 이용할 수 있습니다.

> string literal을 출력할 때 만약 '(작은 따옴표)가 있으면 '(작은 따옴표)는 ''(작은 따옴표 2개)로 변경되어 출력됩니다. 예로 'literal''s' 처럼 출력됩니다.

| Type    | Function       | Rendered                     |
| ------- | -------------- | ---------------------------- |
| Int     | intLiteral     | {value}                      |
| Long    | longLiteral    | {value}L                     |
| Float   | floatLiteral   | {value}F                     |
| Double  | doubleLiteral  | {value}                      |
| Boolean | booleanLiteral | TRUE or FALSE                |
| Char    | charLiteral    | '{value}'                    |
| String  | stringLiteral  | '{value}'                    |
| Enum    | enumLiteral    | {qualified name}.{enum name} |

## Aggregation functions

집합 함수를 만들기 위해, 다음 함수들을 사용할 수 있습니다.

* COUNT (count)
* MIN (min)
* MAX (max)
* AVG (avg)
* SUM (sum)

```kotlin
count(path(Book::price))
countDistinct(path(Book::price))

max(path(Book::price))
maxDistinct(path(Book::price))

min(path(Book::price))
minDistinct(path(Book::price))

sum(path(Book::price))
sumDistinct(path(Book::price))
```

### Sum

`sum()`은 파라미터에 따라 다른 반환 타입을 가지게 됩니다.

| Type       | Return Type |
| ---------- | ----------- |
| Int        | Long        |
| Long       | Long        |
| Float      | Double      |
| Double     | Double      |
| BigInteger | BigInteger  |
| BigDecimal | BigDecimal  |

## Functions

Kotlin JDSL은 JPA에서 제공하는 여러 함수들을 지원하기 위함 함수들을 제공합니다.

### String functions

| Function  | DSL function |
| --------- | ------------ |
| CONCAT    | support      |
| SUBSTRING | support      |
| TRIM      | support      |
| LOWER     | support      |
| UPPER     | support      |
| LENGTH    | support      |
| LOCATE    | support      |

### Arithmetic functions

| Function | DSL function |
| -------- | ------------ |
| ABS      | not yet      |
| CEILING  | not yet      |
| EXP      | not yet      |
| FLOOR    | not yet      |
| LN       | not yet      |
| MOD      | not yet      |
| POWER    | not yet      |
| ROUND    | not yet      |
| SIGN     | not yet      |
| SQRT     | not yet      |
| SIZE     | not yet      |
| INDEX    | not yet      |

### Datetime functions

| Function           | DSL function |
| ------------------ | ------------ |
| CURRENT\_DATE      | not yet      |
| CURRENT\_TIME      | not yet      |
| CURRENT\_TIMESTAMP | not yet      |
| LOCAL DATE         | not yet      |
| LOCAL TIME         | not yet      |
| LOCAL DATETIME     | not yet      |
| EXTRACT            | not yet      |

### Database function

DB 함수나 사용자 정의 함수를 만들기 위해, `function()`을 사용할 수 있습니다.

```kotlin
function(String::class, "myFunction", path(Book::isbn))
```

> 사용할 함수의 정보를 JPA 제공자에 등록할 필요가 있을 수 있습니다.
>
> 예를 들어 Hibernate를 사용하고 있다면 `FunctionContributor`를 반드시 등록해야 합니다.



## Cases

case를 만들기 위해, `caseWhen()`과 `caseValue()`를 사용할 수 있습니다.

```kotlin
caseWhen(path(Book::price).lt(BigDecimal.valueOf(100))).then("0")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(200))).then("100")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(300))).then("200")
    .`else`("300")

caseValue(path(Book::price))
    .`when`(BigDecimal.valueOf("100")).then("10")
    .`when`(BigDecimal.valueOf("200")).then("20")
    .`when`(BigDecimal.valueOf("300")).then("30")
    .`else`(path(Book::price))
```

### Coalesce

coalesce를 만들기 위해, `coalesce()`을 사용할 수 있습니다.

```kotlin
coalesce(path(Employee::nickname), path(Employee::name))
```

### NullIf

nullIf를 만들기 위해, `nullIf()`을 사용할 수 있습니다.

```kotlin
nullIf(path(Book::price), BigDecimal.ZERO)
```

## New

DTO 프로젝션을 만들기 위해, `new()`를 사용할 수 있습니다.

```kotlin
data class Row(
    val departmentId: Long,
    val count: Long,
)

new(
    Row::class
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
)
```

## Type

type 연산자를 만들기 위해, `type()`을 사용할 수 있습니다.

```kotlin
select(
    path(Employee::id)
).from(
    entity(Employee::class)
).where(
    type(entity(Employee::class)).eq(FullTimeEmployee::class)
)
```

## Custom expression

커스텀 expression을 만들기 위해, `customExpression()`을 사용할 수 있습니다.

```kotlin
customExpression(String::class, "CAST({0} AS VARCHAR)", path(Book::price))
```

만약 `customExpression()`을 많이 사용한다면 [나만의 DSL](custom-dsl.md)을 만드는 것을 고려해보세요.

-------
# Predicates

Kotlin JDSL은 JPQL의 conditional expression을 표현하기 위해 `Predicate` 인터페이스를 가지고 있습니다.

## Logical operators

논리 연산을 만들기 위해, 다음 함수들을 사용할 수 있습니다.

* AND (and)
* OR (or)
* NOT (not)

> 만약 `and()` 와 `or()`로 넘어온 `Predicate`가 모두 null 이거나 비어 있으면, `and()`의 경우에는 `1 = 1`로 `or()`의 경우에는 `0 = 1`로 해석됩니다. 그렇기 떄문에 다이나믹 쿼리를 만들 때 조심해야 합니다.

```kotlin
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01"))
and(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

path(Employee::name).eq("Employee01").or(path(Employee::nickname).eq("E01"))
or(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

not(path(Employee::name).eq("Employee01"))
```

### Parentheses

확장 함수가 아닌 일반 함수를 호출하는 것으로 논리 연산자에 연산 순서를 위한 소괄호를 추가할 수 있습니다.
확장 함수의 경우 연산 순서가 모호해서 소괄호를 추가할 수 없습니다.

```kotlin
// 일반 함수: (Employee.name = 'Employee01' AND Employee.nickname = 'E01') or (Employee.name = 'Employee02' AND Employee.nickname = 'E02')
or(
    path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")),
    path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")),
)

// 확장 함수: Employee.name = 'Employee01' AND Employee.nickname = 'E01' or Employee.name = 'Employee02' AND Employee.nickname = 'E02'
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")).or(path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")))
```

## Comparison operators

비교 연산을 만들기 위해, 다음 함수들을 사용할 수 있습니다.

* \= (equal or eq)
* <> (notEqual or ne)
* \> (greaterThan or gt)
* \>= (greaterThanOrEqualTo or ge)
* < (lessThan or lt)
* <= (lessThanOrEqualTo or le)

```kotlin
path(Book::price).equal(BigDecimal.valueOf(100))
path(Book::price).eq(BigDecimal.valueOf(100))

path(Book::price).notEqual(BigDecimal.valueOf(100))
path(Book::price).ne(BigDecimal.valueOf(100))

path(Book::price).greaterThan(BigDecimal.valueOf(100))
path(Book::price).gt(BigDecimal.valueOf(100))

path(Book::price).greaterThanOrEqualTo(BigDecimal.valueOf(100))
path(Book::price).ge(BigDecimal.valueOf(100))

path(Book::price).lessThan(BigDecimal.valueOf(100))
path(Book::price).lt(BigDecimal.valueOf(100))

path(Book::price).lessThanOrEqualTo(BigDecimal.valueOf(100))
path(Book::price).le(BigDecimal.valueOf(100))
```

### All or Any

함수 이름 마지막에 `all`과 `any`를 붙이는 것으로 subquery에 대한 All과 Any 연산을 할 수 있습니다.

```kotlin
val query = jpql {
    val annualSalaries = select(
        path(FullTimeEmployee::annualSalary)(EmployeeSalary::value),
    ).from(
        entity(FullTimeEmployee::class),
        join(FullTimeEmployee::departments),
    ).where(
        path(EmployeeDepartment::departmentId).eq(3L),
    ).asSubquery()

    select(
        path(FullTimeEmployee::employeeId),
    ).from(
        entity(FullTimeEmployee::class),
    ).where(
        path(FullTimeEmployee::annualSalary)(EmployeeSalary::value).gtAll(annualSalaries),
    )
}
```

## Null

null 비교 연산을 만들기 위해, `isNull()`과 `isNotNull()`을 사용할 수 있습니다.

```kotlin
path(Employee::nickname).isNull()

path(Employee::nickname).isNotNull()
```

## Like

like 비교 연산을 만들기 위해, `like()`와 `notLike()`를 사용할 수 있습니다.

```kotlin
path(Employee::nickname).like("E%")
path(Employee::nickname).like("E_", escape = '_')

path(Employee::nickname).notLike("E%")
path(Employee::nickname).notLike("E_", escape = '_')
```

## Between

between 비교 연산을 만들기 위해, `between()`과 `notBetween()`을 사용할 수 있습니다.

```kotlin
path(Employee::price).between(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## In

in 비교 연산을 만들기 위해, `in()`과 `notIn()`을 사용할 수 있습니다.

```kotlin
path(Employee::price).`in`(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notIn(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## Exists

exists 연산을 만들기 위해, `exists()`와 `notExists()`을 사용할 수 있습니다.

```kotlin
exists(subquery)

notExists(subquery)
```

## Empty

empty 연산을 만들기 위해, `isEmpty()`와 `isNotEmpty()`을 사용할 수 있습니다.

```kotlin
path(Employee::departments).isEmpty()

path(Employee::departments).isNotEmpty()
```

----------------------------------

## Database function

DB 함수나 사용자 정의 함수를 만들기 위해, `KClass<Boolean>`과 함께 `function()`을 사용할 수 있습니다.

```kotlin
function(Boolean::class, "myFunction", path(Book::isbn))
```

> 사용할 함수의 정보를 JPA 제공자에 등록할 필요가 있을 수 있습니다.
>
> 예를 들어 Hibernate를 사용하고 있다면 `FunctionContributor`를 반드시 등록해야 합니다.



## Custom predicate

커스텀 predicate를 만들기 위해, `customPredicate()`을 사용할 수 있습니다.

```kotlin
customPredicate("{0} MEMBER OF {1}", value(author), path(Book::authors))
```

만약 `customPredicate()`을 많이 사용한다면 [나만의 DSL](custom-dsl.md)을 만드는 것을 고려해보세요.

------
# Sorts

Kotlin JDSL은 JPQL의 order-by item을 표현하기 위해 `Sort` 인터페이스를 가지고 있습니다.
`Sort`를 만들기 위해, `asc()`와 `desc()`를 [`Expression`](expressions.md) 뒤에 붙여 사용할 수 있습니다.

```kotlin
path(Book::isbn).asc()

path(Book::isbn).desc()
```

## Null order

`Sort`의 `nullsFirst()`와 `nullsLast()`를 호출하는 것으로 null이 조회 결과의 어디에 나타나야 하는지 표현할 수 있습니다.

```kotlin
path(Employee::nickname).asc().nullsFirst()

path(Employee::nickname).asc().nullsLast()
```

------
# Subqueries

[select statement](statements.md#select-statement)에 `asEntity()`와 `asSubquery()`를 호출하는 것으로 subquery를 만들 수 있습니다.

## Derived entity

`asEntity()`를 통해 select statement는 [Entity](entities.md)로 사용될 수 있습니다.

```kotlin
data class DerivedEntity(
    val employeeId: Long,
    val count: Long,
)

val query = jpql {
    val subquery = select<DerivedEntity>(
        path(Employee::employeeId).`as`(expression("employeeId")),
        count(Employee::employeeId).`as`(expression("count")),
    ).from(
        entity(Employee::class),
        join(Employee::departments),
    ).groupBy(
        path(Employee::employeeId),
    ).having(
        count(Employee::employeeId).greaterThan(1L),
    )

    select(
        count(DerivedEntity::employeeId),
    ).from(
        subquery.asEntity(),
    )
}
```

## Subquery

`asSubquery()`를 통해 select statement는 [Expression](expressions.md)로 사용될 수 있습니다.

```kotlin
val query = jpql {
    val employeeIds = select<Long>(
        path(EmployeeDepartment::employee)(Employee::employeeId),
    ).from(
        entity(Department::class),
        join(EmployeeDepartment::class)
            .on(path(Department::departmentId).equal(path(EmployeeDepartment::departmentId))),
    ).where(
        path(Department::name).like("%03"),
    ).asSubquery()

    deleteFrom(
        Employee::class,
    ).where(
        path(Employee::employeeId).`in`(employeeIds),
    )
}
```

-------
# Custom DSL

## DSL

`Jpql` 클래스를 상속하고 나만의 함수를 추가하는 것으로 나만의 DSL을 만들 수 있습니다.

`Jpql`은 Kotlin JDSL이 제공하는 모든 기본 DSL 함수를 가지고 있습니다.
이를 이용해 나만의 함수를 만들 수 있습니다.
그리고 [`Expression`](expressions.md) 혹은 [`Predicate`](predicates.md)를 구현한 나만의 `Model` 클래스를 만들고, 이를 반환하는 함수를 만들 수 있습니다.
이 경우 [`JpqlSerializer`](custom-dsl.md#serializer)를 구현하여 `Model`을 String으로 랜더링하는 방법을 Kotlin JDSL에게 알려줄 수 있습니다.

> jpql()`이 DSL을 인식하기 위해서 `JpqlDsl.Constructor`를 companion object로 구현해야 합니다.

```kotlin
class MyJpql : Jpql() {
    companion object Constructor : JpqlDsl.Constructor<MyJpql> {
        override fun newInstance(): MyJpql = MyJpql()
    }

    fun myFunction(value: String): Expression<String> {
        return function(String::class, "myFunction", listOf(value(value)))
    }

    fun Expressionable<String>.regexLike(value: String): Predicate {
        return MyRegexLike(this.toExpression(), value)
    }
}

val query = jpql(MyJpql) {
    select(
        entity(Book::class)
    ).from(
        entity(Book::class)
    ).where(
        myFunction("test").regexLike(".*")
    )
}
```

### Serializer

나만의 `Model`을 String을 랜더링하기 위해 `JpqlSerializer`를 구현하고 이를 `RenderContext`에 추가해야 합니다.

> `JpqlSerializer`는 랜더링 로직을 구현할 수 있도록 `JpqlWriter`와 `RenderContext`를 제공합니다.`RenderContext`를 통해 `JpqlRenderSerializer`를 얻어 나의 `Model`이 가지고 있는 다른 `Model`을 랜더링할 수 있습니다.또 `RenderContext`를 통해 `JpqlRenderStatement`와 `JpqlRenderClause`를 얻어 현재 어떤 statement와 clause 안에서 랜더링하고 있는지 알 수 있습니다.
>
> 이것들을 이용해서 나만의 `Model`을 String으로 랜더링할 수 있습니다.

```kotlin
class MyRegexLikeSerializer : JpqlSerializer<MyRegexLike> {
    override fun handledType(): KClass<MyRegexLike> {
        return MyRegexLike::class
    }

    override fun serialize(part: MyRegexLike, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

//        val statement = context.getValue(JpqlRenderStatement)
//        val clause = context.getValue(JpqlRenderClause)
//
//        statement.isSelect()
//        clause.isWhere()

        writer.write("REGEXP_LIKE")
        writer.writeParentheses {
            delegate.serialize(part.expr, writer, context)
            writer.write(", ")
            delegate.serialize(part.pattern, writer, context)
        }
    }
}
```

`JpqlRenderContext`는 `JpqlSerializer` 구현체를 추가할 수 있도록 `registerModule()`를 제공합니다.

```kotlin
val myModule = object : JpqlRenderModule {
    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        context.addSerializer(MyRegexLikeSerializer())
    }
}

val myContext = JpqlRenderContext().registerModule(myModule)

val jpqQuery = entityManager.createQuery(query, myContext)
```

-------
# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL은 Spring Boot AutoConfigure를 지원합니다.
만약 프로젝트가 Spring Boot와 `com.linecorp.kotlin-jdsl:spring-data-jpa-support` dependency를 같이 포함하고 있다면, `JpqlRenderContext` bean이 `KotlinJdslAutoConfiguration`을 통해 자동 생성 됩니다.

만약 `JpqlSerializer` 또는 `JpqlIntrospector`를 bean으로 선언했다면, 자동으로 `JpqlRenderContext`에 해당 bean이 포함됩니다.

## Spring Data Repository

만약 사용하고 있는 `JpaRepository`가 `KotlinJdslJpqlExecutor`를 상속하면, Kotlin JDSL이 제공하는 확장 기능을 사용할 수 있습니다.

```kotlin
interface AuthorRepository : JpaRepository<Author, Long>, KotlinJdslJpqlExecutor
interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor

authorRepository.findAll {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class),
        join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
    ).groupBy(
        path(Author::authorId),
    ).orderBy(
        count(Author::authorId).desc(),
    )
}

bookRepository.findPage(pageable) {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    )
}
```

{% hint style="info" %}
만약 `KotlinJdslJpqlExecutor`를 `@DataJpaTest`에서 사용하고 싶다면 `KotlinJdslAutoConfiguration`를 테스트에서 직접 import 해야 합니다.
`@DataJpaTest`는 slice test이기 때문에 최소한의 bean만 생성합니다. 그리고 이 bean에는 `KotlinJdslAutoConfiguration`이 포함되어 있지 않습니다.
그래서 `@DataJpaTest`에서 Kotlin JDSL의 기능을 사용하고 싶다면 테스트에서 `KotlinJdslAutoConfiguration`를 직접 import 해야 합니다.
{% endhint %}

## Spring Batch

SpringBatch는 JPQL로 쿼리를 할 수 있도록 `JpaPagingItemReader`와 `JpaCursorItemReader`를 제공합니다.
Kotlin JDSL은 DSL로 생성된 JPQL 쿼리가 위 ItemReader들에서 실행될 수 있도록 `KotlinJdslQueryProvider`를 제공합니다.

```kotlin
@Auwoired
lateinit var queryProviderFactory: KotlinJdslQueryProviderFactory

val queryProvider = queryProviderFactory.create {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class),
    )
}

JpaCursorItemReaderBuilder<Isbn>()
    .entityManagerFactory(entityManagerFactory)
    .queryProvider(queryProvider)
    .saveState(false)
    .build()
```
