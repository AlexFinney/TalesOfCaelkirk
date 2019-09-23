package skeeter144.toc.client.entity.animation;

import net.minecraft.client.renderer.entity.model.ModelRenderer;
import skeeter144.toc.util.Util;

public class PartOrientation {
	public final float x, y, z, rotX, rotY, rotZ;
	public final String partName;
	public ModelRenderer part;
	public PartOrientation(String partName, ModelRenderer part, float deltaX,    float deltaY,    float deltaZ, 
																		float deltaRotX, float deltaRotY, float deltaRotZ) {
		
		deltaRotX = Util.toRad(deltaRotX);
		deltaRotY = Util.toRad(deltaRotY);
		deltaRotZ = Util.toRad(deltaRotZ);
		this.partName = partName;
		
		this.x = deltaX + part.offsetX;
		this.y = deltaY + part.offsetY;
		this.z = deltaZ + part.offsetZ;
		this.rotX = deltaRotX + part.rotateAngleX;
		this.rotY = deltaRotY + part.rotateAngleY;
		this.rotZ = deltaRotZ + part.rotateAngleZ;
	}
	
	public PartOrientation(ModelRenderer m, String name) {
		x = m.offsetX;
		y = m.offsetY;
		z = m.offsetZ;
		rotX = m.rotateAngleX;
		rotY = m.rotateAngleY;
		rotZ = m.rotateAngleZ;
		part = m;
		partName = name;
	}
	
	
}
