
import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.i18n.SupportedLocales
import com.kotlindiscord.kord.extensions.utils.env
import dev.kord.common.entity.PresenceStatus
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import extensions.EasterEggs
import extensions.Information
import extensions.Links
import extensions.MembersFlow
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

lateinit var bot: ExtensibleBot

@OptIn(PrivilegedIntent::class, ExperimentalTime::class)
suspend fun main() {
	bot = ExtensibleBot(env("TOKEN")) {
		chatCommands {
			enabled = true
		}
		
		extensions {
			sentry { enable = false }
			
			help {
				deletePaginatorOnTimeout = true
				paginatorTimeout = 5.minutes.inWholeMilliseconds
			}
			
			add(::EasterEggs)
			add(::Information)
			add(::Links)
			add(::MembersFlow)
		}
		/*hooks {
			extensionAdded { ex ->
				println(ex.commands.map { it.name })
			}
		}*/
		
		intents {
			+Intents.all
		}
		
		i18n {
			defaultLocale = SupportedLocales.ENGLISH
			interactionUserLocaleResolver()
		}
		
		members {
			fillPresences = true
			all()
		}
		
		applicationCommands {
			enabled = true
			defaultGuild = communAyfriID
		}
		
		presence {
			status = PresenceStatus.Online
			
			streaming("looking Ayfri's stream", "https://twitch.tv/Ayfri1015")
		}
	}
	
	bot.start()
}
