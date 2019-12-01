package skeeter144.toc.client.entity.tilerenderer;

import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntityRenderer<TileEntityMobSpawner> {

	BlockRendererDispatcher blockRenderer;

	@Override
	public void render(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage) {
		
		if(!Minecraft.getInstance().player.isCreative()) 
			return;
		
		if (blockRenderer == null)
			blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher(); 
		
		BlockPos blockpos = te.getPos();
		BlockState BlockState = TOCBlocks.blockMobSpawner.getDefaultState();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		//this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();

		if (Minecraft.isAmbientOcclusionEnabled()) {
			GlStateManager.shadeModel(7425);
		} else {
			GlStateManager.shadeModel(7424);
		}

		bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
		bufferbuilder.setTranslation(x - (double)blockpos.getX() , y - (double)blockpos.getY(), z - (double)blockpos.getZ());
		World world = te.getWorld();

		this.renderStateModel(blockpos, BlockState, bufferbuilder, world, true);

		bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		RenderHelper.enableStandardItemLighting();
	}

	private boolean renderStateModel(BlockPos pos, BlockState state, BufferBuilder buffer, World w, boolean checkSides) {
        IBakedModel model = blockRenderer.getBlockModelShapes().getModel(state);
        IModelData data = model.getModelData(w, pos, state, ModelDataManager.getModelData(w, pos));
		
		return blockRenderer.getBlockModelRenderer().renderModel(w, model, state, pos, buffer, false, new Random(), 69, data);
	}
}
