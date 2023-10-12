package com.example.pokemonsapp.utils.extensions

import android.content.Context
import android.widget.Toast
import com.example.domain.models.ErrorEntity
import com.example.pokemonsapp.R

fun Context.showErrorMessage(errorEntity: ErrorEntity) {
    val message = when(errorEntity){
        is ErrorEntity.ApiError.BadRequest -> getString(R.string.bad_request)
        is ErrorEntity.ApiError.Network -> getString(R.string.network_error)
        is ErrorEntity.ApiError.ResponseNull -> getString(R.string.empty_response)
        is ErrorEntity.ApiError.Unknown -> getString(R.string.unknow_error)
    }

    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}