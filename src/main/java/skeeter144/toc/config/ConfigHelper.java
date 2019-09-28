package skeeter144.toc.config;

import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {

	// We store a reference to the ModConfigs here to be able to change the values in them from our code
	// (For example from a config GUI)
	private static ModConfig clientConfig;
	private static ModConfig serverConfig;

	public static void bakeClient(final ModConfig config) {
		clientConfig = config;

		TOCModConfig.clientBoolean = ConfigHolder.CLIENT.clientBoolean.get();
		TOCModConfig.clientStringList = ConfigHolder.CLIENT.clientStringList.get();
		TOCModConfig.clientEnumDyeColor = ConfigHolder.CLIENT.clientEnumDyeColor.get();

	}

	public static void bakeServer(final ModConfig config) {
		serverConfig = config;

		TOCModConfig.serverBoolean = ConfigHolder.SERVER.serverBoolean.get();
		TOCModConfig.serverStringList = ConfigHolder.SERVER.serverStringList.get();
		TOCModConfig.serverEnumDyeColor = ConfigHolder.SERVER.serverEnumDyeColor.get();

	}

	private static void setValueAndSave(final ModConfig modConfig, final String path, final Object newValue) {
		modConfig.getConfigData().set(path, newValue);
		modConfig.save();
	}

}