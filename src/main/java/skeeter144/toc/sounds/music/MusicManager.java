package skeeter144.toc.sounds.music;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundEvent;
import skeeter144.toc.sounds.Sounds;

public class MusicManager {
	
	public static class SoundCategory{
		public static final int SUPER_CREEPY = 0;
		public static final int CREEPY = 1;
	}
	
	
	
	public static void playMusicTrack(int id) {
		SoundEvent track = null;
		switch(id) {
			case SoundCategory.SUPER_CREEPY:
				track = Sounds.super_creepy_music;
				break;
			case SoundCategory.CREEPY:
				track = Sounds.creepy_music;
				break;
		}
		Minecraft.getInstance().getSoundHandler().playSound(PositionedSoundRecord.getRecord(track, 1, 1));
	}

}
