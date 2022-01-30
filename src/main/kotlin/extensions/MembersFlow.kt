package extensions

import com.kotlindiscord.kord.extensions.checks.inGuild
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.event
import com.kotlindiscord.kord.extensions.utils.toReaction
import communAyfriID
import dev.kord.common.entity.ButtonStyle
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.getChannelOf
import dev.kord.core.behavior.interaction.acknowledgeEphemeralUpdateMessage
import dev.kord.core.builder.components.emoji
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.event.guild.MemberLeaveEvent
import dev.kord.core.event.interaction.GuildButtonInteractionCreateEvent
import dev.kord.rest.builder.message.create.actionRow
import englishRoleID
import englishWelcomeChannelID
import frenchRoleID
import frenchWelcomeChannelID
import kotlinx.coroutines.flow.count
import memberRoleID
import utils.basicEmbed
import utils.completeEmbed
import utils.deleteAfter
import welcomeChannelID
import kotlin.time.ExperimentalTime

class MembersFlow : Extension() {
	override val name: String = "MemberFlow"
	
	@OptIn(ExperimentalTime::class)
	override suspend fun setup() {
		event<MemberJoinEvent> {
			check { inGuild(communAyfriID) }
			
			action {
				val welcomeChannel = event.guild.getChannel(welcomeChannelID) as TextChannel
				
				welcomeChannel.createMessage {
					basicEmbed {
						title = "Welcome ! Bienvenue !"
						description = """
						Hi ${event.member.mention} <a:blobwave:851853188436328489>, I want to know if you are a French speaker or an English speaker, could you let me know ?
						
						Bonjour ${event.member.mention} <a:blobwave:851853188436328489>, j'aimerais savoir si tu parles le français ou l'anglais, pourrais-tu me dire ?
						""".trimIndent()
						footer {
							text = "You can still change later !\n\nTu peux toujours changer après !"
						}
					}
					
					actionRow {
						interactionButton(ButtonStyle.Success, "english") {
							emoji("\uD83C\uDDEC\uD83C\uDDE7".toReaction())
							label = "I'm an english speaker !"
						}
						interactionButton(ButtonStyle.Success, "french") {
							emoji("\uD83C\uDDEB\uD83C\uDDF7".toReaction())
							label = "Je parle français !"
						}
					}
				}
			}
		}
		
		event<GuildButtonInteractionCreateEvent> {
			check { inGuild(communAyfriID) }
			
			action {
				val guild = kord.getGuild(event.interaction.guildId)!!
				val user = event.interaction.user
				
				when (event.interaction.component!!.customId) {
					"french" -> {
						val channel = guild.getChannelOf<TextChannel>(frenchWelcomeChannelID)
						channel.completeEmbed(
							"Bienvenue à ${user.tag}.", """
						Bienvenue à ${user.mention} sur le serveur **${guild.asGuild().name}** ! <a:blobwave:851853188436328489>
						N'hésite pas à prendre des rôles dans <#506467165327851521> ^^
						Nous sommes dorénavant ${guild.members.count()} ! <:yeey:503128562463932447>
						""".trimIndent()
						) {
							image = (event.interaction.user.avatar ?: event.interaction.user.defaultAvatar).url
						}
						event.interaction.member.addRole(frenchRoleID)
					}
					"english" -> {
						val channel = guild.getChannelOf<TextChannel>(englishWelcomeChannelID)
						channel.completeEmbed(
							"Welcome to ${user.tag}.", """
						Welcome to ${user.mention} on the guild **${guild.asGuild().name}** ! <a:blobwave:851853188436328489>
						Do not hesitate to take roles in <#506467165327851521> ^^
						We are now ${guild.members.count()} ! <:yeey:503128562463932447>
						""".trimIndent()
						) {
							image = (event.interaction.user.avatar ?: event.interaction.user.defaultAvatar).url
						}
						event.interaction.member.addRole(englishRoleID)
					}
				}
				
				event.interaction.member.addRole(memberRoleID)
				event.interaction.acknowledgeEphemeralUpdateMessage {
					embeds = mutableListOf()
				}
				event.interaction.message!!.deleteAfter(10, "User validated first message.")
			}
		}
		
		event<MemberLeaveEvent> {
			check { inGuild(communAyfriID) }
			
			action {
				val welcomeChannel = event.guild.getChannel(frenchWelcomeChannelID) as TextChannel
				
				welcomeChannel.completeEmbed(
					"Au revoir à ${event.user.tag}.", """
					${event.user.tag} quitte notre serveur ! <a:blobwave:851853188436328489>
					Nous sommes dorénavant ${event.guild.members.count()}.
					""".trimIndent()
				) {}
			}
		}
	}
}
