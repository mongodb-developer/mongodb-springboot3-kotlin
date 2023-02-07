package com.mongodb.starter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/restaurants")
class Controller(@Autowired val repo: Repo) {


    @GetMapping
    fun getCount(): Int {
        return repo.findAll().count()
    }

    @GetMapping("/{id}")
    fun getRestaurantById(@PathVariable("id") id: String): ResponseEntity<Restaurant> {
        val restaurant = repo.findByRestaurantId(id)
        return if (restaurant != null) ResponseEntity.ok(restaurant) else ResponseEntity
            .notFound().build()
    }

    @PostMapping
    fun postRestaurant(): Restaurant {
        val restaurant = Restaurant().copy(name = "sample", restaurantId = "33332")
        return repo.insert(restaurant)
    }


    @PostMapping("/addByParams")
    fun postRestaurantAsParams(@RequestBody restaurant: Restaurant): Restaurant {
        return repo.insert(restaurant)
    }

    @DeleteMapping("/{id}")
    fun deleteRestaurant(@PathVariable("id") id: String) {
        repo.findByRestaurantId(id)?.let {
            repo.delete(it)
        }
    }

    @PatchMapping("/{id}")
    fun updateRestaurant(@PathVariable("id") id: String): Restaurant? {
        return repo.findByRestaurantId(restaurantId = id)?.let {
            repo.save(it.copy(name = "Update"))
        }
    }
}
