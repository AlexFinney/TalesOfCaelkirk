package skeeter144.toc.client.entity.tilerenderer;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {
	@Override
	public void render(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
//		if (Minecraft.getMinecraft().player.capabilities.isCreativeMode) {
//			return;
//		}

		ItemStack stack = new ItemStack(Items.DIAMOND);
		IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);

		IBlockState bs = te.getWorld().getBlockState(te.getPos());

		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(.5, 1.05, .5);

		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();

		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
