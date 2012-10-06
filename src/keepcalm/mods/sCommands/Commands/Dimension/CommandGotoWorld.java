package keepcalm.mods.sCommands.Commands.Dimension;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WrongUsageException;
/*
 * Goto a world
 */
public class CommandGotoWorld extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "worldgoto";
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		//DimensionManager.getWorld(id);
		if (arg1.length < 1 ) { arg1[0] = "overworld"; }
		int targId;
		try {
			targId = DimHelpers.findDimWithName(arg1[0]);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			throw new WrongUsageException("/worldgoto <worldname>");
		}
		catch (Exception e) {
			throw new CommandException("No such world - " + arg1[0]);
		}
		EntityPlayerMP player = (EntityPlayerMP) arg0;
		if (!DimHelpers.sendPlayerToDimension(targId, player)) {
			player.sendChatToPlayer("Dimension transfer failed.");
		}
	}

}
