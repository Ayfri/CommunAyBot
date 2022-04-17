package extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import dev.kord.common.annotation.KordPreview
import utils.respond

class Links : Extension() {
	override val name = "Links"
	
	override suspend fun setup() {
		publicSlashCommand {
			name = "github"
			description = "extensions.links.github.description"
			
			action {
				respond("https://github.com/Ayfri")
			}
		}
		
		publicSlashCommand {
			name = "twitch"
			description = "extensions.links.twitch.description"
			
			action {
				respond("https://twitch.tv/Ayfri1015")
			}
		}
	}
}
