package extensions

import com.kotlindiscord.kord.extensions.checks.inGuild
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.event
import communAyfriID
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.event.guild.MemberLeaveEvent
import kotlinx.coroutines.flow.count
import utils.completeEmbed
import utils.isOwner
import welcomeChannelID

class MembersFlow : Extension() {
	override val name: String = "MemberFlow"
	
	override suspend fun setup() {
		event<MemberJoinEvent> {
			check { inGuild(communAyfriID) }
			
			action {
				val welcomeChannel = event.guild.getChannel(welcomeChannelID) as TextChannel
				
				welcomeChannel.completeEmbed(
					"Bienvenue à ${event.member.tag}.", """
						Bienvenue à ${event.member.mention} sur le serveur ${event.guild.asGuild()} ! <a:blobwave:851853188436328489>
						N'hésite pas à prendre des rôles dans <#506467165327851521> ^^
						Nous sommes dorénavant ${event.guild.members.count()} ! <:yeey:503128562463932447>
						""".trimIndent()
				) {}
			}
		}
		
		event<MemberLeaveEvent> {
			check {
				inGuild(communAyfriID)
				if (!passed) isOwner()
			}
			
			action {
				val welcomeChannel = event.guild.getChannel(welcomeChannelID) as TextChannel
				
				welcomeChannel.completeEmbed(
					"Au revoir à ${event.user.tag}.", """
					${event.user.tag} quitte notre serveur ! <a:blobwave:851853188436328489>
					Nous sommes dorénavant ${event.guild.members.count()} !
					""".trimIndent()
				) {}
			}
		}
	}
}
