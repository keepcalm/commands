package keepcalm.mods.sCommands.Commands;

import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommandSender;

/*
 * If you're forgetful...
 */
public class CommandWhoAmI extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "whoami";
	}
	public boolean canCommandSenderUseCommand(ICommandSender guy) {
		return true;
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		arg0.sendChatToPlayer("Your name is " + ChatColour.lightGreen + arg0.getCommandSenderName() + ChatColour.RESET + ".");
	}

}
