package skeeter144.toc.event;

public class AnimationEvent {
	public final int whenOccurs;
	public final String functionName;
	
	public AnimationEvent(int when, String name) {
		this.whenOccurs = when;
		this.functionName = name;
	}
	
}
