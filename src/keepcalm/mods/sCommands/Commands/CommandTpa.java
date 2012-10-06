package keepcalm.mods.sCommands.Commands;

import java.util.HashMap;
import java.util.List;

import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;

/*
 * TPA - teleport, but the player must accept.
 */

public class CommandTpa extends CommandBase {
	//TODO: needRequest.
	private boolean needRequest;
	public CommandTpa() {
		needRequest = true;
	}
	public CommandTpa(boolean ask) {
		needRequest = ask;
	}
	@Override
	public String getCommandName() {
		return "tpa";
	}
	public String getCommandUsage(ICommandSender guy) {
		return "/tpa <destination player> OR /tpa <target player> <X> <Y> <Z> OR /tpa <player to tp> <destination player>";
	}
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length < 3) {
			return getListOfStringsMatchingLastWord(args, ModLoader.getMinecraftServerInstance().getAllUsernames());
		}
		return null;
			
	}
	
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		if (var2.length == 1) {
			// 1 arg => tpa var1 -> target
			//getStringAsEntityPlayer();
			ServerConfigurationManager man = ModLoader.getMinecraftServerInstance().getConfigurationManager();
			EntityPlayerMP sender = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
			EntityPlayerMP target = man.getPlayerForUsername(var2[0]);
			if (target == null) {
				// oops...
				sender.addChatMessage("Sorry, but " + var2[0] + " is not on the server right now.");
			}
			else {
				sender.addChatMessage("Request sent.");		
				target.addChatMessage(sender.username + " wants to teleport to you. " + ChatColour.yellow + "/tpyes" + ChatColour.RESET + " to accept " + ChatColour.yellow + "/tpno" + ChatColour.RESET + " to deny." );
				/*double targetX = target.posX;
				double targetY = target.posY;
				double targetZ = target.posZ;*/
				CommandTpAcc.addToWaiting(sender, target);
			}
			
			
		}
		else if (var2.length == 2) {
			
			EntityPlayerMP from = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(var2[0]);
			EntityPlayerMP to = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(var2[1]);
			HashMap<EntityPlayerMP,boolean[]> map = new HashMap<EntityPlayerMP,boolean[]>();
			var1.sendChatToPlayer("Request sent.");
			from.sendChatToPlayer(var1.getCommandSenderName() + " wants to teleport you to " + to.username + ". " + ChatColour.yellow + "/tpyes" + ChatColour.RESET + " to accept, " + ChatColour.yellow + "/tpno" + ChatColour.RESET + " to refuse");
			to.sendChatToPlayer(var1.getCommandSenderName() + " wants to teleport " + from.username + " to you. " + ChatColour.yellow + "/tpyes" + ChatColour.RESET + " to accept, " + ChatColour.yellow + "/tpno" + ChatColour.RESET + " to refuse");
			boolean[] bool = new boolean[2];
			bool[0] = false;
			bool[1] = false;
			map.put(to, bool);
			CommandTpAcc.addToWaiting(from, map);
		
		}
		else if (var2.length == 4) {
			// teleport var2[0] to xyz from var2[1..3]
			ServerConfigurationManager man = ModLoader.getMinecraftServerInstance().getConfigurationManager();
			EntityPlayerMP sender = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
			EntityPlayerMP target = man.getPlayerForUsername(var2[0]);
			if (target == null) {
				sender.addChatMessage("Sorry, but " + var2[0] + " is not on the server right now.");
			}
			else {
				sender.addChatMessage("Request sent.");
				target.addChatMessage(sender.username + " wants to teleport you to " + var2[1] + "," + var2[2] + "," + var2[3] + ". &e/tpyes&r to accept &e/tpno&r to deny.");
			}
			
		}
		else {
			throw new WrongUsageException("/tpa <target player> <destination player> OR /tpa <target player> <X> <Y> <Z>");
		}

	}

}
