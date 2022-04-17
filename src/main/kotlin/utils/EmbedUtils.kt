package utils

import bot
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.supplier.EntitySupplyStrategy
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.create.MessageCreateBuilder
import dev.kord.rest.builder.message.create.embed
import kotlinx.datetime.Clock

suspend fun MessageCreateBuilder.basicEmbed(builder: suspend EmbedBuilder.() -> Unit = {}) {
	return embed {
		val user = bot.getKoin().get<Kord>().getSelf(EntitySupplyStrategy.cacheWithRestFallback)
		
		footer {
			icon = user.avatar?.url
			text = user.username
		}
		
		timestamp = Clock.System.now()
		
		builder()
	}
}
suspend fun TextChannel.basicEmbed(builder: suspend EmbedBuilder.() -> Unit = {}): Message {
	return createEmbed {
		val user = bot.getKoin().get<Kord>().getSelf(EntitySupplyStrategy.cacheWithRestFallback)
		
		footer {
			icon = user.avatar?.url
			text = user.username
		}
		
		timestamp = Clock.System.now()
		
		builder()
	}
}


suspend fun MessageCreateBuilder.completeEmbed(title: String = "", description: String = "", builder: suspend EmbedBuilder.() -> Unit = {}) {
	return basicEmbed {
		this.title = title
		this.description = description
		
		builder()
	}
}
suspend fun TextChannel.completeEmbed(title: String = "", description: String = "", builder: suspend EmbedBuilder.() -> Unit = {}): Message {
	return basicEmbed {
		this.title = title
		this.description = description
		
		builder()
	}
}


suspend fun MessageCreateBuilder.imageEmbed(url: String, title: String = "", description: String = "", builder: suspend EmbedBuilder.() -> Unit = {}) {
	return completeEmbed(title, description) {
		image = url
		
		builder()
	}
}
