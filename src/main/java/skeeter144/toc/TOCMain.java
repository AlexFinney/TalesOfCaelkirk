package skeeter144.toc;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.proxy.ClientProxy;
import skeeter144.toc.proxy.CommonProxy;
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
	public static Random rand;
	public static TOCMain INSTANCE;
	
//	@Mod.Instance(Reference.MODID)
//	public static TOCMain instance;
	
//	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
//	public static CommonProxy proxy;
	
	public static PlayerManager pm = PlayerManager.instance();
	public static MobManager mm = MobManager.instance();
	public static CombatManager cm = CombatManager.instance();
	public static RegionManager rm = new RegionManager();
	public static RecipeManager recM = RecipeManager.instance();
	
	public static TOCPlayer localPlayer;
	
	public static TaskManager clientTaskManager = new TaskManager();
	public static TaskManager serverTaskManager = new TaskManager();
	
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public TOCMain() {
		INSTANCE = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
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
	
	private void setup(final FMLCommonSetupEvent event) {
		System.out.println("common");
    }

    private void client(final FMLClientSetupEvent event) {
    	System.out.println("client");
    }
    
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
