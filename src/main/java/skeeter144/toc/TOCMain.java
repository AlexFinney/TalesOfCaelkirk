package skeeter144.toc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.blocks.TOCClientBlockRenderers;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.config.ConfigHolder;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.npc.DialogManager;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.proxy.ClientForgeEventSubscriber;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.regions.RegionManager;
import skeeter144.toc.tasks.TaskManager;
import skeeter144.toc.util.MobManager;
import skeeter144.toc.util.PlayerManager;
import skeeter144.toc.util.Reference;

@Mod(Reference.MODID)
public class TOCMain 
{	
	public static float VANILLA_TO_TOC_DAMAGE_CONVERSION = 3.0f;
	public static final Logger LOGGER = LogManager.getLogger();
	public static Random rand = new Random(System.currentTimeMillis());
	
	public static PlayerManager pm = PlayerManager.instance();
	public static MobManager mm = MobManager.instance();
	public static CombatManager cm = CombatManager.instance();
	public static RegionManager rm = new RegionManager();
	public static RecipeManager recM = RecipeManager.instance();
	
	public static TOCPlayer localPlayer;
	
	public static TaskManager clientTaskManager = new TaskManager();
	public static TaskManager serverTaskManager = new TaskManager();
	
	public static TOCMain instance;
	
	public TOCMain() {
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
		instance = this;
		MinecraftForge.EVENT_BUS.addListener(this::tickTasks);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, TOCBlocks::registerAllBlocks);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, TOCBlocks::registerTileEntities);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, TOCEntityType::registerEntityTypes);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverStopping);
		
		DialogManager.init();
		Spells.init();
	}

	void tickTasks(TickEvent.ServerTickEvent e) {
		serverTaskManager.tickTasks();
	}
	
	
	public void clientSetup(final FMLClientSetupEvent evt) {
		TOCClientBlockRenderers.registerAll();
		ClientForgeEventSubscriber.registerClientEntityRenders();
		Keybindings.registerKeybinds();
	}
	
	public void commonSetup(final FMLCommonSetupEvent evt) {
		QuestManager.initQuests();
	}
	
	public void serverSetup(final FMLDedicatedServerSetupEvent evt) {
		QuestManager.initQuests();
	}
	
	public void serverStopping(final FMLServerStoppingEvent evt) {
		PlayerManager.instance().savePlayers();
	}
	
//	static {
//	   if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
//		      TOCBook.init();
//	   }
//	}
	
//	@EventHandler
//	public static void preInit(FMLPreInitializationEvent event)
//	{
//		rand = new Random(System.currentTimeMillis());
//		proxy.preInit(event);
//	}
	
//	@EventHandler
//	public static void init(FMLInitializationEvent event)
//	{
//		proxy.init(event);
//	}
	
//	@EventHandler
//	public static void postInit(FMLPostInitializationEvent event)
//	{
//		proxy.postInit(event);
//		MinecraftForge.EVENT_BUS.register(new Events());
//	}
	
//	@EventHandler
//	public void serverLoad(FMLServerStartingEvent event)
//	{	
//		try {
//			File f = new File("regions_manager.bin");
//			if(f.exists()) {
//				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
//				rm = (RegionManager)ois.readObject();
//				rm.playerRegions = new HashMap<UUID, Set<Region>>();
//				ois.close();
//			}
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		event.registerServerCommand(new CommandSetXp());
//		event.registerServerCommand(new CommandRegions());
//		event.registerServerCommand(new CommandSummonNpc());
//		//event.registerServerCommand(new CommandMiningMinigame());
//
//		
//		TOCEntities.initVanillaMobXp();
//		BankManager.loadInventories();
//		
//		QuestManager.loadQuestProgress();
//	}
	
	
//	@EventHandler
//	public void serverStopping(FMLServerStoppingEvent event)
//	{	
//		try {
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("regions_manager.bin")));
//			oos.writeObject(rm);
//			oos.close();
//
//			QuestManager.saveQuestProgress();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		BankManager.saveInventories();
//		pm.savePlayers();
//	}
}
