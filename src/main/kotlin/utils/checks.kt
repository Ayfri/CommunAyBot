package utils

import com.kotlindiscord.kord.extensions.checks.memberFor
import com.kotlindiscord.kord.extensions.checks.types.CheckContext
import dev.kord.core.event.Event
import ownerID

suspend fun <T : Event> CheckContext<T>.isOwner() {
	if (!passed) return
	if (ownerID != memberFor(event)!!.id) fail()
}
