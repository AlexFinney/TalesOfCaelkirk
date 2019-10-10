package skeeter144.toc.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import skeeter144.toc.util.Reference;

public class Network {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannelWrapper INSTANCE; 
	static {
		 INSTANCE = SimpleChannelWrapper.create();
	}
	private static int id = 0;
	public static int getNextId() {
		return id++;
	}
	
	public static class SimpleChannelWrapper{
		SimpleChannel channel;
		int msgId = 0;
		private SimpleChannelWrapper(SimpleChannel channel) {
			this.channel = channel;
			registerPackets();
		}
		
		public static SimpleChannelWrapper create() {
			SimpleChannel channel = NetworkRegistry.ChannelBuilder
					.named(new ResourceLocation(Reference.MODID, "main_channel"))
					.clientAcceptedVersions(PROTOCOL_VERSION::equals)
					.serverAcceptedVersions(PROTOCOL_VERSION::equals)
					.networkProtocolVersion(() -> PROTOCOL_VERSION)
					.simpleChannel();
			return new SimpleChannelWrapper(channel);
		}
		
		public <MSG> void sendToAll(MSG msg) {
			channel.send(PacketDistributor.ALL.noArg(), msg);
		}
		public <MSG> void sendToAllAround(MSG msg, Chunk chunk) {
			channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
		}

		public <MSG> void sendTo(MSG msg, EntityPlayerMP player) {
			channel.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		}
		
		public <MSG> void sendToServer(MSG msg) {
			channel.sendToServer(msg);
		}
		
		private void registerPackets() {
			channel.registerMessage(msgId++, SetClientTOCPlayerPKT.class, SetClientTOCPlayerPKT::encode, SetClientTOCPlayerPKT::decode, SetClientTOCPlayerPKT.Handler::handle);
			channel.registerMessage(msgId++, AddLevelXpPKT.class, AddLevelXpPKT::encode, AddLevelXpPKT::decode, AddLevelXpPKT.Handler::handle);
			channel.registerMessage(msgId++, AdjustPlayersScreenDim.class, AdjustPlayersScreenDim::encode, AdjustPlayersScreenDim::decode, AdjustPlayersScreenDim.Handler::handle);
			channel.registerMessage(msgId++, AnimationEventMessage.class, AnimationEventMessage::encode, AnimationEventMessage::decode, AnimationEventMessage.Handler::handle);
			channel.registerMessage(msgId++, CloseGuisMessage.class, CloseGuisMessage::encode, CloseGuisMessage::decode, CloseGuisMessage.Handler::handle);
			channel.registerMessage(msgId++, CraftItemMessage.class, CraftItemMessage::encode, CraftItemMessage::decode, CraftItemMessage.Handler::handle);
			channel.registerMessage(msgId++, HealthManaRegenUpdateMessage.class, HealthManaRegenUpdateMessage::encode, HealthManaRegenUpdateMessage::decode, HealthManaRegenUpdateMessage.Handler::handle);
			channel.registerMessage(msgId++, ItemCraftedMessage.class, ItemCraftedMessage::encode, ItemCraftedMessage::decode, ItemCraftedMessage.Handler::handle);
			channel.registerMessage(msgId++, ItemCraftingQueueAddedMessage.class, ItemCraftingQueueAddedMessage::encode, ItemCraftingQueueAddedMessage::decode, ItemCraftingQueueAddedMessage.Handler::handle);
			channel.registerMessage(msgId++, ItemTransactionMessage.class, ItemTransactionMessage::encode, ItemTransactionMessage::decode, ItemTransactionMessage.Handler::handle);
			channel.registerMessage(msgId++, LightningBoltCastMessage.class, LightningBoltCastMessage::encode, LightningBoltCastMessage::decode, LightningBoltCastMessage.Handler::handle);
			channel.registerMessage(msgId++, NotfyClientOfEffectMessage.class, NotfyClientOfEffectMessage::encode, NotfyClientOfEffectMessage::decode, NotfyClientOfEffectMessage.Handler::handle);
			channel.registerMessage(msgId++, OpenBankGUIMessage.class, OpenBankGUIMessage::encode, OpenBankGUIMessage::decode, OpenBankGUIMessage.Handler::handle);
			channel.registerMessage(msgId++, OpenShopGuiMessage.class, OpenShopGuiMessage::encode, OpenShopGuiMessage::decode, OpenShopGuiMessage.Handler::handle);
			channel.registerMessage(msgId++, PlayerVitalsUpdateMessage.class, PlayerVitalsUpdateMessage::encode, PlayerVitalsUpdateMessage::decode, PlayerVitalsUpdateMessage.Handler::handle);
			channel.registerMessage(msgId++, PlayMobAnimationMessage.class, PlayMobAnimationMessage::encode, PlayMobAnimationMessage::decode, PlayMobAnimationMessage.Handler::handle);
			channel.registerMessage(msgId++, PlayMusicTrackMessage.class, PlayMusicTrackMessage::encode, PlayMusicTrackMessage::decode, PlayMusicTrackMessage.Handler::handle);
			channel.registerMessage(msgId++, QuestDialogResponseMessage.class, QuestDialogResponseMessage::encode, QuestDialogResponseMessage::decode, QuestDialogResponseMessage.Handler::handle);
			channel.registerMessage(msgId++, SendIconUpdateMessage.class, SendIconUpdateMessage::encode, SendIconUpdateMessage::decode, SendIconUpdateMessage.Handler::handle);
			channel.registerMessage(msgId++, SetAnvilRecipeMessage.class, SetAnvilRecipeMessage::encode, SetAnvilRecipeMessage::decode, SetAnvilRecipeMessage.Handler::handle);
			channel.registerMessage(msgId++, SetMobSpawnerSettingsMessage.class, SetMobSpawnerSettingsMessage::encode, SetMobSpawnerSettingsMessage::decode, SetMobSpawnerSettingsMessage.Handler::handle);
			channel.registerMessage(msgId++, ShouldShowRegionsMessage.class, ShouldShowRegionsMessage::encode, ShouldShowRegionsMessage::decode, ShouldShowRegionsMessage.Handler::handle);
			channel.registerMessage(msgId++, ShowEntityDialogMessage.class, ShowEntityDialogMessage::encode, ShowEntityDialogMessage::decode, ShowEntityDialogMessage.Handler::handle);
			channel.registerMessage(msgId++, SpawnParticlesPKT.class, SpawnParticlesPKT::encode, SpawnParticlesPKT::decode, SpawnParticlesPKT.Handler::handle);
			channel.registerMessage(msgId++, SpecialAttackCooldownMessage.class, SpecialAttackCooldownMessage::encode, SpecialAttackCooldownMessage::decode, SpecialAttackCooldownMessage.Handler::handle);
			channel.registerMessage(msgId++, UpdatePlayerFlyingMessage.class, UpdatePlayerFlyingMessage::encode, UpdatePlayerFlyingMessage::decode, UpdatePlayerFlyingMessage.Handler::handle);
			channel.registerMessage(msgId++, WandEmbueMessage.class, WandEmbueMessage::encode, WandEmbueMessage::decode, WandEmbueMessage.Handler::handle);
			channel.registerMessage(msgId++, OpenGUIClientPKT.class, OpenGUIClientPKT::encode, OpenGUIClientPKT::decode, OpenGUIClientPKT.Handler::handle);
		}
	}
	
}
