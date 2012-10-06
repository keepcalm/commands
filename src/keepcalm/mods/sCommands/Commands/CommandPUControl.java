package keepcalm.mods.sCommands.Commands;

import java.util.ArrayList;
import java.util.List;

import keepcalm.mods.sCommands.CommandsMain;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.ModLoader;
import net.minecraft.src.WrongUsageException;
/* Control powerusers. */
public class CommandPUControl extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "powercontrol";
	}
	public String getCommandUsage(ICommandSender guy) {
		return "/powercontrol <add|remove> player";
	}
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (CommandsMain.isSinglePlayer) {
			return true;
		}
		else {
			return CommandsMain.isPowerUser(sender.getCommandSenderName());
		}
		
	}
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		List<String> t = new ArrayList<String> ();
		if (args.length == 0) {
			t.add("add");
			t.add("remove");
		}
		else if (args.length == 1) {
			String[] online = ModLoader.getMinecraftServerInstance().getAllUsernames();
			for (int i = 0; i < online.length; i++) {
				t.add(online[i]);
			}
		}
		else {
			t.add("<enter>");
		}
		return t;
	}
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		if (var2.length < 2) {
			throw new WrongUsageException("/powercontrol <add|remove> <user>");
		}
		if (var2[0] == "add") {
			CommandsMain.addPowerUser(var2[1], getCommandSenderAsPlayer(var1));
		}
		else if (var2[0] == "remove" || var2[0] == "delete" || var2[0] == "rm") {
			CommandsMain.rmPowerUser(var2[1], getCommandSenderAsPlayer(var1));
		}
	}

}
