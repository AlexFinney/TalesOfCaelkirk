package skeeter144.toc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;

public class CommandMiningMinigame{

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("give").requires((sender) -> {
			return sender.hasPermissionLevel(2);
		}).then(Commands.argument("targets", EntityArgument.multiplePlayers())
				.then(Commands.argument("item", ItemArgument.itemStack()).executes((p_198493_0_) -> {
					return 1;
				}).then(Commands.argument("count", IntegerArgumentType.integer(1)).executes((p_198495_0_) -> {
					return 1;
				})))));
	}

//	List<String> aliases;
//	public CommandMiningMinigame() {
//		aliases = new ArrayList<String>();
//		aliases.add("mmg");
//	}
//	
//	@Override
//	public int compareTo(ICommand arg0) {
//		return 0;
//	}
//
//	@Override
//	public String getName() {
//		return "minigame_mining";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender) {
//		return "/minigame_mining <command> [args ...]";
//	}
//
//	@Override
//	public List<String> getAliases() {
//		return aliases;
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
//		if(args.length > 0) {
//			String command = args[0].toLowerCase();
//			switch(command) {
//			case "start":
//			case "begin":
//			case "create":
//				new MiningMinigame(sender.getEntityWorld(), (EntityPlayer)sender.getCommandSenderEntity(), sender.getPosition(), 10, 10);
//				break;
//			}
//			
//		}else {
//			sender.sendMessage(new TextComponentString(getUsage(sender)));	
//		}
//	}
//
//	@Override
//	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
//		return sender.canUseCommand(4, "minigame_mining");
//	}
//
//	@Override
//	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
//			BlockPos targetPos) {
//		return null;
//	}
//
//	@Override
//	public boolean isUsernameIndex(String[] args, int index) {
//		return false;
//	}

}
