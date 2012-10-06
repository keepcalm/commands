package keepcalm.mods.sCommands.Commands;

import java.util.List;

import keepcalm.mods.sCommands.CommandsHelper;
import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WrongUsageException;
/*
 * remove a jail
 */
public class CommandJailRemove extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "deljail";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return CommandsMain.isSinglePlayer ? true : CommandsMain.isPowerUser(arg0.getCommandSenderName());
	}
	public List addTabCompletionOptions(ICommandSender arg0, String[] arg1) {
		String[] j = new String[CommandJailAdd.jails.size()];
		return this.getListOfStringsMatchingLastWord(arg1, CommandJailAdd.jails.keySet().toArray(j));
		
		//return ret;
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length < 1) {
			throw new WrongUsageException("/deljail <jail name>");
		}
		CommandJailAdd.jails.remove(arg1[0]);
		CommandsHelper.saveJails();
	}

}
