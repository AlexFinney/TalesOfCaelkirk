package skeeter144.toc.client.gui;


import net.minecraft.entity.LivingEntity;

public interface ToroHealthDisplay {
	void setEntity(LivingEntity entity);
	void setPosition(int x, int y);
	void draw();
}