package skeeter144.toc;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.IForgeRegistryEntry;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.config.ConfigHelper;
import skeeter144.toc.config.ConfigHolder;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.util.Reference;

@EventBusSubscriber(modid = Reference.MODID,bus = EventBusSubscriber.Bus.MOD)
public class TOCEventSubscriber {
	private static final Logger LOGGER = LogManager.getLogger(Reference.MODID + " Mod Event Subscriber");
	
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Reference.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
	
	@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
		TOCBlocks.registerAllBlocks(event);
		LOGGER.debug("Registered Blocks");
	}
	
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		TOCItems.registerAllItems(event);
		
	}
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		final ModConfig config = event.getConfig();
		// Rebake the configs when they change
		if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
			ConfigHelper.bakeClient(config);
		} else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
			ConfigHelper.bakeServer(config);
		}
	}
	
	/**
	 * This method will be called by Forge when it is time for the mod to register its EntityTypes.
	 * This method will always be called after the Block and Item registry methods.
	 */
	@SubscribeEvent
	public static void onRegister(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event) {
		// Register your TileEntities here if you have them
		event.getRegistry().registerAll(
				// We don't have a datafixer for our TileEntity, so we pass null into build
				//setup(TileEntityType.Builder.create(TileEntityExampleTileEntity::new).build(null), "example_tile_entity")
		);
		LOGGER.debug("Registered TileEntitys");
	}
}
