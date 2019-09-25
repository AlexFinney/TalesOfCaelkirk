package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CraftItemMessage{
	public static void encode(CraftItemMessage pkt, PacketBuffer buf) {}
	public static CraftItemMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final CraftItemMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	Item item;
//	int numToCraft;
//	public CraftItemMessage() {}
//	public CraftItemMessage(Item item, int numToCraft) {
//		this.item = item;
//		this.numToCraft = numToCraft;
//	}
//	
//	
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(numToCraft);
//		String s = item.getRegistryName().toString();
//		buf.writeInt(s.length());
//		for(char c : s.toCharArray()) {
//			buf.writeChar(c);
//		}
//	}
//
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		numToCraft = buf.readInt();
//		char[] chars = new char[buf.readInt()];
//		for(int i = 0; i < chars.length; ++i) {
//			chars[i] = buf.readChar();
//		}
//		this.item = Item.REGISTRY.getObject(new ResourceLocation(new String(chars)));
//	}
//
//	
//	public static class CraftItemMessageHandlerHandler<CraftItemMessage, IMessage>{
//		public IMessage onMessage(CraftItemMessage message, MessageContext ctx) {
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
//				public void run() {
//					if(message.numToCraft > 0) {
//						RecipeManager.instance().queueRecipe(ctx.getServerHandler().player.getUniqueID(),
//								RecipeManager.instance().getRecipeForItem(message.item));
//					}else {
//						RecipeManager.instance().cancelCraftingForPlayer(ctx.getServerHandler().player.getUniqueID());
//					}
//					
//				}
//			});
//			return null;
//		}
//	}
}
