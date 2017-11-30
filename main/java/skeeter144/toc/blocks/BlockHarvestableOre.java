package skeeter144.toc.blocks;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.items.tools.CustomPickaxe;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesMessage;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.sounds.Sounds;

public class BlockHarvestableOre extends CustomBlock{
	final List<Item> picks = Arrays.asList(TOCItems.bronze_pickaxe, TOCItems.iron_pickaxe, TOCItems.steel_pickaxe, TOCItems.mithril_pickaxe,
									 TOCItems.adamantite_pickaxe, TOCItems.runite_pickaxe, TOCItems.dragonstone_pickaxe);
	public Item item;
	public int minSecs, maxSecs, xpGiven;
	public BlockHarvestableOre(String name, Item ore, int xpGiven, int minSecs, int maxSecs) {
		super(name, Material.ROCK);
		this.setHardness(-1);
		this.item = ore;
		this.minSecs = minSecs;
		this.maxSecs = maxSecs;
		this.xpGiven = xpGiven;
	}
	
	
	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		if(player.getCooledAttackStrength(1) < 1)
			return;
		
		if(!picks.contains(player.getHeldItemMainhand().getItem()))
			return;
		float pitchOffset = TOCMain.rand.nextFloat() / .3f - .15f;
		world.playSound(player, pos, Sounds.pickaxe_strike, SoundCategory.MASTER, 1, 1 + pitchOffset);

		if(world.isRemote)
			return;
		
		 Network.INSTANCE.sendToAll(new SpawnParticlesMessage(ParticleSystem.ORE_MINING_PARTICLE_SYSTEM, pos.getX(), pos.getY(), pos.getZ()));
		
		float chance =((CustomPickaxe)player.getHeldItemMainhand().getItem()).getMineChanceForOre(this);
		if(TOCMain.rand.nextFloat() < chance) {
			 MinecraftForge.EVENT_BUS.post(new BlockEvent.BreakEvent(world, pos, world.getBlockState(pos), player));
			 Network.INSTANCE.sendToAll(new SpawnParticlesMessage(ParticleSystem.ORE_BROKEN_PARTICLE_SYSTEM, pos.getX(), pos.getY(), pos.getZ()));
		}
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
}
