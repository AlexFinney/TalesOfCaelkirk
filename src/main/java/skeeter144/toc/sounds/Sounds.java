package skeeter144.toc.sounds;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.util.Reference;

public class Sounds {

	public static final SoundEvent rat_squeak = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "rat_squeak"));
	public static final SoundEvent rat_die = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "rat_die"));
	public static final SoundEvent rat_hurt = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "rat_hurt"));
	
	public static final SoundEvent man_hurt = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "man_hurt"));
	public static final SoundEvent man_grunt = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "man_grunt"));
	public static final SoundEvent man_die = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "man_die"));
	
	public static final SoundEvent goblin_snarl = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "goblin_snarl"));
	public static final SoundEvent goblin_die = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "goblin_die"));
	public static final SoundEvent goblin_laugh = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "goblin_laugh"));
	public static final SoundEvent goblin_breathe = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "goblin_breathe"));
	
	public static final SoundEvent scorpian_die = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "scorpian_die"));
	public static final SoundEvent scorpian_hurt = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "scorpian_hurt"));
	public static final SoundEvent scorpian_idle_1 = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "scorpian_idle_1"));
	public static final SoundEvent scorpian_sting = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "scorpian_sting"));
	
	public static final SoundEvent ghost_scream = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "ghost_scream"));
	public static final SoundEvent ghost_scream_angry = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "ghost_scream2"));
	
	
	public static final SoundEvent spider_forest_ambient = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "spider_forest_ambient"));
	
	public static final SoundEvent anvil_strike = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "anvil_strike"));
	public static final SoundEvent ingot_place = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "ingot_place"));
	
	public static final SoundEvent pickaxe_strike = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "pickaxe_strike"));
	
	public static final SoundEvent super_creepy_music = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "super_creepy_music"));
	public static final SoundEvent creepy_music = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "creepy_music"));
	
	public static final SoundEvent fake_wood_hit = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "fake_wood_hit"));
			
	public static final SoundEvent spell_wind_gust = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "wind_gust"));
	public static final SoundEvent spell_summon_wall = new CustomSoundEvent(new ResourceLocation(Reference.MODID, "summon_wall"));
	
	
	static SoundEvent registerSound(String name) {
		return new SoundEvent(new ResourceLocation(Reference.MODID, name));
	}
	
	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		
		Field[] fields = Sounds.class.getDeclaredFields();
		for(Field f : fields) {
			try {
				if(f.get(null) instanceof SoundEvent) {
					event.getRegistry().register((SoundEvent)f.get(null));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
