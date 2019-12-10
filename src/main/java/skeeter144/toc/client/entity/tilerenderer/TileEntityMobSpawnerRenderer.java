package skeeter144.toc.client.entity.tilerenderer;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntityRenderer<TileEntityMobSpawner> {

	BlockRendererDispatcher blockRenderer;

	@Override
	public void render(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage) {
		if(!Minecraft.getInstance().player.isCreative()) 
			return;
		
			GL11.glPushMatrix();
			GlStateManager.translated(x, y, z);
			GlStateManager.enableCull();

			double cx = te.getRenderBoundingBox().minX;
			double cy = te.getRenderBoundingBox().minY;
			double maxx = te.getRenderBoundingBox().maxX;
			double maxy = te.getRenderBoundingBox().maxY;
			
			//Minecraft.getInstance()().renderEngine.bindTexture(te.layers.get(i));
	        
			Tessellator tessellator = Tessellator.getInstance();
			tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

			//NORTH
			tessellator.getBuffer().pos(0, 1, 0).tex(cx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(1, 1, 0).tex(cx + maxx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(1, 0, 0).tex(cx + maxx, cy).endVertex();
			tessellator.getBuffer().pos(0, 0, 0).tex(cx, cy).endVertex();
			
			//SOUTH
			tessellator.getBuffer().pos(1, 1, 1).tex(cx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 1, 1).tex(cx + maxx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 0, 1).tex(cx + maxx, cy).endVertex();
			tessellator.getBuffer().pos(1, 0, 1).tex(cx, cy).endVertex();
			
			//EAST
			tessellator.getBuffer().pos(1, 1, 0).tex(cx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(1, 1, 1).tex(cx + maxx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(1, 0, 1).tex(cx + maxx, cy).endVertex();
			tessellator.getBuffer().pos(1, 0, 0).tex(cx, cy).endVertex();
			
			//WEST
			tessellator.getBuffer().pos(0, 1, 1).tex(cx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 1, 0).tex(cx + maxx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 0, 0).tex(cx + maxx, cy).endVertex();
			tessellator.getBuffer().pos(0, 0, 1).tex(cx, cy).endVertex();
			
			//TOP
			tessellator.getBuffer().pos(1, 1, 0).tex(cx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 1, 0).tex(cx + maxx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 1, 1).tex(cx + maxx, cy).endVertex();
			tessellator.getBuffer().pos(1, 1, 1).tex(cx, cy).endVertex();
			
			//DOWN
			tessellator.getBuffer().pos(1, 0, 1).tex(cx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 0, 1).tex(cx + maxx, cy + maxy).endVertex();
			tessellator.getBuffer().pos(0, 0, 0).tex(cx + maxx, cy).endVertex();
			tessellator.getBuffer().pos(1, 0, 0).tex(cx, cy).endVertex();
			
			tessellator.draw();
			
		
			GlStateManager.disableCull();
			GL11.glPopMatrix();
		
		
		
		
//		super.render(te, x, y, z, partialTicks, destroyStage);
//		
//		if (blockRenderer == null)
//			blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher(); 
//		
//		BlockPos blockpos = te.getPos();
//		BlockState BlockState = TOCBlocks.blockMobSpawner.getDefaultState();
//
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder bufferbuilder = tessellator.getBuffer();
//		this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
//		RenderHelper.disableStandardItemLighting();
//		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//		GlStateManager.enableBlend();
//		GlStateManager.disableCull();
//
//		if (Minecraft.isAmbientOcclusionEnabled()) {
//			GlStateManager.shadeModel(7425);
//		} else {
//			GlStateManager.shadeModel(7424);
//		}
//
//		bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
//		bufferbuilder.setTranslation(x - (double)blockpos.getX() , y - (double)blockpos.getY(), z - (double)blockpos.getZ());
//		World world = te.getWorld();
//
//		this.renderStateModel(blockpos, BlockState, bufferbuilder, world, true);
//
//		bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
//		tessellator.draw();
//		RenderHelper.enableStandardItemLighting();
	}

	private boolean renderStateModel(BlockPos pos, BlockState state, BufferBuilder buffer, World w, boolean checkSides) {
        IBakedModel model = blockRenderer.getBlockModelShapes().getModel(state);
        IModelData data = model.getModelData(w, pos, state, ModelDataManager.getModelData(w, pos));
		
		return blockRenderer.getBlockModelRenderer().renderModel(w, model, state, pos, buffer, false, new Random(), 69, data);
	}
}
