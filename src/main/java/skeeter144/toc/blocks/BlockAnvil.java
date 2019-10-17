package skeeter144.toc.blocks;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.sounds.Sounds;

public class BlockAnvil extends BlockTileEntity<TileEntityAnvil> {

	public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
	protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
	protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
	protected static final Logger LOGGER = LogManager.getLogger();

	final ArrayList<Item> ingots = new ArrayList<Item>();
	
	public BlockAnvil(String name) {
		super(Properties.create(Material.ANVIL).hardnessAndResistance(1000, 1000), name);
		//this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
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
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, 
			EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean isServer = !worldIn.isRemote;
		
		if(ingots.contains(player.getHeldItem(hand).getItem())) {
			TileEntityAnvil anvil = (TileEntityAnvil)worldIn.getTileEntity(pos);
			if(isServer) {
				if(anvil.anvilOwner != null) {
					if(!anvil.anvilOwner.equals(player.getUniqueID())) {
						player.sendMessage(new TextComponentString("Someone else is using that anvil! Find an empty one"));
						return false;
					}
				}
			}

			Item item = player.getHeldItem(hand).getItem();
			if(anvil.addedIngots == 0) {
				anvil.ingot = player.getHeldItem(hand).getItem();
				anvil.sendUpdates();
			}
			
			if(item.equals(anvil.ingot) && anvil.addedIngots < 6) {
				++anvil.addedIngots;
				anvil.anvilOwner = player.getUniqueID();
				anvil.sendUpdates();
				if(isServer) {
					player.getHeldItem(hand).shrink(1);
					worldIn.playSound(null, pos, Sounds.ingot_place, SoundCategory.MASTER, 1, 1);
				}
			}else {
				if(isServer) {
					if(!item.equals(anvil.ingot)) {
						player.sendMessage(new TextComponentString("You already have " + new ItemStack(anvil.ingot).getDisplayName()
								+ "s on the anvil. Either take them off by right clicking with an empty hand, or craft those ingots first."));
					}else {
						player.sendMessage(new TextComponentString("You already have 6 ingots on the anvil!"));
					}
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn) {
		super.onBlockClicked(state, worldIn, pos, playerIn);
		boolean isServer = !worldIn.isRemote;

		TileEntityAnvil anvil = (TileEntityAnvil)worldIn.getTileEntity(pos);
		if(playerIn.getCooledAttackStrength(1) < 1)
			return;
		
		//TODO
		boolean holdingHammer = false;//playerIn.getHeldItemMainhand().getItem().equals(TOCItems.blacksmith_hammer);
		
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
				//Network.INSTANCE.sendToAll(new SpawnParticlesPKT(ParticleSystem.ANVIL_STRUCK_PARTICLE_SYSTEM, pos));
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
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	 public IBlockState getStateForPlacement(BlockItemUseContext context) {
	      return this.getDefaultState();//.with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	   }
	

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().with(FACING, EnumFacing.byIndex(meta & 3));
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.get(FACING)).getHorizontalIndex();
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getBlock() != this ? state
				: state.with(FACING, rot.rotate((EnumFacing) state.get(FACING)));
	}


	@Override
	public Class<TileEntityAnvil> getTileEntityClass() {
		return TileEntityAnvil.class;
	}

	@Override
	public TileEntityAnvil createTileEntity(World world, IBlockState state) {
		//TODO: create tileentity types
		return new TileEntityAnvil(null);
	}
}
