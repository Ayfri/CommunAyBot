package extensions

import com.kotlindiscord.kord.extensions.checks.inGuild
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.env
import communAyfriID
import dev.kord.core.behavior.reply
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import utils.imageEmbed

class EasterEggs : Extension() {
	override val bundle = "communaybot"
	override val name = "EasterEggs"
	
	override suspend fun setup() {
		chatCommand {
			name = "extensions.easteregg.sausage.name"
			description = "extensions.easteregg.sausage.description"
			hidden = true
			check { inGuild(communAyfriID) }
			
			action {
				val gistLink = "https://gist.githubusercontent.com/Ayfri/2e0c687ffcefa522c9329749ed46ef90/raw/Sausages.txt"
				val client = HttpClient()
				val sausages = client.get(gistLink).bodyAsText().split('\n')
				val rand = (0..1000).random()
				
				val link =
					if (rand == 42) env("DUCK_PHOTO")
					else sausages.random()
				
				message.reply {
					imageEmbed(link, "Jeej, jaaj, leel, luul.")
				}
			}
		}
		
		publicSlashCommand {
			name = "get-star"
			description = "You got a superstar !"
			
			action {
				respond {
					content = """
					|<a:power_star:851859990141993000> you got a star
					|		<a:coin:851862634461921290> x ${(0..150).random()}
					""".trimMargin()
				}
			}
		}
	}
}
