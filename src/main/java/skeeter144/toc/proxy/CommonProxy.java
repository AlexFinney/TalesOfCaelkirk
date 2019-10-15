package skeeter144.toc.proxy;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.util.Reference;

@EventBusSubscriber(modid = Reference.MODID,bus = EventBusSubscriber.Bus.MOD)
public class CommonProxy 
{
//	public void preInit(FMLPreInitializationEvent event)
//	{
//		TOCBlocks.registerAllBlocks();
//		
//		MinecraftForge.EVENT_BUS.register(new TOCItems());
//		MinecraftForge.EVENT_BUS.register(new Crafting());
//		
//		MinecraftForge.EVENT_BUS.register(new Sounds());
//		
//		TOCBlocks.registerAllTileEntities();
//		TOCEntities.registerEntities();
//		
//		EntityLevels.init();
//		Woodcutting.init();
//		
//		QuestManager.initQuests();
//	}
	
//	public void init(FMLInitializationEvent event){
//		Network.INSTANCE.registerMessage(WandEmbueMessageHandler.class, WandEmbueMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(LightningBoltCastHandler.class, LightningBoltCastMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(AnimationEventMessageHandler.class, AnimationEventMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(QuestDialogResponseMessageHandler.class, QuestDialogResponseMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(ItemTransactionMessageHandler.class, ItemTransactionMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(CraftItemMessageHandler.class, CraftItemMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(SetAnvilRecipeMessageHandler.class, SetAnvilRecipeMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(UpdatePlayerFlyingMessageHandler.class, UpdatePlayerFlyingMessage.class, Network.getNextId(), Side.SERVER);
//		Network.INSTANCE.registerMessage(SetMobSpawnerSettingsMessageHandler.class, SetMobSpawnerSettingsMessage.class, Network.getNextId(), Side.SERVER);
//		
//		Network.INSTANCE.registerMessage(PlayerVitalsUpdateMessageHandler.class, PlayerVitalsUpdateMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(SetClientTOCPlayerMessageHandler.class, SetClientTOCPlayerMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(SpawnParticlesMessageHandler.class, SpawnParticlesPKT.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(HealthManaRegenUpdateHandler.class, HealthManaRegenUpdateMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(SpecialAttackCooldownMessageHandler.class, SpecialAttackCooldownMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(UpdateLevelXpMessageHandler.class, AddLevelXpMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(SpawnBlockedMessageHandler.class, SpawnBlockedMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(PlayMobAnimationMessageHandler.class, PlayMobAnimationMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(ShouldShowRegionsMessageHandler.class, ShouldShowRegionsMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(NotfyClientOfEffectMessageHandler.class, NotfyClientOfEffectMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(AdjustScreenDimPKTHandler.class, AdjustScreenDimPKT.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(PlayMusicTrackHandler.class, PlayMusicTrackMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(ShowQuestDialogMessageHandler.class, ShowEntityDialogMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(CloseGuisMessageHandler.class, CloseGuisMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(OpenShopGuiMessageHandler.class, OpenShopGuiMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(ItemCraftingQueueAddedMessageHandler.class, ItemCraftingQueueAddedMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(ItemCraftedMessageHandler.class, ItemCraftedMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(OpenBankGUIMessageHandler.class, OpenBankGUIMessage.class, Network.getNextId(), Side.CLIENT);
//		Network.INSTANCE.registerMessage(SendIconUpdateMessageHandler.class, SendIconUpdateMessage.class, Network.getNextId(), Side.CLIENT);
		
//		NetworkRegistry.INSTANCE.registerGuiHandler(TOCMain.instance, new GuiHandler());

//		MinecraftForge.EVENT_BUS.register(new PlayerLoginHandler());
//		MinecraftForge.EVENT_BUS.register(new PlayerInventoryHandler());
//		MinecraftForge.EVENT_BUS.register(new PlayerInteractHandler());
//		MinecraftForge.EVENT_BUS.register(new EntityHandler());
//		MinecraftForge.EVENT_BUS.register(new TickHandler());
//		
//		
//		Spells.init();
//	}
	
	//public void postInit(FMLPostInitializationEvent event) {}
	
	//public void drawMapIconMarker(Marker marker) {}
	
	public void showDialogToPlayer(EntityLivingBase ent, NpcDialog dialog){}
	
	public void spawnTornadoSystem(World world, double x, double y, double z, int level) {}

	public void displayDamageDealt(EntityLivingBase entity) {}
	
	public void setEntityInCrosshairs() {}
	
	public ModelBiped getModelForId(int id) {return null;}

	public void renderInit(Item item, int meta, String name) {}

	public void cancelSwing() {}
	
	public void magicLeavesParticle(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {}
	
	
}
