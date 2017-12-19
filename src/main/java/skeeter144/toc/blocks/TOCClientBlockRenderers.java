package skeeter144.toc.blocks;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import skeeter144.toc.client.entity.tilerenderer.TileEntityAnvilRenderer;
import skeeter144.toc.entity.tile.TileEntityAnvil;

public class TOCClientBlockRenderers {

	public static void registerAll() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvil.class, new TileEntityAnvilRenderer());
	}

}
