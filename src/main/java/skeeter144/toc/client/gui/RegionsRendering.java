package skeeter144.toc.client.gui;

import java.awt.Color;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.regions.Region;
import skeeter144.toc.regions.RegionBound;

public class RegionsRendering {
	
	public static boolean doRender = false;
	
	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent  e) {
		try {
			if(doRender) {
				for(Map.Entry<String, Region> entry : TOCMain.rm.getRegions().entrySet()) {
					Region r = entry.getValue();
					for(RegionBound b : r.bounds) {
						drawBoundingBox(Minecraft.getInstance().player.getPositionVector(),
								new Vec3d(b.x1, 0, b.z1), new Vec3d(b.x2, 255, b.z2), true, 5, new Color(r.r, r.g, r.b, .8f));
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}	
	
	
	public static void drawBoundingBox(Vec3d player_pos, Vec3d posA, Vec3d posB, boolean smooth, float width, Color color) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslated(-player_pos.x, -player_pos.y, -player_pos.z);

		Color c = color;
		GL11.glColor4d(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		GL11.glLineWidth(width);
		GL11.glDepthMask(false);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

		double dx = Math.abs(posA.x - posB.x);
		double dy = Math.abs(posA.y - posB.y);
		double dz = Math.abs(posA.z - posB.z);

		//AB
		bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
		bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
		//BC
		bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
		//CD
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
		//DA
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
		bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
		//EF
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
		//FG
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
		//GH
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H
		//HE
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
		//AE
		bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
		//BF
		bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
		//CG
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
		//DH
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H

		tessellator.draw();      


		GL11.glDepthMask(true);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
}
