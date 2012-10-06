package keepcalm.mods.sCommands.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
/*
 * Deny a TPA request
 */
public class CommandTpDeny extends CommandBase {
	private HashMap<EntityPlayerMP,EntityPlayerMP> theStateOfLife;
	private HashMap<EntityPlayerMP,int[]> theOtherSide;
	@Override
	public String getCommandName() {
		
		return "tpno";
	}
	public String getCommandUsage(ICommandSender guy) {
		return "/tpno";
	}
	//@SuppressWarnings("null")
	public List getCommandAliases() {
		List<String> x = new ArrayList<String> ();
		x.add("tpno");
		x.add("no");
		x.add("n");
		x.add("tpn");
		return x;
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		theStateOfLife = CommandTpAcc.getWaiting();
		theOtherSide = CommandTpAcc.getWaitingCoords();
		EntityPlayerMP target;
		try {
			target = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
		}
		catch (Exception e) {
			var1.sendChatToPlayer("Consoles don't teleport. Sorry!");
			return;
		}
		if (!theStateOfLife.containsKey(target) && !theOtherSide.containsKey(target) && !CommandTpAcc.isWaitingTwoWay(target)) {
			target.addChatMessage("You have no TP requests right now... sorry... :(");
		}
		else {
			target.addChatMessage("OK. Removing this TP request.");
			if (theStateOfLife.containsKey(target)) {
				theStateOfLife.get(target).addChatMessage("Your TP request was denied by " + target.username);
			}
			CommandTpAcc.removeFromWaiting(target);
		}
		

	}

}
