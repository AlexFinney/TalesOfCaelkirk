package skeeter144.toc.proxy;

import java.util.Random;

import mapwriter.map.Marker;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.client.gui.GuiHandler;
import skeeter144.toc.entity.TOCEntities;
import skeeter144.toc.handlers.EntityHandler;
import skeeter144.toc.handlers.PlayerInteractHandler;
import skeeter144.toc.handlers.PlayerInventoryHandler;
import skeeter144.toc.handlers.PlayerLoginHandler;
import skeeter144.toc.handlers.tick.TickHandler;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.AddLevelXpMessage.UpdateLevelXpMessageHandler;
import skeeter144.toc.network.AdjustPlayersScreenDim;
import skeeter144.toc.network.AdjustPlayersScreenDim.AdjustPlayersScreenDimHandler;
import skeeter144.toc.network.AnimationEventMessage;
import skeeter144.toc.network.AnimationEventMessage.AnimationEventMessageHandler;
import skeeter144.toc.network.CloseGuisMessage;
import skeeter144.toc.network.CloseGuisMessage.CloseGuisMessageHandler;
import skeeter144.toc.network.CraftItemMessage;
import skeeter144.toc.network.CraftItemMessage.CraftItemMessageHandler;
import skeeter144.toc.network.HealthManaRegenUpdateMessage;
import skeeter144.toc.network.HealthManaRegenUpdateMessage.HealthManaRegenUpdateHandler;
import skeeter144.toc.network.ItemCraftedMessage;
import skeeter144.toc.network.ItemCraftedMessage.ItemCraftedMessageHandler;
import skeeter144.toc.network.ItemCraftingQueueAddedMessage;
import skeeter144.toc.network.ItemCraftingQueueAddedMessage.ItemCraftingQueueAddedMessageHandler;
import skeeter144.toc.network.ItemTransactionMessage;
import skeeter144.toc.network.ItemTransactionMessage.ItemTransactionMessageHandler;
import skeeter144.toc.network.LightningBoltCastMessage;
import skeeter144.toc.network.LightningBoltCastMessage.LightningBoltCastHandler;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.NotfyClientOfEffectMessage;
import skeeter144.toc.network.NotfyClientOfEffectMessage.NotfyClientOfEffectMessageHandler;
import skeeter144.toc.network.OpenBankGUIMessage;
import skeeter144.toc.network.OpenBankGUIMessage.OpenBankGUIMessageHandler;
import skeeter144.toc.network.OpenShopGuiMessage;
import skeeter144.toc.network.OpenShopGuiMessage.OpenShopGuiMessageHandler;
import skeeter144.toc.network.PlayMobAnimationMessage;
import skeeter144.toc.network.PlayMobAnimationMessage.PlayMobAnimationMessageHandler;
import skeeter144.toc.network.PlayMusicTrackMessage;
import skeeter144.toc.network.PlayMusicTrackMessage.PlayMusicTrackHandler;
import skeeter144.toc.network.PlayerVitalsUpdateMessage;
import skeeter144.toc.network.PlayerVitalsUpdateMessage.PlayerVitalsUpdateMessageHandler;
import skeeter144.toc.network.QuestDialogResponseMessage;
import skeeter144.toc.network.QuestDialogResponseMessage.QuestDialogResponseMessageHandler;
import skeeter144.toc.network.SendIconUpdateMessage;
import skeeter144.toc.network.SendIconUpdateMessage.SendIconUpdateMessageHandler;
import skeeter144.toc.network.SetAnvilRecipeMessage;
import skeeter144.toc.network.SetAnvilRecipeMessage.SetAnvilRecipeMessageHandler;
import skeeter144.toc.network.SetClientTOCPlayerMessage;
import skeeter144.toc.network.SetClientTOCPlayerMessage.SetClientTOCPlayerMessageHandler;
import skeeter144.toc.network.SetMobSpawnerSettingsMessage;
import skeeter144.toc.network.SetMobSpawnerSettingsMessage.SetMobSpawnerSettingsMessageHandler;
import skeeter144.toc.network.ShouldShowRegionsMessage;
import skeeter144.toc.network.ShouldShowRegionsMessage.ShouldShowRegionsMessageHandler;
import skeeter144.toc.network.ShowEntityDialogMessage;
import skeeter144.toc.network.ShowEntityDialogMessage.ShowQuestDialogMessageHandler;
import skeeter144.toc.network.SpawnBlockedMessage;
import skeeter144.toc.network.SpawnBlockedMessage.SpawnBlockedMessageHandler;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.network.SpawnParticlesPKT.SpawnParticlesMessageHandler;
import skeeter144.toc.network.SpecialAttackCooldownMessage;
import skeeter144.toc.network.SpecialAttackCooldownMessage.SpecialAttackCooldownMessageHandler;
import skeeter144.toc.network.UpdatePlayerFlyingMessage;
import skeeter144.toc.network.UpdatePlayerFlyingMessage.UpdatePlayerFlyingMessageHandler;
import skeeter144.toc.network.WandEmbueMessage;
import skeeter144.toc.network.WandEmbueMessage.WandEmbueMessageHandler;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.skills.Crafting;
import skeeter144.toc.skills.Woodcutting;
import skeeter144.toc.sounds.Sounds;

public class CommonProxy 
{
	public void preInit(FMLPreInitializationEvent event)
	{
		TOCBlocks.registerAllBlocks();
		
		MinecraftForge.EVENT_BUS.register(new TOCItems());
		MinecraftForge.EVENT_BUS.register(new Crafting());
		
		MinecraftForge.EVENT_BUS.register(new Sounds());
		
		TOCBlocks.registerAllTileEntities();
		TOCEntities.registerEntities();
		
		EntityLevels.init();
		Woodcutting.init();
		
		QuestManager.initQuests();
	}
	
	public void init(FMLInitializationEvent event){
		Network.INSTANCE.registerMessage(WandEmbueMessageHandler.class, WandEmbueMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(LightningBoltCastHandler.class, LightningBoltCastMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(AnimationEventMessageHandler.class, AnimationEventMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(QuestDialogResponseMessageHandler.class, QuestDialogResponseMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(ItemTransactionMessageHandler.class, ItemTransactionMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(CraftItemMessageHandler.class, CraftItemMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(SetAnvilRecipeMessageHandler.class, SetAnvilRecipeMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(UpdatePlayerFlyingMessageHandler.class, UpdatePlayerFlyingMessage.class, Network.getNextId(), Side.SERVER);
		Network.INSTANCE.registerMessage(SetMobSpawnerSettingsMessageHandler.class, SetMobSpawnerSettingsMessage.class, Network.getNextId(), Side.SERVER);
		
		Network.INSTANCE.registerMessage(PlayerVitalsUpdateMessageHandler.class, PlayerVitalsUpdateMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(SetClientTOCPlayerMessageHandler.class, SetClientTOCPlayerMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(SpawnParticlesMessageHandler.class, SpawnParticlesPKT.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(HealthManaRegenUpdateHandler.class, HealthManaRegenUpdateMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(SpecialAttackCooldownMessageHandler.class, SpecialAttackCooldownMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(UpdateLevelXpMessageHandler.class, AddLevelXpMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(SpawnBlockedMessageHandler.class, SpawnBlockedMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(PlayMobAnimationMessageHandler.class, PlayMobAnimationMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(ShouldShowRegionsMessageHandler.class, ShouldShowRegionsMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(NotfyClientOfEffectMessageHandler.class, NotfyClientOfEffectMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(AdjustPlayersScreenDimHandler.class, AdjustPlayersScreenDim.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(PlayMusicTrackHandler.class, PlayMusicTrackMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(ShowQuestDialogMessageHandler.class, ShowEntityDialogMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(CloseGuisMessageHandler.class, CloseGuisMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(OpenShopGuiMessageHandler.class, OpenShopGuiMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(ItemCraftingQueueAddedMessageHandler.class, ItemCraftingQueueAddedMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(ItemCraftedMessageHandler.class, ItemCraftedMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(OpenBankGUIMessageHandler.class, OpenBankGUIMessage.class, Network.getNextId(), Side.CLIENT);
		Network.INSTANCE.registerMessage(SendIconUpdateMessageHandler.class, SendIconUpdateMessage.class, Network.getNextId(), Side.CLIENT);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(TOCMain.instance, new GuiHandler());

		MinecraftForge.EVENT_BUS.register(new PlayerLoginHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInventoryHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHandler());
		MinecraftForge.EVENT_BUS.register(new EntityHandler());
		MinecraftForge.EVENT_BUS.register(new TickHandler());
		
		
		Spells.init();
	}
	
	public void postInit(FMLPostInitializationEvent event) {}
	
	public void drawMapIconMarker(Marker marker) {}
	
	public void showDialogToPlayer(EntityLivingBase ent, NpcDialog dialog){}
	
	public void spawnTornadoSystem(World world, double x, double y, double z, int level) {}

	public void displayDamageDealt(EntityLivingBase entity) {}
	
	public void setEntityInCrosshairs() {}
	
	public ModelBiped getModelForId(int id) {return null;}

	public void renderInit(Item item, int meta, String name) {}

	public void cancelSwing() {}
	
	public void magicLeavesParticle(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {}
	
	
}
