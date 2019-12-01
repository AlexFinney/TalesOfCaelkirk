package skeeter144.toc.client.entity.animation;

import net.minecraft.client.renderer.entity.model.RendererModel;
import skeeter144.toc.client.entity.renderer.AdvancedModelRenderer;
import skeeter144.toc.util.Util;

public class PartOrientation {
	public final float x, y, z, rotX, rotY, rotZ;
	public final String partName;
	public RendererModel part;
	public PartOrientation(String partName, AdvancedModelRenderer part, float deltaX,    float deltaY,    float deltaZ, 
																		float deltaRotX, float deltaRotY, float deltaRotZ) {
		
		deltaRotX = Util.toRad(deltaRotX);
		deltaRotY = Util.toRad(deltaRotY);
		deltaRotZ = Util.toRad(deltaRotZ);
		this.partName = partName;
		
		this.x = deltaX + part.defaultOffsetX + part.offsetX;
		this.y = deltaY + part.defaultOffsetY + part.offsetY;
		this.z = deltaZ + part.defaultOffsetZ + part.offsetZ;
		
		this.rotX = deltaRotX + part.rotateAngleX;
		this.rotY = deltaRotY + part.rotateAngleY;
		this.rotZ = deltaRotZ + part.rotateAngleZ;
	}
	
	public PartOrientation(RendererModel m, String name) {
		x = m.rotationPointX + m.offsetX;
		y = m.rotationPointY + m.offsetY;
		z = m.rotationPointZ + m.offsetZ;
		rotX = m.rotateAngleX;
		rotY = m.rotateAngleY;
		rotZ = m.rotateAngleZ;
		part = m;
		partName = name;
	}
	
	
}
