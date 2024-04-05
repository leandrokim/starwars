package com.leandrokim.people.domain.model.exception

class InvalidIndexException(message: String? = "") : RuntimeException(message) {
    companion object {
        const val errorCode = 5000
    }
}