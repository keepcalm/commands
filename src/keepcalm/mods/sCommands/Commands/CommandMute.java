package keepcalm.mods.sCommands.Commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.ModLoader;
import net.minecraft.src.WrongUsageException;
/*
 * Mute a player, via the chat listener
 */
public class CommandMute extends CommandBase {
	public static List<String> muted = new ArrayList<String> ();
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "mute";
	}
	public String getCommandUsage(ICommandSender guy) {
		return "/mute <player>";
	}
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		
		return getListOfStringsMatchingLastWord(args, ModLoader.getMinecraftServerInstance().getAllUsernames());
	}
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		/*var1.sendChatToPlayer("&eUnimplemented&r");
		return;*/
		
		
		EntityPlayerMP dud = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
		if (var2.length < 1) {
			throw new WrongUsageException("mute <player>");
			//return;
		}
		String target = var2[0];
		MinecraftServer server = ModLoader.getMinecraftServerInstance();
		String[] online = server.getAllUsernames();
		
		boolean foundUser = false;
		for (int i = 0; i < online.length; i++) {
			if (online[i] == target) {
				foundUser = true;
			}
		}
		if (isMuted(target)) {
			// found the user
			rmFromMuted(target);
			dud.addChatMessage("Unmuted " + target);
		}
		else if (!isMuted(target)) {
			addToMuted(target);
			dud.addChatMessage("Muted " + target);
		}/*
		else if (isMuted(target)) {
			rmFromMuted(target);
			dud.addChatMessage("Unmuted " + target);
		}
		else {
			dud.addChatMessage(target + " isn't online and isn't muted!");
		}*/
		
	}
	public void addToMuted(String target) {
		muted.add(target);
	}
	public void rmFromMuted(String target) {
		muted.remove(target);
	}
	public static boolean isMuted(String username) {
		// TODO Auto-generated method stub
		return muted.contains(username);
	}

}
