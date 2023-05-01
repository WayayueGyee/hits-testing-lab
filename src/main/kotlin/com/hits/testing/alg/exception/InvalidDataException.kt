package com.hits.testing.alg.exception

open class InvalidDataException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
}
