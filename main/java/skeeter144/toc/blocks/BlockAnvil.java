package skeeter144.toc.blocks;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesMessage;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.sounds.Sounds;

public class BlockAnvil extends BlockTileEntity<TileEntityAnvil> {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
	protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
	protected static final Logger LOGGER = LogManager.getLogger();

	final ArrayList<Item> ingots = new ArrayList<Item>();
	
	public BlockAnvil(String name) {
		super(Material.ANVIL, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setLightOpacity(0);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setBlockUnbreakable();
		ingots.add(TOCItems.ingot_bronze);
		ingots.add(TOCItems.ingot_iron);
		ingots.add(TOCItems.ingot_steel);
		ingots.add(TOCItems.ingot_gold);
		ingots.add(TOCItems.ingot_mithril);
		ingots.add(TOCItems.ingot_adamantite);
		ingots.add(TOCItems.ingot_runite);
		ingots.add(TOCItems.ingot_dragonstone);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean isServer = !worldIn.isRemote;
		
		if(ingots.contains(playerIn.getHeldItem(hand).getItem())) {
			TileEntityAnvil anvil = (TileEntityAnvil)worldIn.getTileEntity(pos);
			if(isServer) {
				if(anvil.anvilOwner != null) {
					if(!anvil.anvilOwner.equals(playerIn.getUniqueID())) {
						playerIn.sendMessage(new TextComponentString("Someone else is using that anvil! Find an empty one"));
						return false;
					}
				}
			}

			Item item = playerIn.getHeldItem(hand).getItem();
			if(anvil.addedIngots == 0) {
				anvil.ingot = playerIn.getHeldItem(hand).getItem();
				anvil.sendUpdates();
			}
			
			if(item.equals(anvil.ingot) && anvil.addedIngots < 6) {
				++anvil.addedIngots;
				anvil.anvilOwner = playerIn.getUniqueID();
				anvil.sendUpdates();
				if(isServer) {
					playerIn.getHeldItem(hand).shrink(1);
					worldIn.playSound(null, pos, Sounds.ingot_place, SoundCategory.MASTER, 1, 1);
				}
			}else {
				if(isServer) {
					if(!item.equals(anvil.ingot)) {
						playerIn.sendMessage(new TextComponentString("You already have " + new ItemStack(anvil.ingot).getDisplayName()
								+ "s on the anvil. Either take them off by right clicking with an empty hand, or craft those ingots first."));
					}else {
						playerIn.sendMessage(new TextComponentString("You already have 6 ingots on the anvil!"));
					}
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		super.onBlockClicked(worldIn, pos, playerIn);
		boolean isServer = !worldIn.isRemote;

		TileEntityAnvil anvil = (TileEntityAnvil)worldIn.getTileEntity(pos);
		if(playerIn.getCooledAttackStrength(1) < 1)
			return;
		
		boolean holdingHammer = playerIn.getHeldItemMainhand().getItem().equals(TOCItems.blacksmith_hammer);
		
		if(anvil.anvilOwner == null && holdingHammer) {
			if(!isServer)
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1, TOCMain.rand.nextFloat() / 5 + 1);
			else
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1, TOCMain.rand.nextFloat() / 2 + 1);
			return;
		}
		
		if(!anvil.anvilOwner.equals(playerIn.getUniqueID()) && holdingHammer) {
			if(!isServer)
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1, TOCMain.rand.nextFloat() / 5 + 1);
			else {
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1, TOCMain.rand.nextFloat() / 5 + 1);
				
			}
			return;
		}
		
		if(holdingHammer) {
			if(isServer) {
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1, TOCMain.rand.nextFloat() / 5 + 1);
				anvil.hammerStruck(playerIn);
				Network.INSTANCE.sendToAll(new SpawnParticlesMessage(ParticleSystem.ANVIL_STRUCK_PARTICLE_SYSTEM, 
						pos.getX(), pos.getY(), pos.getZ()));
				System.out.println("spawn");
			}else{
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1, TOCMain.rand.nextFloat() / 5 + 1);
			}
			return;
		}else{
			if(!isServer)
				return;
			
			if(anvil.addedIngots > 0)
				playerIn.addItemStackToInventory(new ItemStack(anvil.ingot));
			
			if(--anvil.addedIngots == 0) {
				anvil.ingot = null;
				anvil.anvilOwner = null;
			}
			anvil.sendUpdates();
		}
	}
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_,
			EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();
		try {
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING,
					enumfacing);
		} catch (IllegalArgumentException var11) {
			if (!worldIn.isRemote) {
				LOGGER.warn(String.format("Invalid damage property for anvil at %s. Found %d, must be in [0, 1, 2]",
						pos, meta >> 2));

				if (placer instanceof EntityPlayer) {
					placer.sendMessage(new TextComponentTranslation("Invalid damage property. Please pick in [0, 1, 2]",
							new Object[0]));
				}
			}
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, placer).withProperty(FACING,enumfacing);
		}
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getBlock() != this ? state
				: state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public Class<TileEntityAnvil> getTileEntityClass() {
		return TileEntityAnvil.class;
	}

	@Override
	public TileEntityAnvil createTileEntity(World world, IBlockState state) {
		return new TileEntityAnvil();
	}
}
