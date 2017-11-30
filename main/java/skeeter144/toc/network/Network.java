package skeeter144.toc.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import skeeter144.toc.util.Reference;

public class Network {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	private static int id = 0;
	public static int getNextId() {
		return id++;
	}
	
}
