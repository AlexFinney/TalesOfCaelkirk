package skeeter144.toc.client.entity.animation;

import java.util.ArrayList;
import java.util.List;

import skeeter144.toc.util.CustomRunnable;

public class KeyFrame {

	public final int whenOccurs;
	public final CustomRunnable onFrameChanged;
	List<PartOrientation> orientations = new ArrayList<PartOrientation>();
	public KeyFrame(int whenOccurs, CustomRunnable onFrameChanged) {
		this.whenOccurs = whenOccurs;
		this.onFrameChanged = onFrameChanged;
	}
	
	
	public void addPartOrientation(PartOrientation p) {
		orientations.add(p);
	}
	
	public List<PartOrientation> getPartOrientations(){
		return orientations;
	}
	
	public PartOrientation getPartOrientation(String partName) {
		for(PartOrientation po : orientations) {
			if(po.partName.equals(partName))
				return po;
		}
		return null;
	}
}
