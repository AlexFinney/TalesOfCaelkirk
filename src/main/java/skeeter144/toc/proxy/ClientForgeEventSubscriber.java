package skeeter144.toc.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import skeeter144.toc.client.entity.model.ModelGhost;
import skeeter144.toc.client.entity.model.ModelGiantScorpian;
import skeeter144.toc.client.entity.model.ModelGiantSpider;
import skeeter144.toc.client.entity.model.ModelHumanNpc;
import skeeter144.toc.client.entity.model.ModelRat;
import skeeter144.toc.client.entity.model.ModelSiren;
import skeeter144.toc.client.entity.model.ModelViking;
import skeeter144.toc.client.entity.renderer.RenderGhost;
import skeeter144.toc.client.entity.renderer.RenderGiantScorpian;
import skeeter144.toc.client.entity.renderer.RenderGiantSpider;
import skeeter144.toc.client.entity.renderer.RenderGoblin;
import skeeter144.toc.client.entity.renderer.RenderHumanNpc;
import skeeter144.toc.client.entity.renderer.RenderRat;
import skeeter144.toc.client.entity.renderer.RenderSiren;
import skeeter144.toc.client.entity.renderer.RenderViking;
import skeeter144.toc.client.gui.DialogGui;
import skeeter144.toc.client.gui.GuiEntityStatus;
import skeeter144.toc.client.gui.HUD;
import skeeter144.toc.entity.mob.monster.EntityGhost;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.entity.mob.monster.EntityGiantSpider;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.entity.mob.monster.EntityRat;
import skeeter144.toc.entity.mob.monster.EntitySiren;
import skeeter144.toc.entity.mob.monster.EntityViking;
import skeeter144.toc.entity.mob.npc.banker.EntityBanker;
import skeeter144.toc.entity.mob.npc.questgiver.EntityEvaTeffan;
import skeeter144.toc.entity.mob.npc.questgiver.EntityKelvinWhitestone;
import skeeter144.toc.entity.mob.npc.questgiver.EntityMarlinMonroe;
import skeeter144.toc.entity.mob.npc.questgiver.EntityRobertCromwell;
import skeeter144.toc.entity.mob.npc.questgiver.EntitySeloviusKamazz;
import skeeter144.toc.entity.mob.npc.questgiver.EntityUlricWeston;
import skeeter144.toc.entity.mob.npc.shopkeeper.EntityHumanShopKeeper;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.models.ModelVikingHelm;
import skeeter144.toc.particles.particle.BasicSpellTrailParticle;
import skeeter144.toc.particles.particle.DamageParticle;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.Util;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ClientForgeEventSubscriber
{
	GuiEntityStatus entityStatusGUI;
	private Minecraft mc = Minecraft.getInstance();
	private Entity pointedEntity;
	
	public ClientForgeEventSubscriber() {
	}
	

	@SubscribeEvent
	public static void asd(final EntityJoinWorldEvent e) {
		if(e.getEntity() instanceof ClientPlayerEntity) {
			Minecraft.getInstance().ingameGUI = new HUD(Minecraft.getInstance());
		}
	}
	
//	public void preInit(FMLPreInitializationEvent event)
//	{
//		super.preInit(event);
//		MinecraftForge.EVENT_BUS.register(new TOCItemsClientRegistration());
//		TOCClientBlockRenderers.registerAll();
//		TOCBlocks.registerRenders();
//		entityStatusGUI = new GuiEntityStatus();
//		Keybindings.registerKeybinds();
//		DialogManager.init();
//	}
//	
//	public void init(FMLInitializationEvent event){
//		super.init(event);
//		registerEntityRenders();
//		MinecraftForge.EVENT_BUS.register(new HUD());
//		MinecraftForge.EVENT_BUS.register(new RegionsRendering());
//		MinecraftForge.EVENT_BUS.register(new PlayerInputHandler());
//		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
//		MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
//	}
//	
//	public void postInit(FMLPostInitializationEvent event)
//	{
//		super.postInit(event);
//		MinecraftForge.EVENT_BUS.register(entityStatusGUI);
//	}
//	
//	@Override
//	public void renderInit(Item item, int meta, String name) {
//		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MODID + ":" + name, "inventory"));
//	}
	

	public static void registerClientEntityRenders() {
		createNewRender(EntityRobertCromwell.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
		createNewRender(EntityUlricWeston.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
		createNewRender(EntityEvaTeffan.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
		createNewRender(EntityKelvinWhitestone.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
		createNewRender(EntityMarlinMonroe.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
		createNewRender(EntitySeloviusKamazz.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
		createNewRender(EntityBanker.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
		createNewRender(EntityHumanShopKeeper.class, RenderHumanNpc.class, new ModelHumanNpc(), .5f);
//
		createNewRender(EntityViking.class, RenderViking.class, new ModelViking(), .5f);
		createNewRender(EntityGhost.class, RenderGhost.class, new ModelGhost(), .5f);
		createNewRender(EntityGiantSpider.class, RenderGiantSpider.class, new ModelGiantSpider(), .5f);
		createNewRender(EntityRat.class, RenderRat.class, new ModelRat(), .5f);
		//createNewRender(EntityGoblin.class, RenderGoblin.class, new ModelGoblin(), .5f);
		createNewRender(EntityGiantScorpian.class, RenderGiantScorpian.class, new ModelGiantScorpian(), .5f);
		createNewRender(EntitySiren.class, RenderSiren.class, new ModelSiren(), .5f);

		//TODO: Update the rest of the renderers to the new cleaner way
		EntityRendererManager rm = Minecraft.getInstance().getRenderManager();
		rm.register(EntityGoblin.class, new RenderGoblin(rm));
		//		createNewRender(EntityMuleMount.class, RenderCustomAbstractHorse.class, new ModelHor(), .5f);
//		createNewRender(EntityDonkeyMount.class, RenderCustomAbstractHorse.class, new ModelViking(), .5f);
//		createNewRender(EntityVariableHorseMount.class, RenderCustomAbstractHorse.class, new ModelViking(), .5f);
//		createNewRender(EntityPegasus.class, RenderViking.class, new ModelViking(), .5f);
//		createNewRender(EntityGriffin.class, RenderViking.class, new ModelViking(), .5f);

//		EntityGhost.class, new RenderCustomLiving<EntityGhost>(rm, new ModelGhost(), .5f, new ResourceLocation(Reference.MODID, "textures/entity/ghost.png")));
//		EntityGiantSpider.class, new RenderCustomLiving<EntityGiantSpider>(rm, new ModelGiantSpider(), .5f, new ResourceLocation(Reference.MODID, "textures/entity/giant_spider.png")));
//		EntityRat.class, new RenderCustomLiving<EntityRat>(rm, new ModelRat(), .25f, new ResourceLocation(Reference.MODID, "textures/entity/rat.png")));
//		EntityGoblin.class, new RenderCustomLiving<EntityGoblin>(rm, new ModelGoblin(), .5f,  new ResourceLocation(Reference.MODID, "textures/entity/goblin.png")));
//		EntityGiantScorpian.class, new RenderCustomLiving<EntityGiantScorpian>(rm, new ModelGiantScorpian(), .5f,  new ResourceLocation(Reference.MODID, "textures/entity/giant_scorpian.png")));
//		EntitySiren.class, new RenderCustomLiving<EntitySiren>(rm, new ModelSiren(), .5f, new ResourceLocation(Reference.MODID, "textures/entity/siren.png")));
//		//EntitySalmon.class, new RenderCustomLiving<EntitySalmon>(rm, new ModelSalmon(), .5f, new ResourceLocation(Reference.MODID, "textures/entity/salmon.png")));
//		EntityMuleMount.class, new RenderCustomAbstractHorse(rm, 0.92F));
//		EntityDonkeyMount.class, new RenderCustomAbstractHorse(rm, 0.87F));
//		EntityVariableHorseMount.class, new RenderCustomAbstractHorse(rm, 1F));
//		EntityPegasus.class, new RenderPegasus(rm, new ModelPegasus(), 1F));
//		EntityGriffin.class, new RenderGriffin(rm, new ModelGriffin(), 1F));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private static void createNewRender(Class<? extends Entity> c, Class<? extends LivingRenderer> class1, Model model, float shadowSize) {
		RenderingRegistry.registerEntityRenderingHandler(c, new IRenderFactory() {
			public EntityRenderer<Entity> createRenderFor(EntityRendererManager manager) {
				try {
					Constructor cons = class1.getConstructor(EntityRendererManager.class, EntityModel.class, float.class);
					return (EntityRenderer<Entity>)cons.newInstance(manager, model, shadowSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	
	
	public void displayDamageDealt(LivingEntity entity) {
		if (!entity.world.isRemote) {
			return;
		}

		int currentHealth = (int) Math.ceil(entity.getHealth());
		if(entity instanceof PlayerEntity) {
			
		}

		
		displayParticle(entity, (int) currentHealth);
	}
	
	public void displayParticle(Entity entity, int damage) {
		if (damage == 0) {
			return;
		}
		World world = entity.world;
		double motionX = world.rand.nextGaussian() * 100;
		double motionY = 0.5f;
		double motionZ = world.rand.nextGaussian() * 100;
		Particle damageIndicator = new DamageParticle(damage + "", world, entity.posX, entity.posY + entity.getHeight(), entity.posZ, motionX, motionY,
				motionZ);
		Minecraft.getInstance().particles.addEffect(damageIndicator);
	}
	
	public static void displayParticle(Entity entity, String str) {
		World world = entity.world;
		double motionX = world.rand.nextGaussian() * 100;
		double motionY = 0.5f;
		double motionZ = world.rand.nextGaussian() * 100;
		Particle damageIndicator = new DamageParticle(str, world, entity.posX, entity.posY + entity.getHeight(), entity.posZ, motionX, motionY, motionZ);
		Minecraft.getInstance().particles.addEffect(damageIndicator);
	}

	static int maxUpdatesToWait = 40;
	static int updatesLeft = maxUpdatesToWait;
	public void setEntityInCrosshairs() {
		maxUpdatesToWait = 20;
		Entity e = Util.getPointedEntity(Minecraft.getInstance().player, 1, 50);
		if (e != null) {
			entityStatusGUI.setEntity((LivingEntity) e);
			updatesLeft = maxUpdatesToWait;
			
		}
		if(--updatesLeft <= 0) {
			updatesLeft = 0;
			entityStatusGUI.setEntity(null);
		}
	}

	public void cancelSwing() {
		Minecraft.getInstance().player.swingProgress = 0;
		Minecraft.getInstance().player.swingProgressInt = 0;
		Minecraft.getInstance().player.isSwingInProgress = false;
	}
	
	public void magicLeavesParticle(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		BasicSpellTrailParticle p = new BasicSpellTrailParticle(worldIn,  pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5, 
				2, 0x00FFFF, 1f, true);
		try {
			Field f = p.getClass().getSuperclass().getDeclaredField("particleGravity");
			f.setAccessible(true);
			f.set(p, 3);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Minecraft.getInstance().particles.addEffect(p);
	}
	
	public void showDialogToPlayer(LivingEntity ent, NpcDialog dialog) {
		Minecraft.getInstance().displayGuiScreen(new DialogGui(ent, dialog));
	}
	
	
	
	private static final ModelVikingHelm modelVikingHelm = new ModelVikingHelm();
	public BipedModel<LivingEntity> getModelForId(int id) {
		switch(id){
			case 0:
				return modelVikingHelm;
			default:
				return null;
		}
	}
}
