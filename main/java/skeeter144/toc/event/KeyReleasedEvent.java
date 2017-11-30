package skeeter144.toc.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import skeeter144.toc.util.CustomKey;

public class KeyReleasedEvent extends Event{
	public final CustomKey key;
	public KeyReleasedEvent(CustomKey key) {
		this.key = key;
	}
}
