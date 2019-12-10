package skeeter144.toc.client.entity.tilerenderer;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.command.TimerCallbackSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntityRenderer<TileEntityMobSpawner> {

	BlockRendererDispatcher blockRenderer;

	@Override
	public void render(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage) {
		if(!Minecraft.getInstance().player.isCreative()) 
			return;
		ItemStack stack = new ItemStack(Items.ZOMBIE_HEAD);
		IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(stack);
		
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableBlend();
		GlStateManager.translated(x, y, z);
		GlStateManager.translated(.5, 1, .5);
		double scale = Math.abs(Math.sin((te.ticksAlive % 40) / 40.0 * 2 * Math.PI) / 4 + 1f);
		GlStateManager.scaled(scale, scale, scale);
		double rotation = (te.ticksAlive % 360) * 5;
		GlStateManager.rotated(rotation, 0, 1, 0);
		
		
		Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}
}
