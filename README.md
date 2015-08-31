webuml-importer-git
===================

The WebUml Git SCM Import Modul.

Minimum preconditions:
---------------------

- Java 8
- Maven 3.0.5
- Mongo 2.4+

Run with Maven & Command Line
-----------------------------

! Start your local MongoDB first !

```
mvn package
java -jar target/webuml-importer-git.jar --spring.profiles.active=local
curl -s localhost:8081 | jq  .
```

Run in IntelliJ IDEA
--------------------

1. Menu | Run | Edit Configurations...
2. \+ | "Application"
3. Name: WebUml
4. Main-Class: com.webuml.importer.Application
5. Program-Arguments: --spring.profiles.active=local
6. OK

Press |> in toolbar.


Configruation
----------------

| Name | File
|------|-------------------------------------------|
| App                     | http://webuml-importer-git.example.org |
| Config                  | /Procfile |
| Application Properties  | src/main/resources/application-heroku.properties |
