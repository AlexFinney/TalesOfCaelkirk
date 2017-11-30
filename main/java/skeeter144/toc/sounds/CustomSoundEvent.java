package skeeter144.toc.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class CustomSoundEvent extends SoundEvent{

	public CustomSoundEvent(ResourceLocation soundNameIn) {
		super(soundNameIn);
		this.setRegistryName(soundNameIn);
	}

}
