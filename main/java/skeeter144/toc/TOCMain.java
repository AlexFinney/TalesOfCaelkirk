package skeeter144.toc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.commands.CommandRegions;
import skeeter144.toc.commands.CommandSetXp;
import skeeter144.toc.commands.CommandSummonNpc;
import skeeter144.toc.event.Events;
import skeeter144.toc.handler.PatternHandler;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.proxy.CommonProxy;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.regions.Region;
import skeeter144.toc.regions.RegionManager;
import skeeter144.toc.tasks.TaskManager;
import skeeter144.toc.util.MobManager;
import skeeter144.toc.util.PlayerManager;
import skeeter144.toc.util.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class TOCMain 
{	
	public static float VANILLA_TO_TOC_DAMAGE_CONVERSION = 3.0f;
	
	public static Random rand;
	
	@Mod.Instance
	public static TOCMain instance;
	
	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
	public static CommonProxy proxy;
	
	public static PlayerManager pm = PlayerManager.instance();
	public static MobManager mm = MobManager.instance();
	public static CombatManager cm = CombatManager.instance();
	public static RegionManager rm = new RegionManager();
	public static RecipeManager recM = RecipeManager.instance();
	
	public static TOCPlayer localPlayer;
	
	public static TaskManager clientTaskManager = new TaskManager();
	public static TaskManager serverTaskManager = new TaskManager();
	
	public static final CreativeTabs TAB_ADDITONAL_BANNERS = new CreativeTabAdditionalBanners();
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		PatternHandler.initCraftingBanners();
		rand = new Random(System.currentTimeMillis());
		proxy.preInit(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
		MinecraftForge.EVENT_BUS.register(new Events());
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{	
		try {
			File f = new File("regions_manager.bin");
			if(f.exists()) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				rm = (RegionManager)ois.readObject();
				rm.playerRegions = new HashMap<UUID, Set<Region>>();
				ois.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		event.registerServerCommand(new CommandSetXp());
		event.registerServerCommand(new CommandRegions());
		event.registerServerCommand(new CommandSummonNpc());
	}
	
	
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{	
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("regions_manager.bin")));
			oos.writeObject(rm);
			oos.close();

			QuestManager.saveQuestProgress();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
