package com.mongodb.starter

import org.springframework.data.mongodb.repository.MongoRepository

interface Repo : MongoRepository<Restaurant, String> {

    fun findByRestaurantId(restaurantId: String): Restaurant?
}