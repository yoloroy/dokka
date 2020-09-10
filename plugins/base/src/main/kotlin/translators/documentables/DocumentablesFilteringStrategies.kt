package org.jetbrains.dokka.base.translators.documentables

import org.jetbrains.dokka.model.DFunction

interface DocumentablesFilteringStrategies {
    fun shouldConstructorBeInPage(constructor: DFunction): Boolean
}
