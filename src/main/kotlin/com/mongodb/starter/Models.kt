package com.mongodb.starter

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

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

