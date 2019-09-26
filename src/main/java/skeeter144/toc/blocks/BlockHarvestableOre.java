package skeeter144.toc.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.tile.TileEntityHarvestedOre;
import skeeter144.toc.items.tools.TOCPickaxe;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.sounds.Sounds;

public class BlockHarvestableOre extends CustomBlock{
	final List<Item> picks = new ArrayList<Item>();//Arrays.asList(TOCItems.bronze_pickaxe, TOCItems.iron_pickaxe, TOCItems.steel_pickaxe, TOCItems.mithril_pickaxe,
									 //TOCItems.adamantite_pickaxe, TOCItems.runite_pickaxe, TOCItems.dragonstone_pickaxe);
	public Item item;
	public int minSecs, maxSecs, xpGiven;
	public BlockHarvestableOre(String name, Item ore, int xpGiven, int minSecs, int maxSecs) {
		super(Properties.create(Material.ROCK).hardnessAndResistance(-1), name);
		this.item = ore;
		this.minSecs = minSecs;
		this.maxSecs = maxSecs;
		this.xpGiven = xpGiven;
	}
	
	
	@Override
	public void onBlockClicked(IBlockState state, World world, BlockPos pos, EntityPlayer player) {
		if(player.getCooledAttackStrength(1) < 1)
			return;
		
		if(!picks.contains(player.getHeldItemMainhand().getItem()))
			return;
		float pitchOffset = TOCMain.rand.nextFloat() / .3f - .15f;
		world.playSound(player, pos, Sounds.pickaxe_strike, SoundCategory.MASTER, 1, 1 + pitchOffset);

		if(world.isRemote)
			return;
		
		// Network.INSTANCE.sendToAll(new SpawnParticlesPKT(ParticleSystem.ORE_MINING_PARTICLE_SYSTEM, pos));
		
		float chance = ((TOCPickaxe)player.getHeldItemMainhand().getItem()).getMineChanceForOre(this);
		if(TOCMain.rand.nextFloat() < chance) {
			// Network.INSTANCE.sendToAll(new SpawnParticlesPKT(ParticleSystem.ORE_BROKEN_PARTICLE_SYSTEM, pos));
			 
				if(player.inventory.getFirstEmptyStack() == -1) {
					player.sendMessage(new TextComponentString("Your inventory is too full to hold any more ore!"));
					return;
				}
					
				IBlockState oldState = world.getBlockState(pos);
				MinecraftForge.EVENT_BUS.post(new BlockMinedEvent(world, pos, oldState, player));
				//TODO
				//world.setBlockState(pos, TOCBlocks.harvested_ore.getDefaultState());
				TileEntityHarvestedOre ore = (TileEntityHarvestedOre)world.getTileEntity(pos);
				ore.resourceBlockState = oldState;
				BlockHarvestableOre hOre = (BlockHarvestableOre)oldState.getBlock();
				ore.minSecs = hOre.minSecs;
				ore.maxSecs = hOre.maxSecs;
				if(player != null) {
					player.addItemStackToInventory(new ItemStack(hOre.item));
					TOCMain.pm.getPlayer(player).levels.addExp(Levels.MINING, hOre.xpGiven);
					//Network.INSTANCE.sendTo(new AddLevelXpMessage("Mining", hOre.xpGiven), (EntityPlayerMP) player);
				}
		}
	}
	

	public static class BlockMinedEvent extends BlockEvent{
		public EntityPlayer player;
		public BlockMinedEvent(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
			super(world, pos, state);
			this.player = player;
		}
	}
}
