package skeeter144.toc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import skeeter144.toc.player.EntityLevels.Levels;

public class CommandSetXp{
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("setxp").requires((sender) -> {
			return sender.hasPermissionLevel(3);
		})
		.then(Commands.argument("target", EntityArgument.singlePlayer()))
			.then(Commands.argument("level", StringArgumentType.string()))
				.then(Commands.argument("amount", IntegerArgumentType.integer()))
					.executes((sender) -> {	
						return setXp(EntityArgument.getOnePlayer(sender, "targets"), 
								StringArgumentType.getString(sender, "level"),
								IntegerArgumentType.getInteger(sender, "amount"));
					})
		);
	}

	private static int setXp(EntityPlayerMP player, String level, Integer amount) {
		return 1;
	}
	
//	@Override
//	public int compareTo(ICommand cmd) {
//		return 0;
//	}
//
//	@Override
//	public String getName() {
//		return "setxp";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender) {
//		return "setxp <level> <amount>";
//	}
//
//	@Override
//	public List<String> getAliases() {
//		return new ArrayList<String>();
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
//		if(sender.getEntityWorld().isRemote)
//			return;
//		
//		if(!server.isSinglePlayer()) {
//			sender.sendMessage(new TextComponentString("You cannot use setxp on a multiplayer server!"));
//			return;
//		}
//		
//		if(args.length != 2) {
//			sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
//			return;
//		}
//		
//		String level = args[0];
//		int amount = Integer.parseInt(args[1]);
//		
//		TOCPlayer pl = TOCMain.pm.getPlayer((EntityPlayer) sender.getCommandSenderEntity());
//		int prevXp = 0;
//		if(pl != null) {
//			Collection<Level> levels = pl.getPlayerLevels().getLevels();
//			for(Level l : levels) {
//				if(l.getName().equalsIgnoreCase(level)) {
//					prevXp = l.getXp();
//					l.setXp(amount);
//					sender.sendMessage(new TextComponentString(level + " xp updated to " + amount + "!" ));
//					break;
//				}
//			}
//			Network.INSTANCE.sendTo(new AddLevelXpMessage(level, amount - prevXp), (EntityPlayerMP)sender.getCommandSenderEntity());
//		}
//	}
//
//	@Override
//	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
//		return sender.canUseCommand(4, "setxp");
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
