package skeeter144.toc.event;

import net.minecraftforge.eventbus.api.Event;
import skeeter144.toc.util.CustomKey;

public class KeyReleasedEvent extends Event{
	public final CustomKey key;
	public KeyReleasedEvent(CustomKey key) {
		this.key = key;
	}
}
