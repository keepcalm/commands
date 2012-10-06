package keepcalm.mods.sCommands.Commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommand;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.ModLoader;

/* Test to see if the mod is working. */

public class CommandPing extends CommandBase {


	//@Override
	public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		
		List<String> list =new ArrayList<String> ();
		list.add("all");
		list.add("me");
		list.add("console");
		return list;
	}

	//@Override
	public boolean canCommandSenderUseCommand(ICommandSender var1) {
		// TODO Auto-generated method stub
		if (var1.getCommandSenderName().contains("Player") || ModLoader.getMinecraftServerInstance().getServerOwner() == var1.getCommandSenderName()) {
			return true;
		}
		return false;
	}

	//@Override
	public List getCommandAliases() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String> ();
		list.add("pong");
		list.add("potato");
		return list;
	}

	//@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "ping";
	}

	//@Override
	public String getCommandUsage(ICommandSender var1) {
		// TODO Auto-generated method stub
		return "/ping";
	}

	//@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		/*int i;
		for (i = 0; i < var2.length; i++) {
			var1.sendChatToPlayer("Argument " + (i + 1) + " was " + var2[i]);
		}*/
		var1.sendChatToPlayer(ChatColour.yellow + "Pong");
	}

}
