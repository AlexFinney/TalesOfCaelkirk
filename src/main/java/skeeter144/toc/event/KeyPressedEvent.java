package skeeter144.toc.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import skeeter144.toc.util.CustomKey;

public class KeyPressedEvent extends Event{
	
	public final CustomKey key;
	public KeyPressedEvent(CustomKey key) {
		this.key = key;
	}
}
