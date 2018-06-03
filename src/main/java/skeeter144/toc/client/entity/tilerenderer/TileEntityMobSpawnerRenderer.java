package skeeter144.toc.client.entity.tilerenderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {

	BlockRendererDispatcher blockRenderer;

	@Override
	public void render(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		
		if(!Minecraft.getMinecraft().player.capabilities.isCreativeMode) 
			return;
		
		if (blockRenderer == null)
			blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher(); 
		
		BlockPos blockpos = te.getPos();
		IBlockState iblockstate = TOCBlocks.blockMobSpawner.getDefaultState();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
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

		this.renderStateModel(blockpos, iblockstate, bufferbuilder, world, true);

		bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		RenderHelper.enableStandardItemLighting();
	}

	private boolean renderStateModel(BlockPos pos, IBlockState state, BufferBuilder buffer, World p_188186_4_,
			boolean checkSides) {
		return this.blockRenderer.getBlockModelRenderer().renderModel(p_188186_4_,
				this.blockRenderer.getModelForState(state), state, pos, buffer, checkSides);
	}
}
