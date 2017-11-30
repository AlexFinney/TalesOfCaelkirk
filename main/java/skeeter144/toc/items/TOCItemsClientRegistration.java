package skeeter144.toc.items;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.blocks.TOCBlocks;

public class TOCItemsClientRegistration {
	
	@SubscribeEvent
	public void registerItemModels(ModelRegistryEvent e) {
		registerAllBlockModels();
		registerAllItemModels();
	}
	
	private void registerAllBlockModels() {
		registerBlockItemModel(TOCBlocks.spiderTreeLeaves.getDefaultState());
		registerBlockItemModel(TOCBlocks.spiderTreeLog.getDefaultState());
	}
	
	private void registerAllItemModels() {
		try {
			for(Field f : TOCItems.class.getFields()){
				if(f.get(null) instanceof Item) {
					registerItemRendering(((Item)f.get(null)));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private final StateMapperBase propertyStringMapper = new StateMapperBase() {
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return new ModelResourceLocation("minecraft:air");
        }
    };
	
	private void registerBlockItemModel(final IBlockState state) {
		final Block block = state.getBlock();
		final Item item = Item.getItemFromBlock(block);

		if (item != Items.AIR) {
			ModelResourceLocation loc =  new ModelResourceLocation(block.getRegistryName(), propertyStringMapper.getPropertyString(state.getProperties()));
			registerItemModel(item, stack -> loc);
		}
	}
	
	private void registerItemRendering(Item i) {
		String modelLocation =  i.getRegistryName().toString();
		ModelResourceLocation fullLocation = new ModelResourceLocation(modelLocation, "inventory");
		ModelBakery.registerItemVariants(i, fullLocation);
		registerItemModel(i, stack -> fullLocation);
	}
	
	private void registerItemModel(final Item item, final ItemMeshDefinition meshDefinition) {
		ModelLoader.setCustomMeshDefinition(item, meshDefinition);
	}
}
