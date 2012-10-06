package keepcalm.mods.sCommands.Commands;

import java.util.HashMap;
import java.util.Map;

import keepcalm.mods.sCommands.CommandsHelper;
import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WrongUsageException;
/*
 * Create a jail
 */
public class CommandJailAdd extends CommandBase {
	public static Map<String,int[]> jails = new HashMap<String,int[]>();
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "setjail";
	}
	public String getCommandUsage(ICommandSender sender) {
		return "/setjail <name>";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		if (CommandsMain.isSinglePlayer) {
			return true;
		}
		else {
			return CommandsMain.isPowerUser(arg0.getCommandSenderName());
		}
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length < 1) {
			throw new WrongUsageException("/setjail <name>");
		}
		int[] pos = new int[4];
		EntityPlayerMP player;
		try {
			player = (EntityPlayerMP) getCommandSenderAsPlayer(arg0);
		}
		catch (Exception e) {
			throw new CommandException("Need to be in-game to make a jail!");
		}
		pos[0] = (int) player.posX;
		pos[1] = (int) player.posY;
		pos[2] = (int) player.posZ;
		pos[3] = player.worldObj.getWorldInfo().getDimension();
		jails.put(arg1[0], pos);
		CommandsHelper.saveJails();
		arg0.sendChatToPlayer("Jail added");
		
	}

}
