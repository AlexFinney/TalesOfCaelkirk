package skeeter144.toc.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.util.math.MathHelper;

public class EntityDisplay implements ToroHealthDisplay {
	private static final int RENDER_HEIGHT = 30;
	private static final int RENDER_WIDTH = 18;
	private static final int PADDING = 2;
	private static final int WIDTH = 40;
	private static final int HEIGHT = WIDTH;

	private int y;

	private float glX;
	private float glY;

	private LivingEntity entity;
	private Entity leashedToEntity;
	private float prevYawOffset;
	private float prevYaw;
	private float prevPitch;
	private float prevYawHead;
	private float prevPrevYahHead;
	private int scale = 1;

	public EntityDisplay(Minecraft mc) {
	}

	@Override
	public void setPosition(int x, int y) {
		this.y = y;
		glX = (float) x + WIDTH / 2;
		updateScale();
	}

	@Override
	public void setEntity(LivingEntity entity) {
		this.entity = entity;
		updateScale();
	}

	@Override
	public void draw() {
		try {
			pushEntityLeasedTo();
			pushEntityRotations();
			glDraw();
			popEntityRotations();
			popEntityLeasedTo();
		} catch (Throwable ignore) {
		}
	}

	private void updateScale() {
		if (entity == null) {
			glY = (float) y + HEIGHT - PADDING;
			return;
		}
		int scaleY = MathHelper.ceil(RENDER_HEIGHT / entity.getHeight());
		int scaleX = MathHelper.ceil(RENDER_WIDTH / entity.getWidth());
		scale = Math.min(scaleX, scaleY);
		glY = (float) y + (HEIGHT / 2 + RENDER_HEIGHT / 2);
		if (entity instanceof GhastEntity) {
			glY -= 10;
		}
	}

	private void glDraw() {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();

		GlStateManager.translatef(glX, glY, 50.0F);
		GlStateManager.scalef((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(-100.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(0.0f, 1.0F, 0.0F, 0.0F);

		RenderHelper.enableStandardItemLighting();

		GlStateManager.translatef(0.0F, 0.0F, 0.0F);
		EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
//		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
//		rendermanager.renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);

		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		//GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
//		GlStateManager.disableTexture2D();
	//	GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	private void popEntityLeasedTo() {
		if (entity instanceof MobEntity && leashedToEntity != null) {
			((MobEntity) entity).setLeashHolder(leashedToEntity, false);
			leashedToEntity = null;
		}
	}

	private void pushEntityLeasedTo() {
		if (entity instanceof MobEntity) {
			if (((MobEntity) entity).getLeashed()) {
				leashedToEntity = ((MobEntity) entity).getLeashHolder();
				((MobEntity) entity).setLeashHolder(null, false);
			}
		}
	}

	private void popEntityRotations() {
		entity.renderYawOffset = prevYawOffset;
		entity.rotationYaw = prevYaw;
		entity.rotationPitch = prevPitch;
		entity.rotationYawHead = prevYawHead;
		entity.prevRotationYawHead = prevPrevYahHead;
	}

	private void pushEntityRotations() {
		prevYawOffset = entity.renderYawOffset;
		prevYaw = entity.rotationYaw;
		prevPitch = entity.rotationPitch;
		prevYawHead = entity.rotationYawHead;
		prevPrevYahHead = entity.prevRotationYawHead;
		entity.renderYawOffset = 0.0f;
		entity.rotationYaw = 0.0f;
		entity.rotationPitch = 0.0f;
		entity.rotationYawHead = 0.0f;
		entity.prevRotationYawHead = 0.0f;
	}
}