package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommandSender;
/* Debug function - tests if you're an OP or not. */
public class CommandOpTest extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "whoop";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return true;
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		arg0.sendChatToPlayer("You " + (CommandsMain.isPowerUser(arg0.getCommandSenderName()) ? "are" : "are NOT") + " a power user.");
		
	}

}
