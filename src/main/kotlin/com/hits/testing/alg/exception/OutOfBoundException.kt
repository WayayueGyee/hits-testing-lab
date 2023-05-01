package com.hits.testing.alg.exception

open class OutOfBoundException :
    InvalidDataException("Prices must be greater than or equal to 0 and less than or equal to 1000")
