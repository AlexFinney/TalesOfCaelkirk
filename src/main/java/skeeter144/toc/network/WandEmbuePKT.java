package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.magic.Spells;

public class WandEmbuePKT{

	String playerUUID;
	int wandSlot, embueSpellId;
	public WandEmbuePKT() {}
	public WandEmbuePKT(PlayerEntity player, int wandSlot, int embueId) {
		playerUUID = player.getUniqueID().toString();
		this.wandSlot = wandSlot;
		this.embueSpellId = embueId;
	}
	
	public static void encode(WandEmbuePKT pkt, PacketBuffer buf) {
		buf.writeString(pkt.playerUUID);
		buf.writeInt(pkt.wandSlot);
		buf.writeInt(pkt.embueSpellId);
	}
	public static WandEmbuePKT decode(PacketBuffer buf) {
		WandEmbuePKT pkt = new WandEmbuePKT();
		pkt.playerUUID = buf.readString(1024);
		pkt.wandSlot = buf.readInt();
		pkt.embueSpellId = buf.readInt();
		return pkt;
	}
	public static class Handler
	{
		public static void handle(final WandEmbuePKT message, Supplier<NetworkEvent.Context> ctx){
			ctx.get().enqueueWork(new Runnable() {
				public void run() {
					PlayerEntity player =  ctx.get().getSender();
					int hand = message.wandSlot;
					
					ItemStack stack = (hand == 1 ? player.getHeldItemMainhand() : player.getHeldItemOffhand());
					
					CompoundNBT nbt = null;
					if(stack.getTag() == null) {
						nbt = new CompoundNBT();
						stack.setTag(nbt);
					}
					nbt = stack.getTag();
					nbt.putInt("embued_spell", message.embueSpellId);
					
					stack.setDisplayName(new StringTextComponent("Wand: " + Spells.getSpell(message.embueSpellId).getName()));
				}
			});
		}
	}
}
