package com.marwa.sephora.data.utils

import java.io.IOException

sealed class NetworkException(message: String?, cause: Throwable? = null) :
    IOException(message, cause) {
    object MissingData : NetworkException("data not Fetched")
    class ErreurServeur(cause: Throwable) :
        NetworkException("Server Error : " + cause.message, cause)
}