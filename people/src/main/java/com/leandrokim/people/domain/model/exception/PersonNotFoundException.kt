package com.leandrokim.people.domain.model.exception

class PersonNotFoundException(message: String? = "") : RuntimeException(message) {
    companion object {
        const val errorCode = 5002
    }
}