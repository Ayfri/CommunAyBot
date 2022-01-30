package utils

import dev.kord.core.behavior.MessageBehavior
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun MessageBehavior.deleteAfter(time: Int, reason: String = "") = coroutineScope {
	launch {
		delay((time * 1000).toLong())
		this@deleteAfter.delete(reason)
	}
}
