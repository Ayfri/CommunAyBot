package extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.common.annotation.KordPreview

@OptIn(KordPreview::class)
class Links : Extension() {
	override val name: String = "Links"
	
	override suspend fun setup() {
		publicSlashCommand {
			name = "github"
			description = "extensions.links.github.description"
			
			action {
				respond {
					content = "https://github.com/Ayfri"
				}
			}
		}
		
		publicSlashCommand {
			name = "twitch"
			description = "extensions.links.twitch.description"
			
			action {
				respond {
					content = "https://twitch.tv/Ayfri1015"
				}
			}
		}
	}
}
