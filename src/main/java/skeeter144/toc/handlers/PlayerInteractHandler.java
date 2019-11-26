package skeeter144.toc.handlers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.BlockHarvestedTree;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.blocks.log.CustomBlockLog;
import skeeter144.toc.client.gui.Guis;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.entity.tile.TileEntityHarvestedTree;
import skeeter144.toc.handlers.PlayerInventoryHandler.ItemAddedToInventoryEvent;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.items.tools.TOCAxe;
import skeeter144.toc.network.AddLevelXpPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.OpenGUIClientPKT;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.skills.Woodcutting;
import skeeter144.toc.tasks.TickableTask;
import skeeter144.toc.util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerInteractHandler {
	
	
	@SubscribeEvent
	public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock e) {
		if(e.getWorld().isRemote)
			return;
		
		if(e.getPlayer().abilities.isCreativeMode)
			return;
		
		if(e.getPlayer().getCooledAttackStrength(1) != 1) {
			e.setCanceled(true);
			return;
		}
		
		Block b = e.getWorld().getBlockState(e.getPos()).getBlock();
		if(b instanceof CustomBlockLog) {
			if(e.getPlayer().getHeldItemMainhand().getItem() instanceof TOCAxe)
				processLogChopped(e);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLeftClick(AttackEntityEvent e) {
		if(e.getPlayer().getCooledAttackStrength(1) != 1) {
			e.setCanceled(true);
			cancelSwing(e.getPlayer());
		}
	}
	
	private static void cancelSwing(PlayerEntity p) {
		TOCMain.clientTaskManager.addTask(new TickableTask(4) {
			public void tick(int worldTick) {
				p.swingProgress = 0;
				p.isSwingInProgress = false;
				//TOCMain.proxy.cancelSwing();
			}
		});
	}
	
	@SubscribeEvent
	public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
		List<Item> ingots = Arrays.asList(TOCItems.ingot_bronze, TOCItems.ingot_iron, TOCItems.ingot_steel,
				TOCItems.ingot_gold, TOCItems.ingot_mithril, TOCItems.ingot_runite, TOCItems.ingot_dragonstone);

		BlockState bs = e.getWorld().getBlockState(e.getPos()).getBlock().getDefaultState();
		if (!e.getWorld().isRemote) {
			if (bs.getBlock().equals(Blocks.NETHER_BRICKS))
				Network.INSTANCE.sendTo(new OpenGUIClientPKT(Guis.SMELTING_GUI, e.getPos()), (ServerPlayerEntity)e.getPlayer());
			
			if (e.getWorld().getTileEntity(e.getPos()) instanceof TileEntityAnvil) {
				TileEntityAnvil anvil = (TileEntityAnvil) e.getWorld().getTileEntity(e.getPos());

				if (!ingots.contains(e.getPlayer().getHeldItemMainhand().getItem())
						&& anvil.producedItem == null) 
				{
					Network.INSTANCE.sendTo(new OpenGUIClientPKT(Guis.SMITHING_GUI, e.getPos()),
							(ServerPlayerEntity) e.getPlayer());
				}
				
				if (anvil != null && anvil.producedItem != null) {
					e.getPlayer().addItemStackToInventory(anvil.producedItem);
					anvil.anvilOwner = null;
					anvil.producedItem = null;
					anvil.sendUpdates();
				}
			}
		}
	}
		
	private static void processLogChopped(PlayerInteractEvent e) {
		World world = e.getWorld();
		PlayerEntity player = e.getPlayer();
		BlockPos pos = e.getPos();
		
		if(e.getWorld().isRemote)
			return;
		
		float pitchOffset = TOCMain.rand.nextFloat() / .3f - .15f;
		world.playSound(null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.MASTER, 1, 1 + pitchOffset);

		//Network.INSTANCE.sendToAll(new SpawnParticlesPKT(ParticleSystem.ORE_MINING_PARTICLE_SYSTEM, pos));
		
		LogBlock log = (LogBlock) world.getBlockState(pos).getBlock();

		if(TOCMain.pm.getPlayer(e.getPlayer()).getPlayerLevels().getLevel(Levels.WOODCUTTING).getLevel() < 
				Woodcutting.getLevelRequirementForWood(log)) {
			player.sendMessage(new StringTextComponent("You need at least Woodcutting level " + Woodcutting.getLevelRequirementForWood(log)
					+ " to cut down " + log.getNameTextComponent()));
			return;
		}
		float chance = Woodcutting.getChopChanceForWood((TOCAxe) e.getPlayer().getHeldItemMainhand().getItem(), world.getBlockState(pos));
		if(chance == 0) {
			player.sendMessage(new StringTextComponent("Your axe is not strong enough to chop down " + log.getNameTextComponent()));
			return;
		}
		if(TOCMain.rand.nextFloat() <= chance) {
			Map<BlockPos, BlockState> list = Woodcutting.getTreeFromLog(world, pos);
			int leavesFound = 0;
			for(Map.Entry<BlockPos, BlockState> entry : list.entrySet()) {
				if(entry.getValue().getBlock() instanceof LeavesBlock)
					++leavesFound;
				if(leavesFound > 5)
					break;
			}
			
			if(leavesFound < 5) {
				System.out.println("Not enough leaves found: " + leavesFound);
				return;
			}
			
			if(player.inventory.getFirstEmptyStack() == -1) {
				player.sendMessage(new StringTextComponent("Your inventory is too full to hold any more logs!"));
				return;
			}
			
			if(list.size() > Woodcutting.MAX_TREE_SIZE) {
				player.sendMessage(new StringTextComponent("You can't cut down that tree!"));
				return;
			}

			int xpGiven = Woodcutting.getExpForWood(log);
			Item item = Woodcutting.getHarvestItemForWood(log);
			
			if(TOCMain.rand.nextFloat() < Woodcutting.getDestroyChanceForWood(world.getBlockState(pos))) {
				BlockState lowestBlock = null;
				BlockPos lowestPos = null;
				for(Map.Entry<BlockPos, BlockState> entry : list.entrySet()) {
					if(lowestBlock == null && lowestPos == null || lowestPos.getY() > entry.getKey().getY()) {
						lowestPos = entry.getKey();
						lowestBlock = entry.getValue();
					}
					world.setBlockState(entry.getKey(), Blocks.AIR.getDefaultState());
				}
				world.setBlockState(lowestPos, lowestBlock);
			
				TileEntityHarvestedTree tree = (TileEntityHarvestedTree) ((BlockHarvestedTree)TOCBlocks.harvested_tree).createTileEntity(null, world);
				tree.minSecs = Woodcutting.getMinRespawnSecsForWood(log);
				tree.maxSecs = Woodcutting.getMaxRespawnSecsForWood(log);
				tree.treeBlocks = list;

				world.setTileEntity(pos,  tree);
			}
			
			if(player != null) {
				ItemStack stack = new ItemStack(item);
				MinecraftForge.EVENT_BUS.post(new ItemAddedToInventoryEvent(player, stack));
				player.addItemStackToInventory(stack);
				TOCMain.pm.getPlayer(player).levels.addExp(Levels.WOODCUTTING, xpGiven);
				Network.INSTANCE.sendTo(new AddLevelXpPKT("Woodcutting", xpGiven), (ServerPlayerEntity) player);
			}
		}
	}
}
