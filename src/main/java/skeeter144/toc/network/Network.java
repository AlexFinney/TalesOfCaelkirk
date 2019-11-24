package skeeter144.toc.network;

import net.minecraft.entity.player.ServerPlayerEntity;
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

		public <MSG> void sendTo(MSG msg, ServerPlayerEntity player) {
			channel.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		}
		
		public <MSG> void sendToServer(MSG msg) {
			channel.sendToServer(msg);
		}
		
		private void registerPackets() {
			channel.registerMessage(msgId++, SetClientTOCPlayerPKT.class, SetClientTOCPlayerPKT::encode, SetClientTOCPlayerPKT::decode, SetClientTOCPlayerPKT.Handler::handle);
			channel.registerMessage(msgId++, AddLevelXpPKT.class, AddLevelXpPKT::encode, AddLevelXpPKT::decode, AddLevelXpPKT.Handler::handle);
			channel.registerMessage(msgId++, AdjustScreenDimPKT.class, AdjustScreenDimPKT::encode, AdjustScreenDimPKT::decode, AdjustScreenDimPKT.Handler::handle);
			channel.registerMessage(msgId++, AnimationEventPKT.class, AnimationEventPKT::encode, AnimationEventPKT::decode, AnimationEventPKT.Handler::handle);
			channel.registerMessage(msgId++, CloseGuisPKT.class, CloseGuisPKT::encode, CloseGuisPKT::decode, CloseGuisPKT.Handler::handle);
			channel.registerMessage(msgId++, CraftItemPKT.class, CraftItemPKT::encode, CraftItemPKT::decode, CraftItemPKT.Handler::handle);
			channel.registerMessage(msgId++, HealthManaRegenUpdatePKT.class, HealthManaRegenUpdatePKT::encode, HealthManaRegenUpdatePKT::decode, HealthManaRegenUpdatePKT.Handler::handle);
			channel.registerMessage(msgId++, ItemCraftedPKT.class, ItemCraftedPKT::encode, ItemCraftedPKT::decode, ItemCraftedPKT.Handler::handle);
			channel.registerMessage(msgId++, ItemCraftingQueueAddedPKT.class, ItemCraftingQueueAddedPKT::encode, ItemCraftingQueueAddedPKT::decode, ItemCraftingQueueAddedPKT.Handler::handle);
			channel.registerMessage(msgId++, ItemTransactionPKT.class, ItemTransactionPKT::encode, ItemTransactionPKT::decode, ItemTransactionPKT.Handler::handle);
			channel.registerMessage(msgId++, LightningBoltCastPKT.class, LightningBoltCastPKT::encode, LightningBoltCastPKT::decode, LightningBoltCastPKT.Handler::handle);
			channel.registerMessage(msgId++, NotfyClientOfEffectPKT.class, NotfyClientOfEffectPKT::encode, NotfyClientOfEffectPKT::decode, NotfyClientOfEffectPKT.Handler::handle);
			channel.registerMessage(msgId++, OpenGUIBankPKT.class, OpenGUIBankPKT::encode, OpenGUIBankPKT::decode, OpenGUIBankPKT.Handler::handle);
			channel.registerMessage(msgId++, OpenShopGuiPKT.class, OpenShopGuiPKT::encode, OpenShopGuiPKT::decode, OpenShopGuiPKT.Handler::handle);
			channel.registerMessage(msgId++, PlayerVitalsUpdatePKT.class, PlayerVitalsUpdatePKT::encode, PlayerVitalsUpdatePKT::decode, PlayerVitalsUpdatePKT.Handler::handle);
			channel.registerMessage(msgId++, PlayMobAnimationPKT.class, PlayMobAnimationPKT::encode, PlayMobAnimationPKT::decode, PlayMobAnimationPKT.Handler::handle);
			channel.registerMessage(msgId++, PlayMusicTrackPKT.class, PlayMusicTrackPKT::encode, PlayMusicTrackPKT::decode, PlayMusicTrackPKT.Handler::handle);
			channel.registerMessage(msgId++, QuestDialogResponsePKT.class, QuestDialogResponsePKT::encode, QuestDialogResponsePKT::decode, QuestDialogResponsePKT.Handler::handle);
			channel.registerMessage(msgId++, SendIconUpdatePKT.class, SendIconUpdatePKT::encode, SendIconUpdatePKT::decode, SendIconUpdatePKT.Handler::handle);
			channel.registerMessage(msgId++, SetAnvilRecipePKT.class, SetAnvilRecipePKT::encode, SetAnvilRecipePKT::decode, SetAnvilRecipePKT.Handler::handle);
			channel.registerMessage(msgId++, SetMobSpawnerSettingsPKT.class, SetMobSpawnerSettingsPKT::encode, SetMobSpawnerSettingsPKT::decode, SetMobSpawnerSettingsPKT.Handler::handle);
			channel.registerMessage(msgId++, ShouldShowRegionsPKT.class, ShouldShowRegionsPKT::encode, ShouldShowRegionsPKT::decode, ShouldShowRegionsPKT.Handler::handle);
			channel.registerMessage(msgId++, ShowEntityDialogPKT.class, ShowEntityDialogPKT::encode, ShowEntityDialogPKT::decode, ShowEntityDialogPKT.Handler::handle);
			channel.registerMessage(msgId++, SpawnParticlesPKT.class, SpawnParticlesPKT::encode, SpawnParticlesPKT::decode, SpawnParticlesPKT.Handler::handle);
			channel.registerMessage(msgId++, SpecialAttackCooldownPKT.class, SpecialAttackCooldownPKT::encode, SpecialAttackCooldownPKT::decode, SpecialAttackCooldownPKT.Handler::handle);
			channel.registerMessage(msgId++, UpdatePlayerFlyingPKT.class, UpdatePlayerFlyingPKT::encode, UpdatePlayerFlyingPKT::decode, UpdatePlayerFlyingPKT.Handler::handle);
			channel.registerMessage(msgId++, WandEmbuePKT.class, WandEmbuePKT::encode, WandEmbuePKT::decode, WandEmbuePKT.Handler::handle);
			channel.registerMessage(msgId++, OpenGUIClientPKT.class, OpenGUIClientPKT::encode, OpenGUIClientPKT::decode, OpenGUIClientPKT.Handler::handle);
		}
	}
	
}
