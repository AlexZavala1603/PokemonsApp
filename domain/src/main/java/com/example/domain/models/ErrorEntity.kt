package com.example.domain.models

sealed class ErrorEntity {

    sealed class ApiError : ErrorEntity() {

        object Network : ErrorEntity()

        class BadRequest(val errorMessage: String) : ErrorEntity()

        object Unknown : ErrorEntity()

        object ResponseNull : ErrorEntity()

    }

}