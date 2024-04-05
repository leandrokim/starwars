package com.leandrokim.people.infrastructure.service.dto

import com.google.gson.annotations.SerializedName

class PeopleDTO (
    @SerializedName("count") var count: Int,
    @SerializedName("next") var next: String,
    @SerializedName("previous") var previous: String,
    @SerializedName("results") var results: List<PersonDTO> = emptyList()
)