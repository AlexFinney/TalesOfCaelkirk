package skeeter144.toc.skills;

import static java.util.Arrays.asList;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.registries.IForgeRegistry;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.recipe.LevelCheckedRecipe;

public class Crafting {

	public IRecipe oak_stick;
	public IRecipe birch_stick;
	public IRecipe maple_stick;
	public IRecipe yew_stick;
	public IRecipe orc_stick;
	public IRecipe magic_stick;
	
	public IRecipe oak_shortbow_unstrung;
	public IRecipe oak_longbow_unstrung;
	
	public IRecipe oak_shortbow;
	public IRecipe oak_longbow;
	
	Map<Item, Integer> itemLevelRequirements = new HashMap<Item, Integer>();
	Map<Item, Integer> itemXpRewards = new HashMap<Item, Integer>();
	
	
	void initRecipes() {
		
		oak_shortbow_unstrung = new LevelCheckedRecipe(new ItemStack(TOCItems.arrow_bronze, 4), 1, 20, 
				" a " +
				" s " + 
				" f " + 
				"a,toc:arrowhead_bronze,s:toc:stick_oak,f:minecraft:feather");
		
		
		oak_shortbow_unstrung = new LevelCheckedRecipe(new ItemStack(TOCItems.bow_oak_short_unstrung), 1, 50, 
				" o " +
				"  o" + 
				" o " + 
				"o,toc:stick_oak");
		
		oak_longbow_unstrung = new LevelCheckedRecipe(new ItemStack(TOCItems.bow_oak_long_unstrung), 3, 75, 
				 " oo" +
				 "  o" + 
			     " oo" + 
				 "o,toc:stick_oak");
		
		
		oak_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.oak_log).getItem()), new ItemStack(TOCItems.stick_oak, 4), 1, 10);
		birch_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.birch_log).getItem()), new ItemStack(TOCItems.stick_birch, 4), 8, 30);
		maple_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.maple_log).getItem()), new ItemStack(TOCItems.stick_maple, 4), 18, 80);
		yew_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.yew_log).getItem()), new ItemStack(TOCItems.stick_yew, 4), 30, 120);
		orc_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.orc_log).getItem()), new ItemStack(TOCItems.stick_orc, 4), 50, 180);
		magic_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.magic_log).getItem()), new ItemStack(TOCItems.stick_magic, 4), 70, 300);
		
		oak_shortbow = new LevelCheckedRecipe(asList(TOCItems.bowstring, TOCItems.bow_oak_short_unstrung), new ItemStack(TOCItems.bow_oak_short), 3, 50);
		oak_longbow = new LevelCheckedRecipe(asList(TOCItems.bowstring, TOCItems.bow_oak_long_unstrung), new ItemStack(TOCItems.bow_oak_long), 3, 50);
		
	}
	
	@SubscribeEvent
	void registerRecipes(RegistryEvent.Register<IRecipe> event){
		initRecipes();
		final IForgeRegistry<IRecipe> registry = event.getRegistry();
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (f.get(this) instanceof IRecipe) {
					IRecipe recipe = (IRecipe) f.get(this);
					
					registry.register(recipe);
					
					itemLevelRequirements.put(recipe.getRecipeOutput().getItem(), ((LevelCheckedRecipe)recipe).levelReq);
					itemXpRewards.put(recipe.getRecipeOutput().getItem(), ((LevelCheckedRecipe)recipe).xpGiven);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public void itemCraftedEvent(ItemCraftedEvent e) {
		if(e.player.world.isRemote)
			return;
	
		Item i = e.crafting.getItem();
		Integer levelReq = itemLevelRequirements.get(i);
		if(levelReq == null) {
			System.err.println("Error: Item " + i + " is not registered in the crafting manager.");
			return;
		}
		TOCPlayer p = TOCMain.pm.getPlayer(e.player);
		if(p.levels.getLevel(Levels.CRAFTING).getLevel() < levelReq) {
			e.player.sendMessage(new TextComponentString("You must be Crafting Level " + levelReq + " to craft that!"));
			
			for(int slot = 0; slot < e.craftMatrix.getSizeInventory(); ++slot) {
				if(e.craftMatrix.getStackInSlot(slot).getItem() != Items.AIR) {
					e.player.addItemStackToInventory(e.craftMatrix.getStackInSlot(slot));
					e.craftMatrix.setInventorySlotContents(slot, new ItemStack(Items.AIR));
				}
			}
			e.crafting.setCount(0);
			return;
		}
		int xpGiven = getXpFromItemCraft(e.crafting.getItem());
		p.levels.addExp(Levels.CRAFTING, xpGiven);
		Network.INSTANCE.sendTo(new AddLevelXpMessage("Crafting", xpGiven), (EntityPlayerMP) e.player);
	}
	
	int getCraftingLevelRequiredFor(Item i) {
		return itemLevelRequirements.get(i) == null ? -1 : itemLevelRequirements.get(i);
	}
	
	int getXpFromItemCraft(Item i) {
		return itemXpRewards.get(i) == null ? -1 : itemXpRewards.get(i);
	}
	
	
}
	
