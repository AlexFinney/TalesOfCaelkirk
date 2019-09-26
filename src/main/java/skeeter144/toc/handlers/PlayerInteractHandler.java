package skeeter144.toc.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.log.CustomBlockLog;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.entity.tile.TileEntityHarvestedTree;
import skeeter144.toc.handlers.PlayerInventoryHandler.ItemAddedToInventoryEvent;
import skeeter144.toc.items.tools.TOCAxe;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.skills.Woodcutting;
import skeeter144.toc.tasks.TickableTask;

public class PlayerInteractHandler {
	
	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty e) {
	}
	
	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock e) {
		if(e.getWorld().isRemote)
			return;
		
		if(e.getEntityPlayer().abilities.isCreativeMode)
			return;
		
		if(e.getEntityPlayer().getCooledAttackStrength(1) != 1) {
			e.setCanceled(true);
			return;
		}
		
		Block b = e.getWorld().getBlockState(e.getPos()).getBlock();
		if(b instanceof CustomBlockLog) {
			if(e.getEntityPlayer().getHeldItemMainhand().getItem() instanceof TOCAxe)
				processLogChopped(e);
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
		TOCMain.clientTaskManager.addTask(new TickableTask(4) {
			public void tick(int worldTick) {
				p.swingProgress = 0;
				p.isSwingInProgress = false;
				//TOCMain.proxy.cancelSwing();
			}
		});
	}
	
	@SubscribeEvent
	public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
		List<Item> ingots = new ArrayList<Item>(); // Arrays.asList(TOCItems.ingot_bronze, TOCItems.ingot_iron, TOCItems.ingot_steel,
				//TOCItems.ingot_gold, TOCItems.ingot_mithril, TOCItems.ingot_runite, TOCItems.ingot_dragonstone);

		IBlockState bs = e.getWorld().getBlockState(e.getPos()).getBlock().getDefaultState();
		if (e.getWorld().isRemote) {
			//if (bs.getBlock().equals(Blocks.NETHER_BRICKS))
				//e.getEntityPlayer().openGui(TOCMain.instance, Guis.SMELTING_GUI, e.getWorld(), 0, 0, 0);
//TODO
//			if (bs.getBlock().getDefaultState().equals(TOCBlocks.blockAnvil.getDefaultState())) {
//				TileEntityAnvil anvil = (TileEntityAnvil) e.getWorld().getTileEntity(e.getPos());
//				if (!ingots.contains(e.getEntityPlayer().getHeldItemMainhand().getItem()) && anvil.producedItem == null) {
//				//	e.getEntityPlayer().openGui(TOCMain.instance, Guis.SMITHING_GUI, e.getWorld(), e.getPos().getX(),
//				//			e.getPos().getY(), e.getPos().getZ());
//				}
//			}
		} else {
			if(!(e.getWorld().getTileEntity(e.getPos()) instanceof TileEntityAnvil))
				return;
			
			TileEntityAnvil anvil = (TileEntityAnvil)e.getWorld().getTileEntity(e.getPos());
//			if (bs.getBlock().getDefaultState().equals(TOCBlocks.blockAnvil.getDefaultState())) {
//				if (anvil != null && anvil.producedItem != null) {
//					e.getEntityPlayer().addItemStackToInventory(anvil.producedItem);
//					anvil.anvilOwner = null;
//					anvil.producedItem = null;
//					anvil.sendUpdates();
//				}
//			}
		}
	}
		
	private void processLogChopped(PlayerInteractEvent e) {
		World world = e.getWorld();
		EntityPlayer player = e.getEntityPlayer();
		BlockPos pos = e.getPos();
		
		if(e.getWorld().isRemote)
			return;
		
		float pitchOffset = TOCMain.rand.nextFloat() / .3f - .15f;
		world.playSound(null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.MASTER, 1, 1 + pitchOffset);

		Network.INSTANCE.sendToAll(new SpawnParticlesPKT(ParticleSystem.ORE_MINING_PARTICLE_SYSTEM, pos));
		
		BlockLog log = (BlockLog) world.getBlockState(pos).getBlock();

		if(TOCMain.pm.getPlayer(e.getEntityPlayer()).getPlayerLevels().getLevel(Levels.WOODCUTTING).getLevel() < 
				Woodcutting.getLevelRequirementForWood(log)) {
			player.sendMessage(new TextComponentString("You need at least Woodcutting level " + Woodcutting.getLevelRequirementForWood(log)
					+ " to cut down " + log.getNameTextComponent()));
			return;
		}
		
		float chance = Woodcutting.getChopChanceForWood((TOCAxe) e.getEntityPlayer().getHeldItemMainhand().getItem(), world.getBlockState(pos));
		if(chance == 0) {
			player.sendMessage(new TextComponentString("Your axe is not strong enough to chop down " + log.getNameTextComponent()));
			return;
		}
		
		if(TOCMain.rand.nextFloat() <= chance) {
			Map<BlockPos, IBlockState> list = Woodcutting.getTreeFromLog(world, pos);
			int leavesFound = 0;
			for(Map.Entry<BlockPos, IBlockState> entry : list.entrySet()) {
				if(entry.getValue().getBlock() instanceof BlockLeaves)
					++leavesFound;
				if(leavesFound > 5)
					break;
			}
			
			if(leavesFound < 5) {
				System.out.println("Not enough leaves found: " + leavesFound);
				return;
			}
			
			if(player.inventory.getFirstEmptyStack() == -1) {
				player.sendMessage(new TextComponentString("Your inventory is too full to hold any more logs!"));
				return;
			}
			
			if(list.size() > Woodcutting.MAX_TREE_SIZE) {
				player.sendMessage(new TextComponentString("You can't cut down that tree!"));
				return;
			}

			int xpGiven = Woodcutting.getExpForWood(log);
			Item item = Woodcutting.getHarvestItemForWood(log);
			
			if(TOCMain.rand.nextFloat() < Woodcutting.getDestroyChanceForWood(world.getBlockState(pos))) {
				IBlockState lowestBlock = null;
				BlockPos lowestPos = null;
				for(Map.Entry<BlockPos, IBlockState> entry : list.entrySet()) {
					if(lowestBlock == null && lowestPos == null || lowestPos.getY() > entry.getKey().getY()) {
						lowestPos = entry.getKey();
						lowestBlock = entry.getValue();
					}
					world.setBlockState(entry.getKey(), Blocks.AIR.getDefaultState());
				}
				world.setBlockState(lowestPos, lowestBlock);
			
				//TODO
				//world.setBlockState(pos, TOCBlocks.harvested_tree.getDefaultState());
				TileEntityHarvestedTree tree = (TileEntityHarvestedTree)world.getTileEntity(pos);
				tree.minSecs = Woodcutting.getMinRespawnSecsForWood(log);
				tree.maxSecs = Woodcutting.getMaxRespawnSecsForWood(log);
				
				tree.treeBlocks = list;
			}
			
			if(player != null) {
				ItemStack stack = new ItemStack(item);
				MinecraftForge.EVENT_BUS.post(new ItemAddedToInventoryEvent(player, stack));
				player.addItemStackToInventory(stack);
				TOCMain.pm.getPlayer(player).levels.addExp(Levels.WOODCUTTING, xpGiven);
				Network.INSTANCE.sendTo(new AddLevelXpMessage("Woodcutting", xpGiven), (EntityPlayerMP) player);
			}
		}
	}
}
