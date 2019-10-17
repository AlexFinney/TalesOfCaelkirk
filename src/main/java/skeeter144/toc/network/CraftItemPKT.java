package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.recipe.RecipeManager;

public class CraftItemPKT {

	Item item;
	int numToCraft;
	public CraftItemPKT() {}

	public CraftItemPKT(Item item, int numToCraft) {
		this.item = item;
		this.numToCraft = numToCraft;
	}

	public static void encode(CraftItemPKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.numToCraft);
		String s = pkt.item.getRegistryName().toString();
		buf.writeInt(s.length());
		for(char c : s.toCharArray()) {
			buf.writeChar(c);
		}
	}

	public static CraftItemPKT decode(PacketBuffer buf) {
		CraftItemPKT pkt = new CraftItemPKT();
		pkt.numToCraft = buf.readInt();
		char[] chars = new char[buf.readInt()];
		for(int i = 0; i < chars.length; ++i) {
			chars[i] = buf.readChar();
		}
		
		pkt.item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(new String(chars)));
		return pkt;
	}

	public static class Handler {
		public static void handle(final CraftItemPKT message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				if (message.numToCraft > 0) {
					RecipeManager.instance().queueRecipe(ctx.get().getSender().getUniqueID(),
							RecipeManager.instance().getRecipeForItem(message.item), message.numToCraft);
				} else {
					RecipeManager.instance().cancelCraftingForPlayer(ctx.get().getSender().getUniqueID());
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
