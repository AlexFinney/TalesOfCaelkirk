package skeeter144.toc.client.entity.animation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.entity.model.ModelBase;
import skeeter144.toc.client.entity.renderer.AdvancedModelRenderer;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.event.AnimationEvent;

public class Animation {

	String name;
	int durationTicks;
	List<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
	public List<AnimationEvent> events = new ArrayList<AnimationEvent>();
	ModelBase model;
	
	boolean doesLoop = false;
	public Animation(String name, int durationTicks, ModelBase model, boolean loops) {
		this.name = name;
		this.durationTicks = durationTicks;
		this.model = model;
		doesLoop = loops;
	}
	
	
	
	public AnimationState render(CustomMob mob, float tickStartTime, float newTickTime) {
		float currentTick = newTickTime - tickStartTime;
		AnimationState currentState = AnimationState.NOT_RUNNING;
		
		if(currentTick > durationTicks) {
			currentState = AnimationState.FINISHED;
			if(keyFrames.get(keyFrames.size() - 1).onFrameChanged != null)
				keyFrames.get(keyFrames.size() - 1).onFrameChanged.run(mob);
			
			if(doesLoop) {
				mob.animationTicks = 0;
				currentState = AnimationState.NOT_RUNNING;
			}
			return currentState;
		}
		
		KeyFrame lastFrame = null;
		KeyFrame targetFrame = null;
		for(int i = 0; i < keyFrames.size(); ++i) {
			KeyFrame frame = keyFrames.get(i);
			if(currentTick <= frame.whenOccurs) {
				KeyFrame temp = mob.currentFrame;
				mob.currentFrame = (i > 0) ? keyFrames.get(i-1) : keyFrames.get(0);
				if(mob.currentFrame != temp && mob.currentFrame.onFrameChanged != null) {
					mob.currentFrame.onFrameChanged.run(mob);
				}
				targetFrame = frame;
				break;
			}
			lastFrame = frame;
		}
		
		if(targetFrame == null) {
			return AnimationState.FINISHED;
		}
		
		currentState = AnimationState.RUNNING;
		float ratio = 0;
		if(lastFrame != null) {
			ratio = (currentTick - lastFrame.whenOccurs) / ( targetFrame.whenOccurs - lastFrame.whenOccurs);
		}
		
		for(PartOrientation po : targetFrame.getPartOrientations()) {
			if(lastFrame == null) {
				po.part.rotationPointX = po.x;
				po.part.rotationPointY = po.y;
				po.part.rotationPointZ = po.z;
				po.part.rotateAngleX = po.rotX;
				po.part.rotateAngleY = po.rotY;
				po.part.rotateAngleZ = po.rotZ;
			}else {
				if(lastFrame.getPartOrientation(po.partName) != null) {
					po.part.rotationPointX = lastFrame.getPartOrientation(po.partName).x + (targetFrame.getPartOrientation(po.partName).x - lastFrame.getPartOrientation(po.partName).x) * ratio;
					po.part.rotationPointY = lastFrame.getPartOrientation(po.partName).y + (targetFrame.getPartOrientation(po.partName).y - lastFrame.getPartOrientation(po.partName).y) * ratio;
					po.part.rotationPointZ = lastFrame.getPartOrientation(po.partName).z + (targetFrame.getPartOrientation(po.partName).z - lastFrame.getPartOrientation(po.partName).z) * ratio;
					
					po.part.rotateAngleX = lastFrame.getPartOrientation(po.partName).rotX + (targetFrame.getPartOrientation(po.partName).rotX - lastFrame.getPartOrientation(po.partName).rotX) * ratio;
					po.part.rotateAngleY = lastFrame.getPartOrientation(po.partName).rotY + (targetFrame.getPartOrientation(po.partName).rotY - lastFrame.getPartOrientation(po.partName).rotY) * ratio;
					po.part.rotateAngleZ = lastFrame.getPartOrientation(po.partName).rotZ + (targetFrame.getPartOrientation(po.partName).rotZ - lastFrame.getPartOrientation(po.partName).rotZ) * ratio;
				}
			}
		}
		return currentState;
	}
	
	public void lockInParts() {
		for(KeyFrame frame : keyFrames) {
			for(PartOrientation po : frame.getPartOrientations()) {
				try {
					 model.getClass().getField(po.partName).setAccessible(true);
					 po.part =  (AdvancedModelRenderer)model.getClass().getField(po.partName).get(model);
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addFrame(KeyFrame frame) {
		this.keyFrames.add(frame);
	}
	
	public enum AnimationState{
		RUNNING,
		NOT_RUNNING,
		FINISHED
	}
}
