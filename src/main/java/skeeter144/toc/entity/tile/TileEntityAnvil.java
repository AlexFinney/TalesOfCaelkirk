package skeeter144.toc.entity.tile;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.recipe.Recipe;

public class TileEntityAnvil extends TileEntity {
	public TileEntityAnvil() {
		super(TOCBlocks.te_anvil);
	}
	public TileEntityAnvil(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public Item ingot;
	public ItemStack producedItem;
	public Recipe selectedRecipe;
	public int addedIngots;
	public UUID anvilOwner;
	
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("ingotType", ingot != null ? ingot.getRegistryName().toString() : "");
		compound.setString("producedItem", producedItem != null ? producedItem.getItem().getRegistryName().toString() : "");
		compound.setInt("producedItemCount", producedItem != null ? producedItem.getCount() : 0);
		compound.setInt("addedIngots", addedIngots);
		compound.setLong("uuid1", anvilOwner != null ? anvilOwner.getMostSignificantBits() : 0);
		compound.setLong("uuid2", anvilOwner != null ? anvilOwner.getLeastSignificantBits() : 0);
		return compound;
	}
	
	
	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		String ingotType = compound.getString("ingotType");
		if(ingotType != "")
			ingot = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ingotType));
		
		String producedItemtype = compound.getString("producedItem");
		if(producedItemtype != "")
			producedItem = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(producedItemtype)), compound.getInt("producedItemCount"));
		else
			producedItem = null;
		
		addedIngots = compound.getInt("addedIngots");
		anvilOwner = new UUID(compound.getLong("uuid1"), compound.getLong("uuid2")); 
	}
	
	public void hammerStruck(EntityPlayer striker) {
		if(!this.world.isRemote) {
			if(strikesLeft > 0 && selectedRecipe != null && ingot != null && ingot.equals(selectedRecipe.components[0].getItem()) 
					&& addedIngots >= selectedRecipe.components[0].getCount()) {
				if(--strikesLeft <= 0) {
					strikesLeft = maxStrikes;
					
					if(selectedRecipe != null)
						producedItem = new ItemStack(selectedRecipe.crafted.getItem(), selectedRecipe.crafted.getCount());
					
					striker.addItemStackToInventory(new ItemStack(ingot, addedIngots - selectedRecipe.components[0].getCount()));
					
					TOCMain.pm.getPlayer(striker).levels.addExp(selectedRecipe.level, selectedRecipe.xp);
					
					Network.INSTANCE.sendTo(new AddLevelXpMessage(selectedRecipe.level.name().toString(), selectedRecipe.xp), (EntityPlayerMP) striker);
					
					addedIngots = 0;
					ingot = null;
					sendUpdates();
				}
			}
		}
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return serializeNBT();
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		markDirty();
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	final int maxStrikes = 12;
	int strikesLeft = maxStrikes;
	public void setSelectedRecipe(Recipe r) {
		if(selectedRecipe != null)
			strikesLeft = r.components[0].getCount() * 3;
		this.selectedRecipe = r;
	}
}
