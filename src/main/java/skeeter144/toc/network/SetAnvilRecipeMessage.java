package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.recipe.Recipe;

public class SetAnvilRecipeMessage{
	
	public static void encode(SetAnvilRecipeMessage pkt, PacketBuffer buf) {}
	public static SetAnvilRecipeMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final SetAnvilRecipeMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	Recipe r;
	BlockPos pos;
	public SetAnvilRecipeMessage() {}
	public SetAnvilRecipeMessage(Recipe r, BlockPos pos) {
		this.r = r;
		this.pos = pos;
	}
//	
//	@Override
//	public void toBytes(ByteBuf buf) {
//		char[] crafted = r.crafted.getItem().getRegistryName().toString().toCharArray();
//		buf.writeInt(crafted.length);
//		for(int i = 0; i < crafted.length; ++i)
//			buf.writeChar(crafted[i]);
//		
//		buf.writeInt(pos.getX());
//		buf.writeInt(pos.getY());
//		buf.writeInt(pos.getZ());
//	}
//	
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		char[] crafted = new char[buf.readInt()];
//		for(int i = 0; i < crafted.length; ++i)
//			crafted[i] = buf.readChar();
//		
//		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
//		
//		Item result = Item.REGISTRY.getObject(new ResourceLocation(new String(crafted)));
//		r = RecipeManager.instance().getRecipeForItem(result);
//	}
//	
//	
//	public static class SetAnvilRecipeMessageHandlerHandler<SetAnvilRecipeMessage, IMessage>{
//		@Override
//		public IMessage onMessage(SetAnvilRecipeMessage message, MessageContext ctx) {
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
//				public void run() {
//					if(ctx.getServerHandler().player.world.isBlockLoaded(message.pos)) {
//						TileEntityAnvil anvil = (TileEntityAnvil) ctx.getServerHandler().player.world.getTileEntity(message.pos);
//						if(anvil == null)
//							return;
//						if(anvil.anvilOwner != null && ctx.getServerHandler().player.getUniqueID().equals(anvil.anvilOwner))
//							anvil.setSelectedRecipe(message.r);
//					}
//				}
//			});
//			return null;
//		}
//	}
}
