package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.CommandsMain;
import keepcalm.mods.sCommands.ConHandler;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet202PlayerAbilities;
/*
 * Gives you unlimited of an item. Broken, doesn't work.
 */
public class CommandUnlimited extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "unlimited";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return CommandsMain.isSinglePlayer ? true : CommandsMain.isPowerUser(arg0.getCommandSenderName());
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		EntityPlayerMP guy = (EntityPlayerMP) getCommandSenderAsPlayer(arg0);
		guy.capabilities.isCreativeMode = true;
		Packet202PlayerAbilities pack = new Packet202PlayerAbilities(guy.capabilities);
		NetworkManager man = ConHandler.netman;
		man.addToSendQueue(pack);
	}

}
