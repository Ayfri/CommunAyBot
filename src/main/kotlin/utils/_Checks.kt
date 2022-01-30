package utils

import com.kotlindiscord.kord.extensions.checks.types.CheckContext
import com.kotlindiscord.kord.extensions.checks.userFor
import dev.kord.core.event.Event
import ownerID

suspend fun <T : Event> CheckContext<T>.isOwner() {
	if (!passed) return
	if (ownerID != userFor(event)!!.id) fail()
}
