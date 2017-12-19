package skeeter144.toc.client.entity.animation;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.model.ModelRenderer;
import skeeter144.toc.util.Util;

public class PartOrientation {
	public final float x, y, z, rotX, rotY, rotZ;
	public final String partName;
	public AdvancedModelRenderer part;
	public PartOrientation(String partName, AdvancedModelRenderer part, float deltaX,    float deltaY,    float deltaZ, 
																		float deltaRotX, float deltaRotY, float deltaRotZ) {
		
		deltaRotX = Util.toRad(deltaRotX);
		deltaRotY = Util.toRad(deltaRotY);
		deltaRotZ = Util.toRad(deltaRotZ);
		this.partName = partName;
		
		this.x = deltaX + part.defaultPositionX;
		this.y = deltaY + part.defaultPositionY;
		this.z = deltaZ + part.defaultPositionZ;
		this.rotX = deltaRotX + part.defaultRotationX;
		this.rotY = deltaRotY + part.defaultRotationY;
		this.rotZ = deltaRotZ + part.defaultRotationZ;
	}
	
	public PartOrientation(AdvancedModelRenderer m, String name) {
		x = m.defaultPositionX;
		y = m.defaultPositionY;
		z = m.defaultPositionZ;
		rotX = m.defaultRotationX;
		rotY = m.defaultRotationY;
		rotZ = m.defaultRotationZ;
		part = m;
		partName = name;
	}
	
	
}
