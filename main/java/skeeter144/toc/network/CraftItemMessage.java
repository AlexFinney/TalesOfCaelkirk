package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.recipe.RecipeManager;

public class CraftItemMessage implements IMessage{

	Item item;
	int numToCraft;
	public CraftItemMessage() {}
	public CraftItemMessage(Item item, int numToCraft) {
		this.item = item;
		this.numToCraft = numToCraft;
		System.out.println("craft " + item.getRegistryName());
	}
	
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(numToCraft);
		String s = item.getRegistryName().toString();
		buf.writeInt(s.length());
		for(char c : s.toCharArray()) {
			buf.writeChar(c);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		numToCraft = buf.readInt();
		char[] chars = new char[buf.readInt()];
		for(int i = 0; i < chars.length; ++i) {
			chars[i] = buf.readChar();
		}
		this.item = Item.REGISTRY.getObject(new ResourceLocation(new String(chars)));
	}

	
	public static class CraftItemMessageHandler implements IMessageHandler<CraftItemMessage, IMessage>{
		public IMessage onMessage(CraftItemMessage message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
				public void run() {
					if(message.numToCraft > 0) {
						RecipeManager.instance().queueRecipe(ctx.getServerHandler().player.getUniqueID(),
								RecipeManager.instance().getRecipeForItem(message.item));
					}else {
						RecipeManager.instance().cancelCraftingForPlayer(ctx.getServerHandler().player.getUniqueID());
					}
					
				}
			});
			return null;
		}
	}
}
