package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.ChatColour;
import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommandSender;
/*Chat colour test. For some reason it doesn't work when grouping chat colours.*/
public class CommandCCTest extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "chattest";
	}
	public String getCommandUsage() {
		return "/chattest";
	}
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		var1.sendChatToPlayer(ChatColour.BOLD + "Bold" + ChatColour.pink + " bold pink" + ChatColour.RESET + " normal");
	}

}
