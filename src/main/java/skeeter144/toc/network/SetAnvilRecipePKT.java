package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.recipe.Recipe;
import skeeter144.toc.recipe.RecipeManager;

public class SetAnvilRecipePKT{
	
	public static void encode(SetAnvilRecipePKT pkt, PacketBuffer buf) {
		char[] crafted = pkt.r.crafted.getItem().getRegistryName().toString().toCharArray();
		buf.writeInt(crafted.length);
		for(int i = 0; i < crafted.length; ++i)
			buf.writeChar(crafted[i]);
		
		buf.writeInt(pkt.pos.getX());
		buf.writeInt(pkt.pos.getY());
		buf.writeInt(pkt.pos.getZ());
	}
	
	public static SetAnvilRecipePKT decode(PacketBuffer buf) {
		SetAnvilRecipePKT pkt = new SetAnvilRecipePKT();
		
		char[] crafted = new char[buf.readInt()];
		for(int i = 0; i < crafted.length; ++i)
			crafted[i] = buf.readChar();
		
		pkt.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		
		Item result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(new String(crafted)));
		pkt.r = RecipeManager.instance().getRecipeForItem(result);
		return pkt;
	}
	
	
	public static class Handler
	{
		public static void handle(final SetAnvilRecipePKT message, Supplier<NetworkEvent.Context> ctx){
			ctx.get().enqueueWork(() -> {
				if(ctx.get().getSender().world.isBlockLoaded(message.pos)) {
					TileEntityAnvil anvil = (TileEntityAnvil) ctx.get().getSender().world.getTileEntity(message.pos);
					if(anvil == null)
						return;
					if(anvil.anvilOwner != null && ctx.get().getSender().getUniqueID().equals(anvil.anvilOwner))
						anvil.setSelectedRecipe(message.r);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
	
	Recipe r;
	BlockPos pos;
	public SetAnvilRecipePKT() {}
	public SetAnvilRecipePKT(Recipe r, BlockPos pos) {
		this.r = r;
		this.pos = pos;
	}
}
