package extensions

import com.kotlindiscord.kord.extensions.checks.inGuild
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.event
import communAyfriID
import dev.kord.common.entity.ButtonStyle
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.getChannelOf
import dev.kord.core.behavior.interaction.updateEphemeralMessage
import dev.kord.core.builder.components.emoji
import dev.kord.core.entity.ReactionEmoji
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

class MembersFlow : Extension() {
	override val name = "MemberFlow"
	
	override suspend fun setup() {
		event<MemberJoinEvent> {
			check { inGuild(communAyfriID) }
			
			action {
				val welcomeChannel = event.guild.getChannel(welcomeChannelID) as TextChannel
				
				welcomeChannel.createMessage {
					basicEmbed {
						event.member.publicFlags
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
							emoji(ReactionEmoji.Unicode("\uD83C\uDDEC\uD83C\uDDE7"))
							label = "I'm an english speaker !"
						}
						interactionButton(ButtonStyle.Success, "french") {
							emoji(ReactionEmoji.Unicode("\uD83C\uDDEB\uD83C\uDDF7"))
							label = "Je parle français !"
						}
					}
				}
			}
		}
		
		event<GuildButtonInteractionCreateEvent> {
			check { inGuild(communAyfriID) }
			
			action {
				val guild = kord.getGuild(event.interaction.guildId) ?: return@action
				val user = event.interaction.user
				
				val buttonName = event.interaction.component.customId
				val channelId = when (buttonName) {
					"english" -> englishWelcomeChannelID
					"french" -> frenchWelcomeChannelID
					else -> null
				}
				val roleId = when (buttonName) {
					"english" -> englishRoleID
					"french" -> frenchRoleID
					else -> null
				}
				
				if (channelId != null && roleId != null) {
					val channel = guild.getChannelOf<TextChannel>(channelId)
					channel.completeEmbed(
						translate("extensions.members-flow.user-validate-welcome-screen.embed.title", user.tag),
						translate("extensions.members-flow.user-validate-welcome-screen.embed.description", arrayOf(user.mention, guild.name, guild.members.count()))
					) {
						image = (event.interaction.user.avatar ?: event.interaction.user.defaultAvatar).url
					}
					guild.getMemberOrNull(user.id)?.addRole(roleId)
				}
				
				guild.getMemberOrNull(user.id)?.addRole(memberRoleID)
				event.interaction.updateEphemeralMessage {
					embeds = mutableListOf()
				}
				event.interaction.message.deleteAfter(10, "User validated first message.")
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
