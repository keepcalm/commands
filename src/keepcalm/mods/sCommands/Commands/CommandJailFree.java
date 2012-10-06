package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.Commands.Dimension.DimHelpers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.MathHelper;
import net.minecraft.src.WrongUsageException;
/*
 * Free a player from jail
 */
public class CommandJailFree extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "unjail";
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length < 1) {
			throw new WrongUsageException("/unjail <convict>");
		}
		if (!CommandJail.locBeforeJail.containsKey(arg1[0])) {
			arg0.sendChatToPlayer(arg1[0] + " is not jailed.");
		}
		double[] oldPos = CommandJail.locBeforeJail.get(arg1[0]);
		int targDim = (int) MathHelper.floor_double(oldPos[-1]);
		EntityPlayerMP targ = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(arg1[0]);
		if (targ == null) {
			throw new CommandException("No such player");
		}
		if (targDim != targ.worldObj.getWorldInfo().getDimension()) {
			DimHelpers.sendPlayerToDimension(targDim, targ);
		}
		targ.setPositionAndUpdate(oldPos[0], oldPos[1], oldPos[2]);
		targ.sendChatToPlayer("You're free!");
		arg0.sendChatToPlayer("Freed.");
		
	}

}
