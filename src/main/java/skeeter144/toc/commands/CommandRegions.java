package skeeter144.toc.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.RegionsRendering;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShouldShowRegionsMessage;
import skeeter144.toc.regions.Point;
import skeeter144.toc.regions.Region;
import skeeter144.toc.regions.RegionBound;

public class CommandRegions implements ICommand {
	
	List<String> aliases;
	public CommandRegions() {
		aliases = new ArrayList<String>();
		aliases.add("regs");
	}
	
	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "regions";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/regions <command> [args ...]";
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
			case "setp":
			case "sp":
			case "setpoint":
				if(args.length != 2) {
					sender.sendMessage(new TextComponentString("Usage: /regions <setpoint, setp, sp> <1 or 2>"));	
					return;
				}
				
				try{
					int pointNum = Integer.parseInt(args[1]);
					MutablePair<Point, Point> points = (MutablePair<Point, Point>) TOCMain.rm.getPlayersPoints(sender.getCommandSenderEntity().getUniqueID());
					if(points == null)
						points = new MutablePair<Point, Point>();
					if(pointNum != 1 && pointNum != 2) {
						sender.sendMessage(new TextComponentString("Usage: /regions <setpoint, setp, sp> <1 or 2>"));
						return;
					}
					Point p = new Point(sender.getCommandSenderEntity().getPosition().getX(), 
										sender.getCommandSenderEntity().getPosition().getZ());
				if(pointNum == 1)
					points.setLeft(p);
				else
					points.setRight(p);
				TOCMain.rm.setPlayersPoints(sender.getCommandSenderEntity().getUniqueID(), points);
				sender.sendMessage(new TextComponentString("Point " + pointNum + " set to [" + p.x + ", " + p.z + "]"));
				}catch(Exception e) {
					sender.sendMessage(new TextComponentString("Usage: /regions <setpoint, setp, sp> <1 or 2>"));	
					return;
				}
				break;
			case "addr":
			case "ar":
			case "add":
			case "addregion":
				if(args.length != 2) {
					sender.sendMessage(new TextComponentString("Usage: /regions <addregion, addr, ar> <name>"));
					return;
				}
				Pair<Point, Point> points = TOCMain.rm.getPlayersPoints(sender.getCommandSenderEntity().getUniqueID());
				if(points == null || points.getLeft() == null || points.getRight() == null) {
					sender.sendMessage(new TextComponentString("You must set two corners of a region before you can add a new region!"));
					sender.sendMessage(new TextComponentString("Try: /regions (setpoint, setp, sp) <1 or 2>"));
					return;
				}
				//TODO: transform points here to get the bottom left and upper right corner
				
				Point p1 = points.getLeft();
				Point p2 = points.getRight();
				
				Point new1 = null, new2 = null;
				if(p1.x <= p2.x) {
					if(p1.z <= p2.z) {
						new1 = new Point(p1.x, p1.z);
						new2 = new Point(p2.x, p2.z);
					}else {
						//x is less, but z is more
						new1 = new Point(p1.x, p2.z);
						new2 = new Point(p2.x, p1.z);
					}
				}else { 
					if(p1.z <= p2.z) {
						//x is greater, and z is less
						new1 = new Point(p2.x, p1.z);
						new2 = new Point(p1.x, p2.z);
					}else{
						//x is greater, z is greater, switch em
						new1 = new Point(p2.x, p2.z);
						new2 = new Point(p1.x, p1.z);
					}
				}
				
				
				TOCMain.rm.addBoundsToRegion(args[1], new1, new2);
				sender.sendMessage(new TextComponentString("Added new boundry to region \"" + args[1] + "\"\nFrom:"
						+ "[" + new1.x + ", " + new1.z + " to [" + new2.x + ", " + new2.z + "]"));
				break;
			case "show":
				Network.INSTANCE.sendTo(new ShouldShowRegionsMessage(true, TOCMain.rm.getRegions()), (EntityPlayerMP)sender.getCommandSenderEntity());
				break;
			case "hide":
				Network.INSTANCE.sendTo(new ShouldShowRegionsMessage(false, null), (EntityPlayerMP)sender.getCommandSenderEntity());RegionsRendering.doRender = false;
				break;
			case "list":
				sender.sendMessage(new TextComponentString("Regions: "));
				for(Map.Entry<String, Region> entry : TOCMain.rm.getRegions().entrySet()) {
					sender.sendMessage(new TextComponentString("     " + entry.getKey() + ", bounds: " + entry.getValue().bounds.size()));
				}
				break;
			case "rma":
			case "rmall":
			case "removeall":
					if(args.length != 2) {
						sender.sendMessage(new TextComponentString("Usage: /regions (removeall, rmall, rma) <region_name>"));
						break;
					}
					String regionName = args[1];
					Region r = TOCMain.rm.getRegion(regionName);
					if(r == null) {
						sender.sendMessage(new TextComponentString("Unknown region: " + regionName));
						break;
					}
					TOCMain.rm.getRegions().remove(regionName);
				break;
			
			case "rmb":
			case "rmbound":
			case "removeboundry":
				if(args.length != 2) {
					sender.sendMessage(new TextComponentString("Usage: /regions (removeboundry, rmbound, rmb) <region_name>"));
					break;
				}
				Region reg = TOCMain.rm.getRegion(args[1]);
				if(reg == null) {
					sender.sendMessage(new TextComponentString("Error: Region \"" + args[1] + "\" does not exist."));
					break;
				}
				List<RegionBound> boundsToRemove = new ArrayList<RegionBound>();
				for(RegionBound rb : reg.bounds) {
					Entity e = sender.getCommandSenderEntity();
					if(rb.containsPoint((int)e.posX, (int)e.posZ)) {
						boundsToRemove.add(rb);
					}
				}
				for(RegionBound rb : boundsToRemove) {
					reg.bounds.remove(rb);
				}
				break;
			default:
				sender.sendMessage(new TextComponentString("Unknown command: " + command));
				break;
			}
			
			
		}else {
			sender.sendMessage(new TextComponentString(getUsage(sender)));	
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "regions");
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
