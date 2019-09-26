package skeeter144.toc.event;

import net.minecraftforge.eventbus.api.Event;
import skeeter144.toc.util.CustomKey;

public class KeyPressedEvent extends Event{
	
	public final CustomKey key;
	public KeyPressedEvent(CustomKey key) {
		this.key = key;
	}
}
