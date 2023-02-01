# Getting Started with backend development in Kotlin using Spring Boot & MongoDB

> This is an introduction article on how to build a Restful application in Kotlin using
> SpringBoot 3 and MongoDB.

## Introduction

I have been mobile developer for a while and using Kotlin to build Android apps. And over the
years after using Kotlin

So we are going build a basic application does a little more than a CRUD operation while
discussing the basic of Spring Boot 3 and MongoDB which we would be using as our database.

## Prerequisite

In this we would be mostly covering very basic stuff, so nothing much is needed as prerequisite.
But a familiarity with [Kotlin](https://kotlinlang.org/) as programming language, basic
understand of [Rest API](https://en.wikipedia.org/wiki/Representational_state_transfer) and [HTTP
method](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) would be helpful.

To aid the development activities, I would be
using [Jetbrains IntelliJ IDEA (Community Edition)](https://www.jetbrains.com/idea/download/).

## HelloWorld App !

Building a HelloWorld app in any programming language/technology, I believe, is quickest and
easiest way to get familiar it while covering the basic stuff like how to run, debug or deploy the
project.

Since we are using the community version of IDEA, we cannot create the `HelloWorld` project
directly from IDE itself using the New Project. But we can
use [spring initializr app](https://start.spring.io/) instead that allows us to create a Spring
project out of the
box.

Once you are on the website, we can update the default selected parameters for the project like
name of the project, language, version of Spring Boot, etc to something similar as shown below.

![Spring Initialier](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_initializr_dbbfd7e79a.png)

And since, we want to create REST API with MongoDB as database lets add the dependency for them
using Add Dependency button on the right.

![Spring dependency](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_Depdendency_bb8866de1c.png)

So project setup looks like this after all the updates.

![Spring project](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Spring_With_Dependency_4137b2515f.png)

Now we can download the project folder using generate button and then open it in using IDE. If
we scan the project folder will only find one class ie `HelloBackendWorldApplication.kt`
which has the `main` function as well.

![Sample Project](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Default_App_c843cf2216.png)

Next step would be print HelloWorld on the screen and since we are building a restful
application therefore we would create `GET` request API. So let add function which would act as
`GET` API call.

```kotlin
@GetMapping("/hello")
fun hello(@RequestParam(value = "name", defaultValue = "World") name: String?): String {
    return String.format("Hello %s!", name)
}
```

And we need to add an annotation of `@RestController` to our `class` to make it restful client.

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

Now lets run our project using run icon from the toolbar.

![Run icon image](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Run_icon_image_bdb6c52ca8.png)

Now load https://localhost:8080/hello on the browser once build is complete and that will print
Hello World on your screen.

![Hello World Output](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Hello_World_output_279aed0e43.png)

And on cross validating this from [Postman](https://www.postman.com) we can clearly understand
that our `Get` API is working perfectly.  
![Hello World Output](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Postman_output_216cb86538.png)

Now let's understand the basic of Spring Boot which made it so easy to create our first API call.

## What is Spring Boot ?

[As per official docs](https://spring.io/projects/spring-boot#overview) - _Spring Boot makes it
easy to create stand-alone, production-grade Spring based Applications that you can "just run"._
That implies that it's a tool built on the top of Spring framework allowing us to build web
application quickly.

In order to aid our development using Spring Boot , we add few annotation in our code like

1. `@SpringBootApplication`
   This annotation is marked at class level, declare to code reader(developer) and Spring that
   it's a Spring Boot project and allows enabling feature which can also done
   using `@EnableAutoConfiguration`,`@ComponentScan` & `@Configuration`.

2. `@RequestMapping` & `@RestController`: These annotation provides the routing information.
   Routing are nothing but a mapping of `Http` request path (text after `host/`) to classes that has
   implementation of these across various `Http` method.

And these annotations are sufficient for building a basic application. Using Spring Boot
now we create Restful web service with all business logic, but we don't have data container
which can store or provide data to run these operations.

## Introduction to MongoDB

For our app, we would be using MongoDB as our database which is a document database. It's an
open source, cross-platform and distributed document database which allows to build apps with
flexible schema. This is great as we can focus on building app rather than defining
the schema.

We can get started with MongoDB really quickly
using [MongoDB Atlas](https://www.mongodb.com/atlas/database)
which is database on cloud as a service and has a free forever tier.

I would recommend you to
explore, [MongoDB Jumpstart series](https://www.youtube.com/watch?v=RGfFpQF0NpE&list=PL4RCxklHWZ9v2lcat4oEVGQhZg6r4IQGV)
to get familiar with MongoDB and its various services just under 10 minutes.

## Connecting with Spring Boot App & MongoDB

With the basics for MongoDB covered, now let's connect our SpringBoot project with it. Connect
with MongoDB is really simple thanks for Spring Data MongoDB plugin.

To connect with MongoDB Atlas, we just database url against `spring.data.mongodb.uri`
property in `application.properties` file and connection string can be found as shown below.

![DB URL](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/find_Connect_Stream_URL_e7a6c2257a.gif)

And format for connection string is

```shell
        spring.data.mongodb.uri = mongodb + srv ://<username>:<pwd>@<cluster>.mongodb.net/<dbname>
```

![Connection URL](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Connect_Mongo_DB_Atlas_19305672d4.png)

## Creating CRUD Restful app

With all the basic covered now lets build a more complex application than HelloWorld!. In this
app, we would be covering all CRUD operation and tweeting them along the way to make it more
realistic app,so let's create a new project similar to HelloWorld app we created earlier. And for
this app, we would be one of the sample dataset provided by MongoDB, one of my favourite
features that enables quick learning.

You can load sample dataset on Atlas as shown below
![sample_db](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/load_sample_dataset_95ce8126e0.png)

We would be using `sample_restaurants` collection for our CRUD application. Before, we start
with the actual CRUD operation lets create the restaurant model class equivalent to in the
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

You would have noticed there is nothing fancy about these class except for the annotation. These
annotation help us to connect or co-relate with Document in the Atlas well like

`@Document` declares that this data class represent a document in the Atlas.

`@Field` is used define alias name for a property in the document like `coord` for coordinate in
`Address` model.

Now let's create a repository class where we can define all methods through which we can access
data and `Spring Boot` has interface `MongoRepository` which help us with it.

```kotlin
interface Repo : MongoRepository<Restaurant, String> {

    fun findByRestaurantId(id: String): Restaurant?
}
```

After that, we create a controller through which we can call these queries. Since this is bigger
project unlike HelloWorld app, we would be creating a separate controller.

```kotlin
@RestController
@RequestMapping("/restaurants")
class Controller(@Autowired val repo: Repo) {

}
``` 

### Read Operation

Now our project is ready to do some action, so lets first read the number of restaurants in our
collection and for this we would be using `GetMapping`.

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

And we get restaurant based `id` as shown below

```kotlin
@GetMapping("/{id}")
fun getRestaurantById(@PathVariable("id") id: String): Restaurant? {
    return repo.findByRestaurantId(id)
}
```

![ready-id](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Read_By_Id_d1752a108b.png)

Let's also validate this against invalidate restaurant id as well.

![ready-null](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Read_Null_637f97f167.png)

As expected haven't got any results but the API response code is still 200, which is incorrect
so lets fix this up. In order to have the correct response code, we will have to check the
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
earlier or can use inbuilt method `insert` provided by `MongoRepository` and since we would be
adding a new object to the collection we would be using `@PostMapping` for this.

```kotlin
    @PostMapping
fun postRestaurant(): Restaurant {
    val restaurant = Restaurant().copy(name = "sample", restaurantId = "33332")
    return repo.insert(restaurant)
}
```

### Update Operation

Spring doesn't have any specific in-built update similar to other CRUD operation, so we would be
using the read and write operation in combination to perform update function.

```kotlin
    @PatchMapping("/{id}")
fun updateRestaurant(@PathVariable("id") id: String): Restaurant? {
    return repo.findByRestaurantId(restaurantId = id)?.let {
        repo.save(it.copy(name = "Update"))
    }
}
```

This is not an ideal way of updating item in the collection as it requires two operation and
improved further if we would have using MongoDB native driver which allows us to perform
complicate operation in minimum steps.

### Delete Operation

Deleting restaurant is also similar, we can use the `MongoRepository` delete function the item
from the collection which take item as an input.

```kotlin
    @DeleteMapping("/{id}")
fun deleteRestaurant(@PathVariable("id") id: String) {
    repo.findByRestaurantId(id)?.let {
        repo.delete(it)
    }
}
```

## Summary

Thank you for reading and hopefully find this article informative! The Complete source code of the
app can be found on GitHub.

If you have any queries or comments, you can share them on the MongoDB Realm forum or tweet me
@codeWithMohit.





