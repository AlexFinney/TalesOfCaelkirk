package skeeter144.toc.network;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.entity.mob.npc.shopkeeper.EntityShopKeeper;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.handlers.PlayerInventoryHandler.CoinsAddedToInventoryEvent;
import skeeter144.toc.util.CurrencyOperations;

public class ItemTransactionPKT{
	boolean buying;
	String itemName;
	int count;
	UUID shopKeeperId;
	public ItemTransactionPKT() {}
	public ItemTransactionPKT(boolean isBuying, String itemName, int count, UUID shopKeeperId) {
		this.buying = isBuying;
		this.itemName = itemName;
		this.count = count;
		this.shopKeeperId = shopKeeperId;
	}
	
	public static void encode(ItemTransactionPKT pkt, PacketBuffer buf) {
		buf.writeBoolean(pkt.buying);
		buf.writeInt(pkt.count);
		buf.writeLong(pkt.shopKeeperId.getMostSignificantBits());
		buf.writeLong(pkt.shopKeeperId.getLeastSignificantBits());
		buf.writeInt(pkt.itemName.toCharArray().length);
		for(int i = 0; i < pkt.itemName.toCharArray().length; ++i) {
			buf.writeByte(pkt.itemName.toCharArray()[i]);
		}
	}
	public static ItemTransactionPKT decode(PacketBuffer buf) {
		ItemTransactionPKT pkt = new ItemTransactionPKT();
		pkt.buying = buf.readBoolean();
		pkt.count = buf.readInt();
		pkt.shopKeeperId = new UUID(buf.readLong(), buf.readLong());
		byte[] chars = new byte[buf.readInt()];
		buf.readBytes(chars);
		pkt.itemName = new String(chars);
		return pkt;
	}
	public static class Handler
	{
		public static void handle(final ItemTransactionPKT message, Supplier<NetworkEvent.Context> ctx){
			ctx.get().getSender().getServerWorld().addScheduledTask(new Runnable() {
				public void run() {
					Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(message.itemName));
					if(item == null) {
						System.out.println("WARNING *********** " + message.itemName + " did not map to an item!!!1!!!!");
						return;
					}
					EntityPlayerMP pl = ctx.get().getSender();
					
					ItemStack stack = new ItemStack(item, message.count);
					EntityShopKeeper theKeeper = null;
					List<EntityShopKeeper> entities = pl.world.getEntitiesWithinAABB(EntityShopKeeper.class, new AxisAlignedBB(pl.posX - 10, pl.posY - 10, pl.posZ - 10, pl.posX + 10, pl.posY + 10, pl.posZ + 10));
					for(EntityShopKeeper e : entities) {
						if(e.getUniqueID().equals(message.shopKeeperId)) {
							theKeeper = e;
						}
					}
					if(theKeeper != null) {
						ShopData data = theKeeper.shopData;
						ItemPrice price = message.buying ? data.getSellPriceForItem(item) : data.getBuyPriceForItem(item);
						price = CurrencyOperations.multiply(price, message.count);
						if(message.buying) {
							if(CurrencyOperations.doesPlayerHaveEnoughMoney(pl, price)) {
								if(pl.inventory.addItemStackToInventory(stack))
									CurrencyOperations.subtractMoneyFromPlayer(pl, price);
								else
									pl.sendMessage(new TextComponentString("Not enough inventory space!"));
							}else {
								pl.sendMessage(new TextComponentString("Not enough money that!"));
							}
						}else {
							ItemStack stackToSell = null;
							for(ItemStack is : pl.inventory.mainInventory) {
								if(is.getItem().equals(item)) {
									stackToSell = is;
									break;
								}
							}
							if(pl.getHeldItemOffhand().getItem().equals(item)) {
								stackToSell = pl.getHeldItemOffhand();
							}
							
							if(stackToSell != null) {
								stackToSell.shrink(message.count);
								for(int i = 0; i < message.count; ++i) {
									MinecraftForge.EVENT_BUS.post(new CoinsAddedToInventoryEvent(pl, price));
								}
							}
						}
					}
				}
			});
		}
	}
}
