package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.recipe.Recipe;
import skeeter144.toc.recipe.RecipeManager;

public class SetAnvilRecipeMessage implements IMessage{
	
	Recipe r;
	BlockPos pos;
	public SetAnvilRecipeMessage() {}
	public SetAnvilRecipeMessage(Recipe r, BlockPos pos) {
		this.r = r;
		this.pos = pos;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		char[] crafted = r.crafted.getItem().getRegistryName().toString().toCharArray();
		buf.writeInt(crafted.length);
		for(int i = 0; i < crafted.length; ++i)
			buf.writeChar(crafted[i]);
		
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		char[] crafted = new char[buf.readInt()];
		for(int i = 0; i < crafted.length; ++i)
			crafted[i] = buf.readChar();
		
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		
		Item result = Item.REGISTRY.getObject(new ResourceLocation(new String(crafted)));
		r = RecipeManager.instance().getRecipeForItem(result);
	}
	
	
	public static class SetAnvilRecipeMessageHandler implements IMessageHandler<SetAnvilRecipeMessage, IMessage>{
		@Override
		public IMessage onMessage(SetAnvilRecipeMessage message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
				public void run() {
					if(ctx.getServerHandler().player.world.isBlockLoaded(message.pos)) {
						TileEntityAnvil anvil = (TileEntityAnvil) ctx.getServerHandler().player.world.getTileEntity(message.pos);
						if(anvil == null)
							return;
						if(anvil.anvilOwner != null && ctx.getServerHandler().player.getPersistentID().equals(anvil.anvilOwner))
							anvil.setSelectedRecipe(message.r);
					}
				}
			});
			return null;
		}
	}
}
