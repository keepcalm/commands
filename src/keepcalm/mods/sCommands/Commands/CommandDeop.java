package keepcalm.mods.sCommands.Commands;

import java.util.List;

import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;
/* Implementation of /deop for client games. This isn't registered when on a dedicated server. */
public class CommandDeop extends CommandBase {
	
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		
		return "deop";
	}
	public String getCommandUsage() {
		return "/deop <operator name>";
	}
	public List addTabCompleteOptions(ICommandSender guy, String[] args) {
		//List ret = new ArrayList<String>();
		 return args.length == 1 ? getListOfStringsFromIterableMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getOps()) : null;
		
	}
	public boolean canCommandSenderUseCommand(ICommandSender guy) {
		if (CommandsMain.isSinglePlayer) {
			return true;
		}
		else {
			return CommandsMain.isPowerUser(guy.getCommandSenderName());
		}
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length < 1) {
			throw new WrongUsageException("/deop <player>");
		}
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		
		EntityPlayerMP target = server.getConfigurationManager().getPlayerForUsername(arg1[0]);
		if (target != null) {
			target.addChatMessage("You are no longer Op!");
			server.getConfigurationManager().removeOp(arg1[0]);
			server.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(ChatColour.ITALIC + "[" + arg0.getCommandSenderName() + ": Deopping " + arg1[0] + " ]" ));
		}
		else {
			arg0.sendChatToPlayer(arg1[0] + " is not online.");
		}

	}

}
