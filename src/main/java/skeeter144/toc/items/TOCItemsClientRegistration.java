package skeeter144.toc.items;

import java.lang.reflect.Field;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TOCItemsClientRegistration {
	
	@SubscribeEvent
	public void registerItemModels(ModelRegistryEvent e) {
		registerAllItemModels();
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
	
	
	private void registerItemRendering(Item i) {
		String modelLocation =  i.getRegistryName().toString();
		ModelResourceLocation fullLocation = new ModelResourceLocation(modelLocation, "inventory");
		//TODO
		//ModelBakery.registerItemVariants(i, fullLocation);
		//registerItemModel(i, stack -> fullLocation);
	}
	
//	private void registerItemModel(final Item item, final ItemMeshDefinition meshDefinition) {
//		ModelLoader.setCustomMeshDefinition(item, meshDefinition);
//	}
}
