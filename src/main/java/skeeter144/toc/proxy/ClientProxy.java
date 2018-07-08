package skeeter144.toc.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Random;

import mapwriter.map.IconMarker;
import mapwriter.map.Marker;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.blocks.TOCClientBlockRenderers;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.client.entity.model.ModelGhost;
import skeeter144.toc.client.entity.model.ModelGiantScorpian;
import skeeter144.toc.client.entity.model.ModelGiantSpider;
import skeeter144.toc.client.entity.model.ModelGoblin;
import skeeter144.toc.client.entity.model.ModelGriffin;
import skeeter144.toc.client.entity.model.ModelHumanNpc;
import skeeter144.toc.client.entity.model.ModelPegasus;
import skeeter144.toc.client.entity.model.ModelRat;
import skeeter144.toc.client.entity.model.ModelViking;
import skeeter144.toc.client.entity.renderer.RenderCustomAbstractHorse;
import skeeter144.toc.client.entity.renderer.RenderGhost;
import skeeter144.toc.client.entity.renderer.RenderGiantScorpian;
import skeeter144.toc.client.entity.renderer.RenderGiantSpider;
import skeeter144.toc.client.entity.renderer.RenderGoblin;
import skeeter144.toc.client.entity.renderer.RenderGriffin;
import skeeter144.toc.client.entity.renderer.RenderHumanNpc;
import skeeter144.toc.client.entity.renderer.RenderPegasus;
import skeeter144.toc.client.entity.renderer.RenderRat;
import skeeter144.toc.client.entity.renderer.RenderViking;
import skeeter144.toc.client.gui.DialogGui;
import skeeter144.toc.client.gui.GuiEntityStatus;
import skeeter144.toc.client.gui.HUD;
import skeeter144.toc.client.gui.RegionsRendering;
import skeeter144.toc.entity.mob.monster.EntityGhost;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.entity.mob.monster.EntityGiantSpider;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.entity.mob.monster.EntityRat;
import skeeter144.toc.entity.mob.monster.EntityViking;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityDonkeyMount;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityMuleMount;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityVariableHorseMount;
import skeeter144.toc.entity.mob.mount.flying.EntityGriffin;
import skeeter144.toc.entity.mob.mount.flying.EntityPegasus;
import skeeter144.toc.entity.mob.passive.banker.EntityBanker;
import skeeter144.toc.entity.mob.passive.questgiver.EntityRobertCromwell;
import skeeter144.toc.entity.mob.passive.questgiver.EntityUlricWeston;
import skeeter144.toc.entity.mob.passive.shopkeeper.EntityHumanShopKeeper;
import skeeter144.toc.handlers.ItemTooltipHandler;
import skeeter144.toc.handlers.PlayerInputHandler;
import skeeter144.toc.handlers.tick.ClientTickHandler;
import skeeter144.toc.items.TOCItemsClientRegistration;
import skeeter144.toc.models.ModelVikingHelm;
import skeeter144.toc.particles.particle.BasicSpellTrailParticle;
import skeeter144.toc.particles.particle.DamageParticle;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.Util;

public class ClientProxy extends CommonProxy
{
	GuiEntityStatus entityStatusGUI;
	private Minecraft mc = Minecraft.getMinecraft();
	private Entity pointedEntity;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new TOCItemsClientRegistration());
		TOCClientBlockRenderers.registerAll();
		TOCBlocks.registerRenders();
		entityStatusGUI = new GuiEntityStatus();
		Keybindings.registerKeybinds();
	}
	
	public void init(FMLInitializationEvent event){
		super.init(event);
		registerEntityRenders();
		MinecraftForge.EVENT_BUS.register(new HUD());
		MinecraftForge.EVENT_BUS.register(new RegionsRendering());
		MinecraftForge.EVENT_BUS.register(new PlayerInputHandler());
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
		MinecraftForge.EVENT_BUS.register(entityStatusGUI);
	}
	
	@Override
	public void renderInit(Item item, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MODID + ":" + name, "inventory"));
	}
	
	public void registerEntityRenders() {
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		
		
		rm.entityRenderMap.put(EntityRobertCromwell.class, new RenderHumanNpc(rm, new ModelHumanNpc(), .5f));
		rm.entityRenderMap.put(EntityUlricWeston.class, new RenderHumanNpc(rm, new ModelHumanNpc(), .5f));
		
		rm.entityRenderMap.put(EntityBanker.class, new RenderHumanNpc(rm, new ModelHumanNpc(), .5f));
		
		rm.entityRenderMap.put(EntityHumanShopKeeper.class, new RenderHumanNpc(rm, new ModelHumanNpc(), .5f));
		
		rm.entityRenderMap.put(EntityRat.class, new RenderRat(rm, new ModelRat(), .25f));
		rm.entityRenderMap.put(EntityViking.class, new RenderViking(rm, new ModelViking(), .5f));
		rm.entityRenderMap.put(EntityGoblin.class, new RenderGoblin(rm, new ModelGoblin(), .5f));
		rm.entityRenderMap.put(EntityGiantScorpian.class, new RenderGiantScorpian(rm, new ModelGiantScorpian(), .5f));
		rm.entityRenderMap.put(EntityGiantSpider.class, new RenderGiantSpider(rm, new ModelGiantSpider(), .5f));
		rm.entityRenderMap.put(EntityGhost.class, new RenderGhost(rm, new ModelGhost(), .5f));

		rm.entityRenderMap.put(EntityMuleMount.class, new RenderCustomAbstractHorse(rm, 0.92F));
		rm.entityRenderMap.put(EntityDonkeyMount.class, new RenderCustomAbstractHorse(rm, 0.87F));
		rm.entityRenderMap.put(EntityVariableHorseMount.class, new RenderCustomAbstractHorse(rm, 1F));
		rm.entityRenderMap.put(EntityPegasus.class, new RenderPegasus(rm, new ModelPegasus(), 1F));
		rm.entityRenderMap.put(EntityGriffin.class, new RenderGriffin(rm, new ModelGriffin(), 1F));
		
	}	

	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void createNewRender(Class<? extends Entity> c, Class<? extends Render> renderClass, ModelBase model, float shadowSize) {
		RenderingRegistry.registerEntityRenderingHandler(c, new IRenderFactory() {
			public Render createRenderFor(RenderManager manager) {
				try {
					Constructor c = renderClass.getConstructor(RenderManager.class, ModelBase.class, float.class);
					return (Render)c.newInstance(manager, model, shadowSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	
	
	@Override
	public void displayDamageDealt(EntityLivingBase entity) {
		if (!entity.world.isRemote) {
			return;
		}

		int currentHealth = (int) Math.ceil(entity.getHealth());
		if(entity instanceof EntityPlayer) {
			
		}

		if (entity.getEntityData().hasKey("health")) {
			int entityHealth = ((NBTTagInt) entity.getEntityData().getTag("health")).getInt();

			if (entityHealth != currentHealth) {
				displayParticle(entity, (int) entityHealth - currentHealth);
			}
		}

		entity.getEntityData().setTag("health", new NBTTagInt(currentHealth));
	}

	
	public void displayParticle(Entity entity, int damage) {
		if (damage == 0) {
			return;
		}
		World world = entity.world;
		double motionX = world.rand.nextGaussian() * 100;
		double motionY = 0.5f;
		double motionZ = world.rand.nextGaussian() * 100;
		Particle damageIndicator = new DamageParticle(damage + "", world, entity.posX, entity.posY + entity.height, entity.posZ, motionX, motionY,
				motionZ);
		Minecraft.getMinecraft().effectRenderer.addEffect(damageIndicator);
	}
	
	public static void displayParticle(Entity entity, String str) {
		World world = entity.world;
		double motionX = world.rand.nextGaussian() * 100;
		double motionY = 0.5f;
		double motionZ = world.rand.nextGaussian() * 100;
		Particle damageIndicator = new DamageParticle(str, world, entity.posX, entity.posY + entity.height, entity.posZ, motionX, motionY,
				motionZ);
		Minecraft.getMinecraft().effectRenderer.addEffect(damageIndicator);
	}

	static int maxUpdatesToWait = 40;
	static int updatesLeft = maxUpdatesToWait;
	@Override
	public void setEntityInCrosshairs() {
		maxUpdatesToWait = 20;
		Entity e = Util.getPointedEntity(Minecraft.getMinecraft().player, 1, 50);
		if (e != null) {
			entityStatusGUI.setEntity((EntityLivingBase) e);
			updatesLeft = maxUpdatesToWait;
			
		}
		if(--updatesLeft <= 0) {
			updatesLeft = 0;
			entityStatusGUI.setEntity(null);
		}
	}

	@Override
	public void cancelSwing() {
		Minecraft.getMinecraft().player.swingProgress = 0;
		Minecraft.getMinecraft().player.swingProgressInt = 0;
		Minecraft.getMinecraft().player.isSwingInProgress = false;
	}
	
	@Override
	public void magicLeavesParticle(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		BasicSpellTrailParticle p = new BasicSpellTrailParticle(worldIn,  pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5, 
				2, 0x00FFFF, 1f, true);
		try {
			Field f = p.getClass().getSuperclass().getDeclaredField("particleGravity");
			f.setAccessible(true);
			f.set(p, 3);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Minecraft.getMinecraft().effectRenderer.addEffect(p);
	}
	
	public void showDialogToPlayer(EntityLivingBase ent, NpcDialog dialog) {
		Minecraft.getMinecraft().displayGuiScreen(new DialogGui(ent, dialog));
	}
	
	
	
	private static final ModelVikingHelm modelVikingHelm = new ModelVikingHelm();
	@Override
	public ModelBiped getModelForId(int id) {
		switch(id){
			case 0:
				return modelVikingHelm;
			default:
				return null;
		}
	}
}
