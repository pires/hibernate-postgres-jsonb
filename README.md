hibernate-postgres-jsonb
======================

Hibernate 4.3.x (JPA 2.1) + PostgreSQL 9.4 JSONB support example.

What I've implemented:
* Hibernate dialect with JSONB support
* Hibernate user-type for JSONB support (with de/serialization from/to JSON, powered by Jackson ```ObjectMapper```)
* Static-metamodel & SQL generation
* Simple DAO with native JSONB query

# Pre-requisites

* JDK 7
* Maven 3.1.0 or newer

# Build and test

## PostgreSQL 9.4

Install, configure and start PostgreSQL 9.4 on ```localhost```, with username ```postgres``` and password ```123qwe```. If you change this, you must update ```src/test/resources/META-INF/persistence.xml``` accordingly.

```
sudo su postgres
createdb dbtest
```

## Test

```
mvn clean test
```

You should see something like this:
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running TestSuite
Configuring TestNG with: TestNG652Configurator
15:06:14,354  INFO LogHelper:46 - HHH000204: Processing PersistenceUnitInfo [
	name: test
	...]
15:06:14,416  INFO Version:54 - HHH000412: Hibernate Core {4.3.6.Final}
(...)
15:06:14,585  INFO Version:66 - HCANN000001: Hibernate Commons Annotations {4.0.5.Final}
15:06:14,607  WARN DriverManagerConnectionProviderImpl:93 - HHH000402: Using Hibernate built-in connection pool (not for production use!)
15:06:14,616  INFO DriverManagerConnectionProviderImpl:166 - HHH000401: using driver [org.postgresql.Driver] at URL [jdbc:postgresql://localhost:5432/dbtest]
15:06:14,616  INFO DriverManagerConnectionProviderImpl:175 - HHH000046: Connection properties: {user=postgres, password=****}
15:06:14,616  INFO DriverManagerConnectionProviderImpl:180 - HHH000006: Autocommit mode: false
15:06:14,617  INFO DriverManagerConnectionProviderImpl:102 - HHH000115: Hibernate connection pool size: 20 (min=1)
15:06:14,849  INFO Dialect:145 - HHH000400: Using dialect: com.github.pires.example.hibernate.dialect.JSONBPostgreSQLDialect
15:06:14,869  INFO LobCreatorBuilder:123 - HHH000424: Disabling contextual LOB creation as createClob() method threw error : java.lang.reflect.InvocationTargetException
15:06:15,055  INFO ASTQueryTranslatorFactory:47 - HHH000397: Using ASTQueryTranslatorFactory
15:06:15,073 TRACE TypeFactory:72 - Scoping types to session factory org.hibernate.internal.SessionFactoryImpl@73f808ef
15:06:15,231  INFO SchemaExport:343 - HHH000227: Running hbm2ddl schema export
Hibernate: 
    drop table if exists JSONB_ENTITIES cascade
Hibernate: 
    create table JSONB_ENTITIES (
        id uuid not null,
        documents jsonb,
        version int8,
        primary key (id)
    )
15:06:15,456  INFO SchemaExport:405 - HHH000230: Schema export complete
Hibernate: 
    insert 
    into
        JSONB_ENTITIES
        (documents, version, id) 
    values
        (?, ?, ?)
15:06:15,591 TRACE BasicBinder:81 - binding parameter [2] as [BIGINT] - [0]
15:06:15,592 TRACE BasicBinder:81 - binding parameter [3] as [OTHER] - [3bc2ac86-cd91-4795-b0b1-b9cdf3b5c2a2]
Hibernate: 
    insert 
    into
        JSONB_ENTITIES
        (documents, version, id) 
    values
        (?, ?, ?)
15:06:15,598 TRACE BasicBinder:81 - binding parameter [2] as [BIGINT] - [0]
15:06:15,599 TRACE BasicBinder:81 - binding parameter [3] as [OTHER] - [1d0d0fb0-e127-4812-90aa-4f27084a6580]
Hibernate: 
    select
        count(jsonbentit0_.id) as col_0_0_ 
    from
        JSONB_ENTITIES jsonbentit0_
15:06:15,734 TRACE BasicExtractor:78 - extracted value ([col_0_0_] : [BIGINT]) - [2]
Hibernate: 
    insert 
    into
        JSONB_ENTITIES
        (documents, version, id) 
    values
        (?, ?, ?)
15:06:15,736 TRACE BasicBinder:81 - binding parameter [2] as [BIGINT] - [0]
15:06:15,736 TRACE BasicBinder:81 - binding parameter [3] as [OTHER] - [c2841567-729a-47b1-badc-65d60650b78d]
Hibernate: 
    select
        count(jsonbentit0_.id) as col_0_0_ 
    from
        JSONB_ENTITIES jsonbentit0_
15:06:15,739 TRACE BasicExtractor:78 - extracted value ([col_0_0_] : [BIGINT]) - [3]
Hibernate: 
    select
        * 
    from
        "public".jsonb_entities,
        lateral jsonb_array_elements(documents) document_entry 
    where
        CAST(document_entry ->> 'created' AS bigint) BETWEEN ? AND ?;
15:06:15,755 TRACE BasicBinder:81 - binding parameter [1] as [BIGINT] - [1413209174141]
15:06:15,756 TRACE BasicBinder:81 - binding parameter [2] as [BIGINT] - [1413209184141]
15:06:15,758 TRACE BasicExtractor:78 - extracted value ([id] : [OTHER]) - [3bc2ac86-cd91-4795-b0b1-b9cdf3b5c2a2]
15:06:15,758 TRACE BasicExtractor:78 - extracted value ([id] : [OTHER]) - [1d0d0fb0-e127-4812-90aa-4f27084a6580]
15:06:15,759 TRACE BasicExtractor:78 - extracted value ([id] : [OTHER]) - [1d0d0fb0-e127-4812-90aa-4f27084a6580]
15:06:15,776  INFO DriverManagerConnectionProviderImpl:281 - HHH000030: Cleaning up connection pool [jdbc:postgresql://localhost:5432/dbtest]
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.829 sec - in TestSuite

Results :

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0

```

# Generate drop-create SQL

```
mvn clean package -Pgenerate-sql
```

See ```sql/drop-create.sql```
