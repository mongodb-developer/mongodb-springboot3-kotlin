# Getting Started with backend development in Kotlin using Spring Boot 3 & MongoDB

> This is an introduction article on how to build a RESTful application in Kotlin using
> [Spring Boot 3](https://spring.io/) and [MongoDB Atlas](https://www.mongodb.com/atlas/database).

## Introduction

Today, we are going to build a basic RESTful application that does a little more than a CRUD
operation, and for that, we will use:

* `Spring Boot 3`, which is one of the popular frameworks based on Spring, allowing developers to
  build production grades quickly.
* `MongoDB`, which is a document oriented database, allowing developers to focus on building apps
  rather than on [database schema](https://en.wikipedia.org/wiki/Database_schema).

## Prerequisites

This is a getting-started article, so nothing much is needed as a prerequisite. But familiarity
with [Kotlin](https://kotlinlang.org/) as a programming language, plus a basic understanding
of [Rest API](https://en.wikipedia.org/wiki/Representational_state_transfer)
and [HTTP methods](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol), would be helpful.

To help with development activities, we will be
using [Jetbrains IntelliJ IDEA (Community Edition)](https://www.jetbrains.com/idea/download/).

## HelloWorld app!

Building a HelloWorld app in any programming language/technology, I believe, is the quickest and
easiest way to get familiar with it. This helps you cover the basic concepts, like how to build,
run, debug, deploy, etc.

Since we are using the community version of IDEA, we cannot create the `HelloWorld` project directly
from IDE itself using the New Project. But we can use
the [Spring initializer app](https://start.spring.io/) instead, which allows us to create a Spring
project out of the box.

Once you are on the website, you can update the default selected parameters for the project, like
the name of the project, language, version of `Spring Boot`, etc., to something similar as shown
below.

![Spring initializer](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_initializr_dbbfd7e79a.png)

And since we want to create REST API with MongoDB as a database, let's add the dependency using the
Add Dependency button on the right.

![Spring dependency](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_Depdendency_bb8866de1c.png)

After all the updates, our project settings will look like this.

![Spring project](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_With_Dependency_4137b2515f.png)

Now we can download the project folder using the generate button and open it using the IDE. If we
scan the project folder, we will only find one class — i.e., `HelloBackendWorldApplication.kt`,
which has the `main` function, as well.

![Sample Project](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Default_App_c843cf2216.png)

The next step is to print HelloWorld on the screen. Since we are building a restful
application, we will create a `GET` request API. So, let's add a function to act as a `GET` API
call.

```kotlin
@GetMapping("/hello")
fun hello(@RequestParam(value = "name", defaultValue = "World") name: String?): String {
    return String.format("Hello %s!", name)
}
```

We also need to add an annotation of `@RestController` to our `class` to make it a `Restful` client.

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

Now load https://localhost:8080/hello on the browser once the build is complete, and that will print
Hello World on your screen.

![Hello World Output](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Hello_World_output_279aed0e43.png)

And on cross-validating this from [Postman](https://www.postman.com), we can clearly understand that
our `Get` API is working perfectly.  
![Hello World Output](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Postman_output_216cb86538.png)

It's time to understand the basics of `Spring Boot` that made it so easy to create our first API
call.

## What is Spring Boot ?

> [As per official docs](https://spring.io/projects/spring-boot#overview), Spring Boot makes it easy
> to create stand-alone, production-grade, Spring-based applications that you can "just run."

This implies that it's a tool built on top of the Spring framework, allowing us to build web
applications quickly.

`Spring Boot` uses annotations, which do the heavy lifting in the background. A few of them, we have
used already, like:

1. `@SpringBootApplication`: This annotation is marked at class level, and declares to the code
   reader (developer) and Spring that it's a Spring Boot project. It allows an enabling feature,
   which can also be done using `@EnableAutoConfiguration`,`@ComponentScan`, and `@Configuration`.

2. `@RequestMapping` and `@RestController`: This annotation provides the routing information.
   Routing is nothing but a mapping of a `HTTP` request path (text after `host/`) to classes that
   have the implementation of these across various `HTTP` methods.

These annotations are sufficient for building a basic application. Using Spring Boot, we will create
a RESTful web service with all business logic, but we don't have a data container that can store or
provide data to run these operations.

## Introduction to MongoDB

For our app, we will be using MongoDB as the database. MongoDB is an open-source, cross-platform,
and distributed document database, which allows building apps with flexible schema. This is great as
we can focus on building the app rather than defining the schema.

We can get started with MongoDB really quickly
using [MongoDB Atlas](https://www.mongodb.com/atlas/database), which is a database as a service in
the cloud and has a free forever tier.

I recommend that you explore
the [MongoDB Jumpstart series](https://www.youtube.com/watch?v=RGfFpQF0NpE&list=PL4RCxklHWZ9v2lcat4oEVGQhZg6r4IQGV)
to get familiar with MongoDB and its various services in under 10 minutes.

## Connecting with the Spring Boot app and MongoDB

With the basics of MongoDB covered, now let's connect our Spring Boot project to it. Connecting with
MongoDB is really simple, thanks to the Spring Data MongoDB plugin.

To connect with MongoDB Atlas, we just need
a [database URL](https://www.mongodb.com/docs/guides/atlas/connection-string/) that can be added
as a `spring.data.mongodb.uri` property in `application.properties` file. The connection string can
be found as shown below.

![DB URL](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/find_Connect_Stream_URL_e7a6c2257a.gif)

The format for the connection string is:

```shell
spring.data.mongodb.uri = mongodb + srv ://<username>:<pwd>@<cluster>.mongodb.net/<dbname>
```

![Connection URL](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Connect_Mongo_DB_Atlas_19305672d4.png)

## Creating a CRUD RESTful app

With all the basics covered, now let's build a more complex application than HelloWorld! In this
app, we will be covering all CRUD operations and tweaking them along the way to make it a more
realistic app. So, let's create a new project similar to the HelloWorld app we created earlier. And
for this app, we will use one of the sample datasets provided by MongoDB — one of my favourite
features that enables quick learning.

You can load
a [sample dataset](https://www.mongodb.com/developer/products/atlas/atlas-sample-datasets/) on Atlas
as shown below:

![sample_db](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/load_sample_dataset_95ce8126e0.png)

We will be using the `sample_restaurants` collection for our CRUD application. Before we start with
the actual CRUD operation, let's create the restaurant model class equivalent to it in the
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

You will notice there is nothing fancy about this class except for the annotation. These annotations
help us to connect or co-relate classes with databases like:

* `@Document`: This declares that this data class represents a document in Atlas.
* `@Field`: This is used to define an alias name for a property in the document, like `coord` for
  coordinate in `Address` model.

Now let's create a repository class where we can define all methods through which we can access
data. `Spring Boot` has interface `MongoRepository`, which helps us with this.

```kotlin
interface Repo : MongoRepository<Restaurant, String> {

    fun findByRestaurantId(id: String): Restaurant?
}
```

After that, we create a controller through which we can call these queries. Since this is a bigger
project, unlike the HelloWorld app, we will create a separate controller where the `MongoRepository`
instance is passed using `@Autowired`, which provides annotations-driven dependency injection.

```kotlin
@RestController
@RequestMapping("/restaurants")
class Controller(@Autowired val repo: Repo) {

}
``` 

### Read operation

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

Taking a step further to read the restaurant-based `restaurantId`. We will have to add a method in
our repo as `restaurantId` is not marked `@Id` in the restaurant class.

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

And again, we will be using [Postman](https://www.postman.com/) to validate the output against a
random `restaurantId` from the sample dataset.

![ready-id](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Read_By_Id_d1752a108b.png)

Let's also validate this against a non-existing `restaurantId`.

![ready-null](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Read_Null_637f97f167.png)

As expected, we haven't gotten any results, but the API response code is still 200, which is
incorrect! So, let's fix this.

In order to have the correct response code, we will have to check the result before sending it back
with the correct response code.

```kotlin
    @GetMapping("/{id}")
fun getRestaurantById(@PathVariable("id") id: String): ResponseEntity<Restaurant> {
    val restaurant = repo.findByRestaurantId(id)
    return if (restaurant != null) ResponseEntity.ok(restaurant) else ResponseEntity
        .notFound().build()
}
```

![ready-404](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Notfound_404_522b7f60ac.png)

### Write operation

To add a new object to the collection, we can add a `write` function in the `repo` we created
earlier, or we can use the inbuilt method `insert` provided by `MongoRepository`. Since we will be
adding a new object to the collection, we'll be using `@PostMapping` for this.

```kotlin
    @PostMapping
fun postRestaurant(): Restaurant {
    val restaurant = Restaurant().copy(name = "sample", restaurantId = "33332")
    return repo.insert(restaurant)
}
```

### Update operation

Spring doesn't have any specific in-built update similar to other CRUD operations, so we will be
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
be improved further if we use the [MongoDB native driver](https://www.mongodb.com/docs/drivers/),
which allows us to perform complicated operations with the minimum number of steps.

### Delete operation

Deleting a restaurant is also similar. We can use the `MongoRepository` delete function of the item
from the collection, which takes the item as input.

```kotlin
    @DeleteMapping("/{id}")
fun deleteRestaurant(@PathVariable("id") id: String) {
    repo.findByRestaurantId(id)?.let {
        repo.delete(it)
    }
}
```

## Summary

Thank you for reading and hopefully you find this article informative! The complete source code of
the app can be found on [GitHub](https://github.com/mongodb-developer/mongodb-springboot3-kotlin).

If you have any queries or comments, you can share them on
the [MongoDB forum](https://www.mongodb.com/community/forums/) or tweet
me [@codeWithMohit](https://twitter.com/codeWithMohit).