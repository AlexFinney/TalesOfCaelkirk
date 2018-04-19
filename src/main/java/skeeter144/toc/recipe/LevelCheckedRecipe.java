package skeeter144.toc.recipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import skeeter144.toc.TOCMain;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.util.Reference;

public class LevelCheckedRecipe implements IRecipe {
	ResourceLocation registryName;
	public boolean shapeless = false;
	Set<Item> shapelessInput;
	List<Item> shapedInput;
	ItemStack output;
	public int levelReq;
	public int xpGiven;

	public LevelCheckedRecipe(List<Item> input, ItemStack output,
			int levelRequired, int xpGiven) {
		shapeless = true;
		this.shapelessInput = new HashSet<Item>(input);
		this.output = output;
		this.levelReq = levelRequired;
		this.xpGiven = xpGiven;
		this.setRegistryName(new ResourceLocation(Reference.MODID,
				output.getItem().getRegistryName().toString()));
	}

	
	/*
	 * "iii" + 
	 * " s " + 
	 * " s " +
	 * "i,iron,"
	 *  "s,stick"
	 */
	public LevelCheckedRecipe(ItemStack output,
			int levelRequired, int xpGiven, String pattern) {
		shapeless = false;
		this.shapedInput = parseItemPattern(pattern);
		this.output = output;
		this.levelReq = levelRequired;
		this.xpGiven = xpGiven;
		this.setRegistryName(new ResourceLocation(Reference.MODID,
				output.getItem().getRegistryName().toString()));
		System.out.println();
	}

	private List<Item> parseItemPattern(String pattern) {
		List<Item> items = new ArrayList<Item>();
		
		String patternKey = pattern.substring(9);
		
		Map<String, Item> itemKey = new HashMap<String, Item>();
		itemKey.put(" ", Items.AIR);
		
		String[] tokens = patternKey.split(",");
		for(int i = 0; i < tokens.length; i+=2) {
			ResourceLocation loc = new ResourceLocation(tokens[i+1].split(":")[0], tokens[i+1].split(":")[1]);
			itemKey.put(tokens[0], GameRegistry.findRegistry(Item.class).getValue(loc));
		}
		
		items.add(itemKey.get(Character.toString(pattern.charAt(0))));
		items.add(itemKey.get(Character.toString(pattern.charAt(1))));
		items.add(itemKey.get(Character.toString(pattern.charAt(2))));
		
		items.add(itemKey.get(Character.toString(pattern.charAt(3))));
		items.add(itemKey.get(Character.toString(pattern.charAt(4))));
		items.add(itemKey.get(Character.toString(pattern.charAt(5))));
		
		items.add(itemKey.get(Character.toString(pattern.charAt(6))));
		items.add(itemKey.get(Character.toString(pattern.charAt(7))));
		items.add(itemKey.get(Character.toString(pattern.charAt(8))));
		
		
		return items;
	}

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		this.registryName = name;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return registryName;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return null;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return playerMeetsLevelReq(inv) && itemPatternMatches(inv);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return new ItemStack(output.getItem(), output.getCount());
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	
	boolean playerMeetsLevelReq(InventoryCrafting inv) {
		EntityPlayer pl = findPlayer(inv);
		if(pl == null)
			return false;
		
		 return TOCMain.pm.getPlayer(pl).getPlayerLevels().getLevel(Levels.CRAFTING).getLevel() >= levelReq;
	}

	
	boolean itemPatternMatches(InventoryCrafting inv) {
		if(shapeless)
			return itemPatternMatchesShapeless(inv);
		else 
			return itemPatternMatchesShaped(inv);
	}
	
	
	boolean itemPatternMatchesShapeless(InventoryCrafting inv) {
		List<Item> stacksInInv = new ArrayList<Item>(); 
		
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			if(inv.getStackInSlot(i).getItem() != Items.AIR)
				stacksInInv.add(inv.getStackInSlot(i).getItem());
		}
		
		if(stacksInInv.size() != shapelessInput.size())
			return false;
		
		Set<Item> invSet = new HashSet<Item>(stacksInInv);
		
		return invSet.equals(shapelessInput);
	}
	
	boolean itemPatternMatchesShaped(InventoryCrafting inv) {
		
		int firstItemIndex = -1, lastItemIndex = -1, counter = 0;
		for(Item item : shapedInput) {
			if(item != Items.AIR) {
				if(firstItemIndex == -1) {
					firstItemIndex = counter;
				}
				lastItemIndex = counter;
			}
			++counter;
		}
		
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			Item item = inv.getStackInSlot(i).getItem();
			if(i < firstItemIndex || i > lastItemIndex) {
				if(item != Items.AIR) {
					return false;
				}
			}else {
				if(item != shapedInput.get(i))
					return false;
			}
		}
		
		return true;
	}
	
	private final Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class, "eventHandler");
	private final Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class, "player");
	private final Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, "player");
	
	private EntityPlayer findPlayer(InventoryCrafting inv) {
		try {
			Container container = (Container) eventHandlerField.get(inv);
			if (container instanceof ContainerPlayer)
				return (EntityPlayer) containerPlayerPlayerField.get(container);
			else if (container instanceof ContainerWorkbench)
				return (EntityPlayer) slotCraftingPlayerField.get(container.getSlot(0));
		} catch (Exception e) {

		}
		return null;
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

}
