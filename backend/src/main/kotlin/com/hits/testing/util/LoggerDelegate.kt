package com.hits.testing.util

import java.util.logging.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    private fun getClassForLogging(javaClass: Class<*>): Class<*> = javaClass.enclosingClass?.takeIf {
        it.kotlin.companionObject?.java == javaClass
    } ?: javaClass

    override fun getValue(thisRef: R, property: KProperty<*>): Logger =
        Logger.getLogger(getClassForLogging(thisRef.javaClass).name)
}

