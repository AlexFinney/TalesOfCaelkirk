package skeeter144.toc.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.ForgeConfigSpec;
import skeeter144.toc.util.Reference;

final class ClientConfig {

	final ForgeConfigSpec.BooleanValue clientBoolean;
	final ForgeConfigSpec.ConfigValue<List<String>> clientStringList;
	final ForgeConfigSpec.ConfigValue<EnumDyeColor> clientEnumDyeColor;

	ClientConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		clientBoolean = builder
				.comment("An example boolean in the client config")
				.translation(Reference.MODID + ".config.clientBoolean")
				.define("clientBoolean", true);
		clientStringList = builder
				.comment("An example list of Strings in the client config")
				.translation(Reference.MODID + ".config.clientStringList")
				.define("clientStringList", new ArrayList<>());
		clientEnumDyeColor = builder
				.comment("An example EnumDyeColor in the client config")
				.translation(Reference.MODID + ".config.clientEnumColor")
				.defineEnum("clientEnumDyeColor", EnumDyeColor.WHITE);
		builder.pop();
	}

}