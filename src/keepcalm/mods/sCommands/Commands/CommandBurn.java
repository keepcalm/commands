package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;

/* set a player alight */
public class CommandBurn extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "burn";
	}
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return CommandsMain.isSinglePlayer ? true : CommandsMain.isPowerUser(sender.getCommandSenderName());
	}
	public String getCommandUsage(ICommandSender sender) {
		return "/burn <player>";
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length < 1) {
			throw new WrongUsageException("burn <player>");
		}
		EntityPlayerMP target = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(arg1[0]);
		arg0.sendChatToPlayer(target.username + " is burning for 5 seconds...");
		target.sendChatToPlayer(arg0.getCommandSenderName() + " burnt you!");
		
		target.setFire(5);
	}

}
