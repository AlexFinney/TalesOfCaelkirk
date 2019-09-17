package skeeter144.toc.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.MiningGameGui;
import skeeter144.toc.client.gui.RegionsRendering;
import skeeter144.toc.minigames.MiningMinigame;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShouldShowRegionsMessage;
import skeeter144.toc.regions.Point;
import skeeter144.toc.regions.Region;
import skeeter144.toc.regions.RegionBound;

public class CommandMiningMinigame implements ICommand {
	
	List<String> aliases;
	public CommandMiningMinigame() {
		aliases = new ArrayList<String>();
		aliases.add("mmg");
	}
	
	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "minigame_mining";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/minigame_mining <command> [args ...]";
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0) {
			String command = args[0].toLowerCase();
			switch(command) {
			case "start":
			case "begin":
			case "create":
				new MiningMinigame(sender.getEntityWorld(), (EntityPlayer)sender.getCommandSenderEntity(), sender.getPosition(), 10, 10);
				break;
			}
			
		}else {
			sender.sendMessage(new TextComponentString(getUsage(sender)));	
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "minigame_mining");
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
