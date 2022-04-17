package utils

import com.kotlindiscord.kord.extensions.types.PublicInteractionContext
import com.kotlindiscord.kord.extensions.types.respond

suspend fun PublicInteractionContext.respond(message: String) {
	respond { content = message }
}
