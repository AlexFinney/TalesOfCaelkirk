package skeeter144.toc.client.entity.animation;

import java.util.HashMap;
import java.util.Map;

public class Animations {
	
	public static String SCORPIAN_STING = "Scorpian_Sting";
	
	static Map<String, Animation> animations = new HashMap<String, Animation>();
	public static void add(Animation animation) {
		animations.put(animation.name, animation);
	}
	
	public static Animation get(String name) {
		return animations.get(name);
	}
}
