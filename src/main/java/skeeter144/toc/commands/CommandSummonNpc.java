package skeeter144.toc.commands;

public class CommandSummonNpc{
//
//	final ArrayList<String> aliases;
//	public CommandSummonNpc() {
//		aliases = new ArrayList<String>();
//		aliases.add("smn");
//	}
//	
//	@Override
//	public int compareTo(ICommand cmd) {
//		return 0;
//	}
//
//	@Override
//	public String getName() {
//		return "summonnpc";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender) {
//		return "/summonnpc npcName [...additional args...]";
//	}
//
//	@Override
//	public List<String> getAliases() {
//		return aliases;
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
//		if(args.length >= 1) {
//			if(args[0].equalsIgnoreCase("shopkeeper") || args[0].equalsIgnoreCase("sk")) {
//				String shopDataFile = "general_store";
//				String textureName = "sam_derric";
//				if(args.length >= 2) {
//					shopDataFile = args[1];
//					if(args.length >= 3)
//						textureName = args[2];
//				}
//				Entity e = new EntityHumanShopKeeper(sender.getEntityWorld(), shopDataFile, textureName);
//				e.setPosition(sender.getPosition().getX(), sender.getPosition().getY() + 1, sender.getPosition().getZ());
//				sender.getEntityWorld().addEntity(e);
//			}
//		}
//		
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
