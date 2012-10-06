package keepcalm.mods.sCommands.Commands;

import java.util.Iterator;

import keepcalm.mods.sCommands.CommandsMain;

import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
/*
 * List jails
 */
public class CommandJailList extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "listjails";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return CommandsMain.isSinglePlayer ? true : CommandsMain.isPowerUser(arg0.getCommandSenderName());
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		String[] t = new String[CommandJailAdd.jails.keySet().size()];
		t = CommandJailAdd.jails.keySet().toArray(t);
		arg0.sendChatToPlayer(this.joinNiceString(t));
	}

}
