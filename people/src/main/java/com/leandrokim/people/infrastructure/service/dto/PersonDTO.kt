package com.leandrokim.people.infrastructure.service.dto

import com.google.gson.annotations.SerializedName
import com.leandrokim.people.domain.model.Person

class PersonDTO(
    @SerializedName("url") var url: String,
    @SerializedName("name") var name: String,
    @SerializedName("height") var height: String,
    @SerializedName("mass") var mass: String,
    @SerializedName("hair_color") var hairColor: String,
    @SerializedName("skin_color") var skinColor: String,
    @SerializedName("eye_color") var eyeColor: String,
    @SerializedName("birth_year") var birthYear: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("homeworld") var homeworld: String,
    @SerializedName("films") var films: ArrayList<String> = arrayListOf(),
    @SerializedName("species") var species: ArrayList<String> = arrayListOf(),
    @SerializedName("vehicles") var vehicles: ArrayList<String> = arrayListOf(),
    @SerializedName("starships") var starships: ArrayList<String> = arrayListOf(),
    @SerializedName("created") var created: String,
    @SerializedName("edited") var edited: String
) {
    fun toPerson(): Person {
        return Person(
            url = url,
            name = name,
            height = height,
            mass = mass,
            hairColor = hairColor,
            skinColor = skinColor,
            eyeColor = eyeColor,
            birthYear = birthYear,
            gender = gender,
            homeworld = homeworld,
            films = films,
            species = species,
            vehicles = vehicles,
            starships = starships,
            created = created,
            edited = edited
        )
    }
}