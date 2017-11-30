package skeeter144.toc.entity.tile;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.recipe.Recipe;

public class TileEntityAnvil extends TileEntity {
	
	public Item ingot;
	public ItemStack producedItem;
	public Recipe selectedRecipe;
	public int addedIngots;
	public UUID anvilOwner;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setString("ingotType", ingot != null ? ingot.getRegistryName().toString() : "");
		compound.setString("producedItem", producedItem != null ? producedItem.getItem().getRegistryName().toString() : "");
		compound.setInteger("producedItemCount", producedItem != null ? producedItem.getCount() : 0);
		compound.setInteger("addedIngots", addedIngots);
		compound.setLong("uuid1", anvilOwner != null ? anvilOwner.getMostSignificantBits() : 0);
		compound.setLong("uuid2", anvilOwner != null ? anvilOwner.getLeastSignificantBits() : 0);
		return super.writeToNBT(compound);
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		String ingotType = compound.getString("ingotType");
		if(ingotType != "")
			ingot = Item.REGISTRY.getObject(new ResourceLocation(ingotType));
		
		String producedItemtype = compound.getString("producedItem");
		if(producedItemtype != "")
			producedItem = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(producedItemtype)), compound.getInteger("producedItemCount"));
		else
			producedItem = null;
		
		addedIngots = compound.getInteger("addedIngots");
		anvilOwner = new UUID(compound.getLong("uuid1"), compound.getLong("uuid2")); 
	}
	
	public void hammerStruck(EntityPlayer striker) {
		if(!this.world.isRemote) {
			if(strikesLeft > 0 && selectedRecipe != null && ingot != null && ingot.equals(selectedRecipe.components[0].getItem()) 
					&& addedIngots >= selectedRecipe.components[0].getCount()) {
				if(--strikesLeft <= 0) {
					strikesLeft = maxStrikes;
					if(selectedRecipe != null)
						producedItem = selectedRecipe.crafted;
					striker.addItemStackToInventory(new ItemStack(ingot, selectedRecipe.components[0].getCount() - addedIngots));
					TOCMain.pm.getPlayer(striker.getPersistentID()).levels.addExp(selectedRecipe.level, selectedRecipe.xp);
					Network.INSTANCE.sendTo(new AddLevelXpMessage(selectedRecipe.level.name().toString(), selectedRecipe.xp), (EntityPlayerMP) striker);
					selectedRecipe = null;
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
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
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
		System.out.println("set " + strikesLeft);
		this.selectedRecipe = r;
	}
}
