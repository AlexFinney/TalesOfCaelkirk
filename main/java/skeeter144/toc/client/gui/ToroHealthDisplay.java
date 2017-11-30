package skeeter144.toc.client.gui;


import net.minecraft.entity.EntityLivingBase;

public interface ToroHealthDisplay {
	void setEntity(EntityLivingBase entity);
	void setPosition(int x, int y);
	void draw();
}