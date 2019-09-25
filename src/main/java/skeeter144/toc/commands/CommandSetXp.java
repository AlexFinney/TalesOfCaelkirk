package skeeter144.toc.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.UserListEntryBan;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.player.Level;
import skeeter144.toc.player.TOCPlayer;

public class CommandSetXp{
	 public static void register(CommandDispatcher<CommandSource> dispatcher) {
	      dispatcher.register(Commands.literal("setxp").requires((sender) -> {
	         return sender.hasPermissionLevel(3);
	      }).executes((sender) -> {
	    	  TOCPlayer pl = TOCMain.pm.getPlayer((EntityPlayer) sender.getCommandSenderEntity());
	  		int prevXp = 0;
	  		if(pl != null) {
	  			Collection<Level> levels = pl.getPlayerLevels().getLevels();
	  			for(Level l : levels) {
	  				if(l.getName().equalsIgnoreCase(level)) {
	  					prevXp = l.getXp();
	  					l.setXp(amount);
	  					sender.sendMessage(new TextComponentString(level + " xp updated to " + amount + "!" ));
	  					break;
	  				}
	  			}
	  			Network.INSTANCE.sendTo(new AddLevelXpMessage(level, amount - prevXp), (EntityPlayerMP)sender.getCommandSenderEntity());
	  		}
	  		return 0;
	      }).then(Commands.literal("ips").executes((sender) -> {
	         return sendBanList(sender.getSource(), sender.getSource().getServer().getPlayerList().getBannedIPs().getEntries());
	      })).then(Commands.literal("players").executes((sender) -> {
	         return sendBanList(sender.getSource(), sender.getSource().getServer().getPlayerList().getBannedPlayers().getEntries());
	      })));
	   }

	   private static int sendBanList(CommandSource source, Collection<? extends UserListEntryBan<?>> bannedPlayerList) {
	      if (bannedPlayerList.isEmpty()) {
	         source.sendFeedback(new TextComponentTranslation("commands.banlist.none"), false);
	      } else {
	         source.sendFeedback(new TextComponentTranslation("commands.banlist.list", bannedPlayerList.size()), false);

	         for(UserListEntryBan<?> userlistentryban : bannedPlayerList) {
	            source.sendFeedback(new TextComponentTranslation("commands.banlist.entry", userlistentryban.getDisplayName(), userlistentryban.getBannedBy(), userlistentryban.getBanReason()), false);
	         }
	      }

	      return bannedPlayerList.size();
	   }
	
	@Override
	public int compareTo(ICommand cmd) {
		return 0;
	}

	@Override
	public String getName() {
		return "setxp";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "setxp <level> <amount>";
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender.getEntityWorld().isRemote)
			return;
		
		if(!server.isSinglePlayer()) {
			sender.sendMessage(new TextComponentString("You cannot use setxp on a multiplayer server!"));
			return;
		}
		
		if(args.length != 2) {
			sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
			return;
		}
		
		String level = args[0];
		int amount = Integer.parseInt(args[1]);
		
		TOCPlayer pl = TOCMain.pm.getPlayer((EntityPlayer) sender.getCommandSenderEntity());
		int prevXp = 0;
		if(pl != null) {
			Collection<Level> levels = pl.getPlayerLevels().getLevels();
			for(Level l : levels) {
				if(l.getName().equalsIgnoreCase(level)) {
					prevXp = l.getXp();
					l.setXp(amount);
					sender.sendMessage(new TextComponentString(level + " xp updated to " + amount + "!" ));
					break;
				}
			}
			Network.INSTANCE.sendTo(new AddLevelXpMessage(level, amount - prevXp), (EntityPlayerMP)sender.getCommandSenderEntity());
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "setxp");
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
