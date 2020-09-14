package org.jetbrains.dokka.base.parsers

import org.jetbrains.dokka.model.doc.*

abstract class Parser {

    abstract fun parseStringToDocNode(extractedString: String): DocTag

    abstract fun preparse(text: String): String

    fun parse(text: String): DocumentationNode {

        val list = jkdocToListOfPairs(preparse(text))

        val mappedList: List<TagWrapper> = list.map {
            when (it.first) {
                "description" -> Description(parseStringToDocNode(it.second) as RootDocTag)
                "author" -> Author(parseStringToDocNode(it.second) as RootDocTag)
                "version" -> Version(parseStringToDocNode(it.second) as RootDocTag)
                "since" -> Since(parseStringToDocNode(it.second) as RootDocTag)
                "see" -> See(
                    parseStringToDocNode(it.second.substringAfter(' ')) as RootDocTag,
                    it.second.substringBefore(' '),
                    null
                )
                "param" -> Param(
                    parseStringToDocNode(it.second.substringAfter(' ')) as RootDocTag,
                    it.second.substringBefore(' ')
                )
                "property" -> Property(
                    parseStringToDocNode(it.second.substringAfter(' ')) as RootDocTag,
                    it.second.substringBefore(' ')
                )
                "return" -> Return(parseStringToDocNode(it.second) as RootDocTag)
                "constructor" -> Constructor(parseStringToDocNode(it.second) as RootDocTag)
                "receiver" -> Receiver(parseStringToDocNode(it.second) as RootDocTag)
                "throws", "exception" -> Throws(
                    parseStringToDocNode(it.second.substringAfter(' ')) as RootDocTag,
                    it.second.substringBefore(' ')
                )
                "deprecated" -> Deprecated(parseStringToDocNode(it.second) as RootDocTag)
                "sample" -> Sample(
                    parseStringToDocNode(it.second.substringAfter(' ')) as RootDocTag,
                    it.second.substringBefore(' ')
                )
                "suppress" -> Suppress(parseStringToDocNode(it.second) as RootDocTag)
                else -> CustomTagWrapper(parseStringToDocNode(it.second) as RootDocTag, it.first)
            }
        }
        return DocumentationNode(mappedList)
    }

    private fun jkdocToListOfPairs(javadoc: String): List<Pair<String, String>> =
        "description $javadoc"
            .split("\n@")
            .map { content ->
                val contentWithEscapedAts = content.replace("\\@", "@")
                val (tag, body) = contentWithEscapedAts.split(" ", limit = 2)
                tag to body
            }
}
