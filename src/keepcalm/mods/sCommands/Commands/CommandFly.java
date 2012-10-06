package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.CommandsMain;
import keepcalm.mods.sCommands.ConHandler;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet202PlayerAbilities;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;
/* Take to the skies... */
public class CommandFly extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "fly";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return CommandsMain.isSinglePlayer ? true : CommandsMain.isPowerUser(arg0.getCommandSenderName());
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		
		EntityPlayerMP player;
		try {
			player = (EntityPlayerMP) getCommandSenderAsPlayer(arg0);
		}
		catch (Exception e) {
			if (arg1.length < 1) {
				throw new WrongUsageException("/fly <player>");
			}
			player = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(arg1[0]);
		}
		// toggle
		if (arg0.getCommandSenderName() != player.getEntityName()) {
			arg0.sendChatToPlayer("Flight " + (player.capabilities.allowFlying ? " disabled" : " enabled") + " for " + player.getEntityName() + ".");
		}
		FMLCommonHandler.instance().getMinecraftServerInstance().setAllowFlight(true);
		player.addChatMessage("Flight " + (player.capabilities.allowFlying ? " disabled." : " enabled. Double tap space-bar to fly!") );
		player.capabilities.allowFlying = (!player.capabilities.allowFlying);
		// this (theoretically) tells the client that it can fly/not fly.
		Packet202PlayerAbilities pack = new Packet202PlayerAbilities(player.capabilities);
		NetworkManager man = ConHandler.netman;
		man.addToSendQueue(pack);
		
		

	}

}
