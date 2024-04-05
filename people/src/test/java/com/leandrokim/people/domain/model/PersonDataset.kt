package com.leandrokim.people.domain.model

object PersonDataset {
    val luke = Person(
        url = "https://swapi.dev/api/people/1/",
        name = "Luke Skywalker",
        height = "172",
        mass = "77",
        hairColor = "blond",
        skinColor = "fair",
        eyeColor = "blue",
        birthYear = "19BBY",
        gender = "male",
        homeworld = "https://swapi.dev/api/planets/1/",
        films = emptyList(),
        species = emptyList(),
        vehicles = emptyList(),
        starships = emptyList(),
        created = "2014-12-09T13:50:51.644000Z",
        edited = "2014-12-20T21:17:56.891000Z"
    )
}