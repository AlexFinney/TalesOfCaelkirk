package skeeter144.toc.skills;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.AddLevelXpPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;

public class Crafting {

	public IRecipe<CraftingInventory> oak_stick;
	public IRecipe<CraftingInventory> birch_stick;
	public IRecipe<CraftingInventory> maple_stick;
	public IRecipe<CraftingInventory> yew_stick;
	public IRecipe<CraftingInventory> orc_stick;
	public IRecipe<CraftingInventory> magic_stick;
	
	public IRecipe<CraftingInventory> oak_shortbow_unstrung;
	public IRecipe<CraftingInventory> oak_longbow_unstrung;
	
	public IRecipe<CraftingInventory> oak_shortbow;
	public IRecipe<CraftingInventory> oak_longbow;
	
	public IRecipe<CraftingInventory> string;
	
	public IRecipe<CraftingInventory> fishingPole;
	
	
	Map<Item, Integer> itemLevelRequirements = new HashMap<Item, Integer>();
	Map<Item, Integer> itemXpRewards = new HashMap<Item, Integer>();
	
	
	void initRecipes() {
		
//		oak_shortbow_unstrung = new LevelCheckedRecipe(new ItemStack(TOCItems.bow_oak_short_unstrung), 1, 50, 
//				" o " +
//				"  o" + 
//				" o " + 
//				"o,toc:stick_oak");
//		
//		oak_longbow_unstrung = new LevelCheckedRecipe(new ItemStack(TOCItems.bow_oak_long_unstrung), 3, 75, 
//				 " oo" +
//				 "  o" + 
//			     " oo" + 
//				 "o,toc:stick_oak");
		
		
//		oak_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.oak_log).getItem()), new ItemStack(TOCItems.stick_oak, 4), 1, 10);
//		birch_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.birch_log).getItem()), new ItemStack(TOCItems.stick_birch, 4), 8, 30);
//		maple_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.maple_log).getItem()), new ItemStack(TOCItems.stick_maple, 4), 18, 80);
//		yew_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.yew_log).getItem()), new ItemStack(TOCItems.stick_yew, 4), 30, 120);
//		orc_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.orc_log).getItem()), new ItemStack(TOCItems.stick_orc, 4), 50, 180);
//		magic_stick = new LevelCheckedRecipe(asList(new ItemStack(TOCBlocks.magic_log).getItem()), new ItemStack(TOCItems.stick_magic, 4), 70, 300);
//		
//		oak_shortbow = new LevelCheckedRecipe(asList(TOCItems.bowstring, TOCItems.bow_oak_short_unstrung), new ItemStack(TOCItems.bow_oak_short), 3, 50);
//		oak_longbow = new LevelCheckedRecipe(asList(TOCItems.bowstring, TOCItems.bow_oak_long_unstrung), new ItemStack(TOCItems.bow_oak_long), 3, 50);
//	
//		string = new LevelCheckedRecipe(asList(Item.getItemFromBlock(Blocks.WOOL)), new ItemStack(Items.STRING, 2), 1, 10);
//		
//		fishingPole = new LevelCheckedRecipe(new ItemStack(Items.FISHING_ROD), 1, 20, "  S" + 
//																					  " St" + 
//																					  "S t" + "S,toc:stick_oak,t,minecraft:string");
//		
	}
	
//	@SubscribeEvent
//	void registerRecipes(RegistryEvent.Register<<RecipeType<?>> event){
//		initRecipes();
//		final IForgeRegistry<IRecipe> registry = event.getRegistry();
//		try {
//			Field[] fields = this.getClass().getDeclaredFields();
//			for (Field f : fields) {
//				if (f.get(this) instanceof IRecipe) {
//					IRecipe recipe = (IRecipe) f.get(this);
//					
//					registry.register(recipe);
//					
//					itemLevelRequirements.put(recipe.getRecipeOutput().getItem(), ((LevelCheckedRecipe)recipe).levelReq);
//					itemXpRewards.put(recipe.getRecipeOutput().getItem(), ((LevelCheckedRecipe)recipe).xpGiven);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@SubscribeEvent
	public void itemCraftedEvent(ItemCraftedEvent e) {
		if(e.getPlayer().world.isRemote)
			return;
	
		Item i = e.getCrafting().getItem();
		Integer levelReq = itemLevelRequirements.get(i);
		if(levelReq == null) {
			System.err.println("Error: Item " + i + " is not registered in the crafting manager.");
			return;
		}
		TOCPlayer p = TOCMain.pm.getPlayer(e.getPlayer());
		if(p.levels.getLevel(Levels.CRAFTING).getLevel() < levelReq) {
			e.getPlayer().sendMessage(new StringTextComponent("You must be Crafting Level " + levelReq + " to craft that!"));
			
//			for(int slot = 0; slot < e.getInventory().getSizeInventory(); ++slot) {
//				if(e.getInventory().getStackInSlot(slot).getItem() != Items.AIR) {
//					e.getInventory().setInventorySlotContents(e.getInventory().getStackInSlot(slot));
//					e.getInventory().setInventorySlotContents(slot, new ItemStack(Items.AIR));
//				}
//			}
			e.getCrafting().setCount(0);
			return;
		}
		int xpGiven = getXpFromItemCraft(e.getCrafting().getItem());
		p.levels.addExp(Levels.CRAFTING, xpGiven);
		Network.INSTANCE.sendTo(new AddLevelXpPKT("Crafting", xpGiven), (ServerPlayerEntity) e.getPlayer());
	}
	
	int getCraftingLevelRequiredFor(Item i) {
		return itemLevelRequirements.get(i) == null ? -1 : itemLevelRequirements.get(i);
	}
	
	int getXpFromItemCraft(Item i) {
		return itemXpRewards.get(i) == null ? -1 : itemXpRewards.get(i);
	}
	
	
}
	
