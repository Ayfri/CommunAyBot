package extensions

import adChannelID
import com.kotlindiscord.kord.extensions.checks.inGuild
import com.kotlindiscord.kord.extensions.extensions.Extension
import communAyfriID
import dev.kord.common.entity.ChannelType
import dev.kord.common.entity.PresenceStatus
import dev.kord.core.behavior.reply
import dev.kord.core.entity.channel.TextChannel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import utils.completeEmbed
import utils.isOwner

class Information : Extension() {
	override val bundle = "communaybot"
	override val name: String = "Information"
	
	override suspend fun setup() {
		command {
			name = "extensions.informations.guild-info.name"
			description = "extensions.informations.guild-info.description"
			aliasKey = "extensions.informations.guild-info.aliases"
			check(inGuild(communAyfriID))
			
			action {
				message.reply {
					completeEmbed(translate("extensions.informations.guild-info.embed.title")) {
						val members = guild!!.members.toList()
						val channels = guild!!.channels.toList()
						
						field {
							name = translate("extensions.informations.guild-info.embed.fields.owner.title")
							value = "${guild!!.owner.mention} (`${guild!!.ownerId.asString}`)"
							inline = true
						}
						
						val membersPresences = members.filterNot { it.isBot }.map { it.getPresenceOrNull()?.status }
						
						val bots = members.count { it.isBot }
						val online = membersPresences.count { it == PresenceStatus.Online }
						val idle = membersPresences.count { it == PresenceStatus.Idle }
						val dnd = membersPresences.count { it == PresenceStatus.DoNotDisturb }
						val invisible = membersPresences.count { it == PresenceStatus.Invisible }
						val offline = membersPresences.count { it == null }
						
						field {
							name = translate("extensions.informations.guild-info.embed.fields.members.title")
							value = translate(
								"extensions.informations.guild-info.embed.fields.members.value",
								arrayOf(members.size, online, idle, dnd, offline + invisible, bots)
							)
							inline = true
						}
						
						val channelsTypes = channels.map { it.type }
						
						val textual = channelsTypes.count { it == ChannelType.GuildText }
						val vocals = channelsTypes.count { it == ChannelType.GuildVoice }
						val categories = channelsTypes.count { it == ChannelType.GuildCategory }
						val announces = channelsTypes.count { it == ChannelType.GuildNews }
						val stages = channelsTypes.count { it == ChannelType.GuildStageVoice }
						
						field {
							name = translate("extensions.informations.guild-info.embed.fields.channels.title")
							value = translate(
								"extensions.informations.guild-info.embed.fields.channels.value",
								arrayOf(channels.size, textual, vocals, categories, announces, stages)
							)
							inline = true
						}
						
						field {
							name = translate("extensions.informations.guild-info.embed.fields.roles.title")
							value = guild!!.roles.toList().filterNot { it.managed }.sortedBy { -it.rawPosition }.joinToString("\n") { it.mention }
							inline = true
						}
					}
				}
			}
		}
		
		command {
			name = "extensions.informations.ad.name"
			description = "extensions.informations.ad.description"
			aliasKey = "extensions.informations.ad.aliases"
			check(inGuild(communAyfriID))
			
			action {
				val adChannel = guild!!.channels.first { it.id == adChannelID } as TextChannel
				val ad = adChannel.messages.toList().minByOrNull { it.timestamp }!!
				
				message.reply {
					content = ad.content
				}
			}
		}
		
		command {
			name = "Ayfri"
			description = "extensions.informations.ayfri.description"
			
			action {
				message.reply {
					completeEmbed("Ayfri", translate("extensions.informations.ayfri.embed.description")) {}
				}
			}
		}
		
		command {
			name = "test"
			description = "test"
			check(inGuild(communAyfriID), isOwner())
			
			action {
			
			}
		}
	}
}

