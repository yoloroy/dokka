package org.jetbrains.dokka.model.doc

import org.jetbrains.dokka.links.DRI
import org.jetbrains.dokka.model.WithChildren

sealed class TagWrapper : WithChildren<DocTag> {
    abstract val root: RootDocTag
    override val children: List<DocTag>
        get() = root.children
}

sealed class NamedTagWrapper : TagWrapper() {
    abstract val name: String
}

data class Description(override val root: RootDocTag) : TagWrapper()
data class Author(override val root: RootDocTag) : TagWrapper()
data class Version(override val root: RootDocTag) : TagWrapper()
data class Since(override val root: RootDocTag) : TagWrapper()
data class See(override val root: RootDocTag, override val name: String, val address: DRI?) : NamedTagWrapper()
data class Param(override val root: RootDocTag, override val name: String) : NamedTagWrapper()
data class Return(override val root: RootDocTag) : TagWrapper()
data class Receiver(override val root: RootDocTag) : TagWrapper()
data class Constructor(override val root: RootDocTag) : TagWrapper()
data class Throws(override val root: RootDocTag, override val name: String) : NamedTagWrapper()
data class Sample(override val root: RootDocTag, override val name: String) : NamedTagWrapper()
data class Deprecated(override val root: RootDocTag) : TagWrapper()
data class Property(override val root: RootDocTag, override val name: String) : NamedTagWrapper()
data class Suppress(override val root: RootDocTag) : TagWrapper()
data class CustomTagWrapper(override val root: RootDocTag, override val name: String) : NamedTagWrapper()
