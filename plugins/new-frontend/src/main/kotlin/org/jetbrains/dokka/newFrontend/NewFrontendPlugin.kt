package org.jetbrains.dokka.newFrontend

import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.dokka.CoreExtensions
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.newFrontend.pages.NewFrontendDocumentableToPageTranslator
import org.jetbrains.dokka.newFrontend.renderer.NewFrontendRenderer
import org.jetbrains.dokka.newFrontend.transformers.NewFrontendToViewTransformer
import org.jetbrains.dokka.plugability.DokkaPlugin
import org.jetbrains.dokka.plugability.querySingle

class NewFrontendPlugin : DokkaPlugin() {
    val dokkaBasePlugin by lazy { plugin<DokkaBase>() }

    val pageTranslator by extending {
        CoreExtensions.documentableToPageTranslator providing { context ->
            NewFrontendDocumentableToPageTranslator(
                dokkaBasePlugin.querySingle { commentsToContentConverter },
                dokkaBasePlugin.querySingle { signatureProvider },
                context.logger
            )
        } override dokkaBasePlugin.documentableToPageTranslator
    }

    val toViewTransformer by extending {
        CoreExtensions.pageTransformer providing {
            NewFrontendToViewTransformer(it)
        }
    }

    @ExperimentalSerializationApi
    val renderer by extending {
        (CoreExtensions.renderer
                providing { ctx -> NewFrontendRenderer(ctx) }
                override dokkaBasePlugin.htmlRenderer)
    }
}