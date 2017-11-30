package skeeter144.toc.handlers;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.BlockHarvestableOre;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.client.gui.Guis;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.entity.tile.TileEntityHarvestedOre;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.tasks.TickableTask;

public class PlayerInteractHandler {
	
	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty e) {
	}
	
	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock e) {
		if(e.getEntityPlayer().getCooledAttackStrength(1) != 1) {
			e.setCanceled(true);
			cancelSwing(e.getEntityPlayer());
		}
	}
	
	@SubscribeEvent
	public void onPlayerLeftClick(AttackEntityEvent e) {
		if(e.getEntityPlayer().getCooledAttackStrength(1) != 1) {
			e.setCanceled(true);
			cancelSwing(e.getEntityPlayer());
		}
	}
	
	private void cancelSwing(EntityPlayer p) {
		TOCMain.clientTaskManager.addTask(new TickableTask(1) {
			public void tick(int worldTick) {
				p.swingProgress = 0;
				p.isSwingInProgress = false;
			}
		});
	}
	
	@SubscribeEvent
	public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
		List<Item> ingots = Arrays.asList(TOCItems.ingot_bronze, TOCItems.ingot_iron, TOCItems.ingot_steel,
				TOCItems.ingot_gold, TOCItems.ingot_mithril, TOCItems.ingot_runite, TOCItems.ingot_dragonstone);

		IBlockState bs = e.getWorld().getBlockState(e.getPos()).getBlock().getDefaultState();
		if (e.getWorld().isRemote) {
			if (bs.getBlock().equals(Blocks.NETHER_BRICK))
				e.getEntityPlayer().openGui(TOCMain.instance, Guis.SMELTING_GUI, e.getWorld(), 0, 0, 0);

			if (bs.getBlock().getDefaultState().equals(TOCBlocks.blockAnvil.getDefaultState())) {
				TileEntityAnvil anvil = (TileEntityAnvil) e.getWorld().getTileEntity(e.getPos());
				if (!ingots.contains(e.getEntityPlayer().getHeldItemMainhand().getItem()) && anvil.producedItem == null) {
					e.getEntityPlayer().openGui(TOCMain.instance, Guis.SMITHING_GUI, e.getWorld(), e.getPos().getX(),
							e.getPos().getY(), e.getPos().getZ());
				}
			}
		} else {
			TileEntityAnvil anvil = (TileEntityAnvil) e.getWorld().getTileEntity(e.getPos());
			if (bs.getBlock().getDefaultState().equals(TOCBlocks.blockAnvil.getDefaultState())) {
				if (anvil != null && anvil.producedItem != null) {
					e.getEntityPlayer().addItemStackToInventory(anvil.producedItem);
					anvil.anvilOwner = null;
					anvil.producedItem = null;
					anvil.sendUpdates();
				}
			}
		}
	}
		
	@SubscribeEvent
	public void onBlockPlace(BlockEvent.PlaceEvent e) {
		if(e.getPlacedBlock().getBlock() instanceof BlockHarvestableOre) {
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent e){
		if(e.getState().getBlock() instanceof BlockHarvestableOre) {
			e.setCanceled(true);
			if(e.getWorld().isRemote)
				return;
			if(e.getPlayer().inventory.getFirstEmptyStack() == -1) {
				e.getPlayer().sendMessage(new TextComponentString("Your inventory is too full to hold any more ore!"));
				return;
			}
				
			IBlockState oldState = e.getState();
			e.getWorld().setBlockState(e.getPos(), TOCBlocks.harvested_ore.getDefaultState());
			TileEntityHarvestedOre ore = (TileEntityHarvestedOre)e.getWorld().getTileEntity(e.getPos());
			ore.resourceBlockState = oldState;
			BlockHarvestableOre hOre = (BlockHarvestableOre)oldState.getBlock();
			ore.minSecs = hOre.minSecs;
			ore.maxSecs = hOre.maxSecs;
			if(e.getPlayer() != null) {
				e.getPlayer().addItemStackToInventory(new ItemStack(hOre.item));
				TOCMain.pm.getPlayer(e.getPlayer().getUniqueID()).levels.addExp(Levels.MINING, hOre.xpGiven);
				Network.INSTANCE.sendTo(new AddLevelXpMessage("Mining", hOre.xpGiven), (EntityPlayerMP) e.getPlayer());
			}
		}
	}
	
	
}
