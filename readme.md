# Getting Started with backend development in Kotlin using Spring Boot 3 & MongoDB

> This is an introduction article on how to build a RESTful application in Kotlin using
> [SpringBoot 3](https://spring.io/) and [MongoDB Atlas](https://www.mongodb.com/atlas/database).

## Introduction

I have been a mobile developer for a while and used Kotlin for building apps. But shortly after a
brief experience, loved the ease of use and clarity in the language paradigm that gave me
the confidence to explore more and build more complex systems like backend applications that can
support Mobile apps.

So today, we are going to build a basic RESTful application that does a little more than a CRUD
operation and for that we will use

* `Spring Boot 3` which is one of the popular framework based on Spring, allowing developers to
  build production grades quickly.
* `MongoDB` which is a document oriented database, allowing developers to focus building app
  rather than [database schema](https://en.wikipedia.org/wiki/Database_schema).

## Prerequisite

This is a getting started article, so nothing much is needed as a prerequisite.
But familiarity with [Kotlin](https://kotlinlang.org/) as a programming language, basic
understanding of [Rest API](https://en.wikipedia.org/wiki/Representational_state_transfer) and [HTTP
methods](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) would be helpful.

To help with development activities, we will be
using [Jetbrains IntelliJ IDEA (Community Edition)](https://www.jetbrains.com/idea/download/).

## HelloWorld App!

Building a HelloWorld app in any programming language/technology, I believe, is the quickest and
easiest way to get familiar with it which helps you cover the basic concepts like how to
build, run, debug or deploy, etc.

Since we are using the community version of IDEA, we cannot create the `HelloWorld` project
directly from IDE itself using the New Project. But we can use
the [Spring initializer app](https://start.spring.io/) instead which allows us to create a Spring
project out of the box.

Once you are on the website, we can update the default selected parameters for the project like
the name of the project, language, version of `Spring Boot`, etc to something similar as shown
below.

![Spring initializer](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_initializr_dbbfd7e79a.png)

And to create REST API(s) using MongoDB as a database let's add the `Spring Data MongoDB` dependency
using Add Dependency button on the right.

![Spring dependency](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_Depdendency_bb8866de1c.png)

After all the updates our project settings would look like this.

![Spring project](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_With_Dependency_4137b2515f.png)

Now we can download the project folder using generate button and then open using the IDE. If
we scan the project folder we will only find one class ie `HelloBackendWorldApplication.kt`
which has the `main` function as well.

![Sample Project](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Default_App_c843cf2216.png)

The Next step would be to print HelloWorld on the screen and since we are building a restful
application therefore we would create a `GET` request API. So let's add a function which would
act as a `GET` API call.

```kotlin
@GetMapping("/hello")
fun hello(@RequestParam(value = "name", defaultValue = "World") name: String?): String {
    return String.format("Hello %s!", name)
}
```

And we need to add an annotation of `@RestController` to our `class` to make it a `Restful` client.

```kotlin
@SpringBootApplication
@RestController
class HelloBackendWorldApplication {
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello World!"
    }
}

fun main(args: Array<String>) {
    runApplication<HelloBackendWorldApplication>(*args)
}
```

Now, let's run our project using the run icon from the toolbar.

![Run icon image](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Run_icon_image_bdb6c52ca8.png)

Now load https://localhost:8080/hello on the browser once the build is complete and that will print
Hello World on your screen.

![Hello World Output](http://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Hello_World_output_279aed0e43.png)

And on cross-validating this from [Postman](https://www.postman.com) we can clearly understand
that our `Get` API is working perfectly.  
![Hello World Output](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Postman_output_216cb86538.png)

It's time to understand the basics of `Spring Boot` that made it so easy to create our first
API call.

## What is `Spring Boot`?

> [As per official docs](https://spring.io/projects/spring-boot#overview) - Spring Boot makes it
> easy to create stand-alone, production-grade Spring based Applications that you can "just run".

This implies that it's a tool built on the top of Spring framework allowing us to build web
applications quickly.

`Spring Boot` uses annotations which do the heavy lifting in the background and a few of them we
have used already like

1. `@SpringBootApplication`
   This annotation is marked at class level, declare to the code reader(developer) and Spring that
   it's a Spring Boot project and allows enabling features which can also be done using
   `@EnableAutoConfiguration`,`@ComponentScan` & `@Configuration`.

2. `@RequestMapping` & `@RestController`: These annotation provides the routing information.
   Routing is nothing but a mapping of `HTTP` request path (text after `host/`) to classes that
   have the implementation of these across various `HTTP` method.

And these annotations are sufficient for building a basic application. Using Spring Boot
now we will create RESTful web service with all business logic, but we don't have a data container
which can store or provide data to run these operations.

## Introduction to MongoDB

For our app, we will be using MongoDB as a database which is a document database. It's an
open-source, cross-platform and distributed document database which allows building apps with
flexible schema. This is great as we can focus on building the app rather than defining
the schema.

We can get started with MongoDB really quickly
using [MongoDB Atlas](https://www.mongodb.com/atlas/database)
which is a database on the cloud as a service and has a free forever tier.

I would recommend you to explore, the
[MongoDB Jumpstart series](https://www.youtube.com/watch?v=RGfFpQF0NpE&list=PL4RCxklHWZ9v2lcat4oEVGQhZg6r4IQGV)
to get familiar with MongoDB and its various services in under 10 minutes.

## Connecting with Spring Boot App & MongoDB

With the basics for MongoDB covered, now let's connect our SpringBoot project with it. Connect
with MongoDB is really simple thanks to the Spring Data MongoDB plugin.

To connect with MongoDB Atlas, we just need
a [database URL](https://www.mongodb.com/docs/guides/atlas/connection-string/) that can be added
as `spring.data.mongodb.uri` property in `application.properties` file and connection string can be
found as shown below.

![DB URL](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/find_Connect_Stream_URL_e7a6c2257a.gif)

And format for the connection string is

```shell
spring.data.mongodb.uri = mongodb + srv ://<username>:<pwd>@<cluster>.mongodb.net/<dbname>
```

![Connection URL](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Connect_Mongo_DB_Atlas_19305672d4.png)

## Creating a CRUD RESTful app

With all the basics covered now let's build a more complex application than HelloWorld! In this app,
we will be covering all CRUD operations and tweaking them along the way to make it a more realistic
app. So let's create a new project similar to the HelloWorld app we created earlier. And for this
app, we would be one of the sample datasets provided by MongoDB, one of my favourite features that
enable quick learning.

You can load
a [sample dataset](https://www.mongodb.com/developer/products/atlas/atlas-sample-datasets/) on Atlas
as shown below

![sample_db](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/load_sample_dataset_95ce8126e0.png)

We would be using `sample_restaurants` collection for our CRUD application. Before, we start
with the actual CRUD operation let's create the restaurant model class equivalent to in the
collection.

![model](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/model_from_compass_20b916a09a.png)

```kotlin

@Document("restaurants")
data class Restaurant(
    @Id
    val id: ObjectId = ObjectId(),
    val address: Address = Address(),
    val borough: String = "",
    val cuisine: String = "",
    val grades: List<Grade> = emptyList(),
    val name: String = "",
    @Field("restaurant_id")
    val restaurantId: String = ""
)

data class Address(
    val building: String = "",
    val street: String = "",
    val zipcode: String = "",
    @Field("coord")
    val coordinate: List<Double> = emptyList()
)

data class Grade(
    val date: Date = Date(),
    @Field("grade")
    val rating: String = "",
    val score: Int = 0
)
```

You would have noticed there is nothing fancy about this class except for the annotation. These
annotations help us to connect or co-relate classes with databases like

* `@Document` declares that this data class represent a document in the Atlas.

* `@Field` is used to define an alias name for a property in the document like `coord` for
  coordinate in `Address` model.

Now let's create a repository class where we can define all methods through which we can access
data and `Spring Boot` has interface `MongoRepository` which helps us with it.

```kotlin
interface Repo : MongoRepository<Restaurant, String> {

    fun findByRestaurantId(id: String): Restaurant?
}
```

After that, we create a controller through which we can call these queries. Since this is a bigger
project unlike the HelloWorld app, we would be creating a separate controller where
`MongoRepository` instance is passed using `@Autowired` which provides annotations-driven dependency
injection.

```kotlin
@RestController
@RequestMapping("/restaurants")
class Controller(@Autowired val repo: Repo) {

}
``` 

### Read Operation

Now our project is ready to do some action, so let's count the number of restaurants in the
collection using `GetMapping`.

```kotlin
@RestController
@RequestMapping("/restaurants")
class Controller(@Autowired val repo: Repo) {

    @GetMapping
    fun getCount(): Int {
        return repo.findAll().count()
    }
}
```

![model](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Read_72aa6d89de.png)

Taking a step further to read restaurant based `restaurantId`, we will have to add a method in
our Repo as `restaurantId` is not marked `@Id` in the restaurant class.

```kotlin
interface Repo : MongoRepository<Restaurant, String> {
    fun findByRestaurantId(restaurantId: String): Restaurant?
}
``` 

```kotlin
@GetMapping("/{id}")
fun getRestaurantById(@PathVariable("id") id: String): Restaurant? {
    return repo.findByRestaurantId(id)
}
```

And again, we would be using [Postman](https://www.postman.com/) to validate the output

![ready-id](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Read_By_Id_d1752a108b.png)

Let's also validate this against a non-valid `restaurantId` as well.

![ready-null](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Read_Null_637f97f167.png)

As expected haven't got any results but the API response code is still 200, which is incorrect
so let's fix this.

In order to have the correct response code, we will have to check the
result before sending it back with the correct response code.

```kotlin
@GetMapping("/{id}")
fun getRestaurantById(@PathVariable("id") id: String): ResponseEntity<Restaurant> {
    val restaurant = repo.findByRestaurantId(id)
    return if (restaurant != null) ResponseEntity.ok(restaurant) else ResponseEntity
        .notFound().build()
}
```

![ready-404](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Notfound_404_522b7f60ac.png)

### Write Operation

To add a new object to the collection, we can add `write` function in the `repo` we created
earlier or can use the inbuilt method `insert` provided by `MongoRepository` and since we would be
adding a new object to the collection we would be using `@PostMapping` for this.

```kotlin
@PostMapping
fun postRestaurant(): Restaurant {
    val restaurant = Restaurant().copy(name = "sample", restaurantId = "33332")
    return repo.insert(restaurant)
}
```

But in a real world application with `POST` request we normally pass request parameter as an
input, in this case it would be `Restaurant` object with `restaurantId = 33332`. So lets update
the code.

```kotlin
@PostMapping("/addByParams")
fun postRestaurantAsParams(@RequestBody restaurant: Restaurant): Restaurant {
    return repo.insert(restaurant)
}

```

![postman-output-post-request-with-params](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_2023_02_07_at_21_31_40_8335baf895.png)

### Update Operation

Spring doesn't have any specific in-built update similar to other CRUD operations, so we would be
using the read and write operation in combination to perform the update function.

```kotlin
@PatchMapping("/{id}")
fun updateRestaurant(@PathVariable("id") id: String): Restaurant? {
    return repo.findByRestaurantId(restaurantId = id)?.let {
        repo.save(it.copy(name = "Update"))
    }
}
```

This is not an ideal way of updating items in the collection as it requires two operations and can
be improved further if we use the [MongoDB native driver](https://www.mongodb.com/docs/drivers/)
which allows us to perform complicated operations in minimum steps.

### Delete Operation

Deleting a restaurant is also similar, we can use the `MongoRepository` delete function of the item
from the collection which takes the item as input.

```kotlin
@DeleteMapping("/{id}")
fun deleteRestaurant(@PathVariable("id") id: String) {
    repo.findByRestaurantId(id)?.let {
        repo.delete(it)
    }
}
```

## Summary

Thank you for reading ,and hopefully you find this article informative! The Complete source code
of the app can be found
on [GitHub](https://github.com/mongodb-developer/mongodb-springboot3-kotlin).

If you have any queries or comments, you can share them on
the [MongoDB forum](https://www.mongodb.com/community/forums/) or tweet me
[@codeWithMohit](https://twitter.com/codeWithMohit).
