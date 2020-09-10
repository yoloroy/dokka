package org.jetbrains.dokka.kotlinAsJava.translators

import org.jetbrains.dokka.base.translators.documentables.DocumentablesFilteringStrategies
import org.jetbrains.dokka.model.DFunction

object KotlinAsjavaDocumentableFilteringStrategies : DocumentablesFilteringStrategies {
    override fun shouldConstructorBeInPage(constructor: DFunction) = true
}
