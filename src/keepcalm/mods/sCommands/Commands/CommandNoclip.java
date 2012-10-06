package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.ConHandler;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet202PlayerAbilities;
import net.minecraft.src.Packet204ClientInfo;
import net.minecraft.src.PlayerNotFoundException;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;
/*
 * More of an experiment. Doesn't work.
 */
public class CommandNoclip extends CommandBase {
	private boolean allowNC = true;
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "noclip";
	}

	@Override
	// noclipping doesn't work - the client doesn't let you noclip. How to update player props?
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length > 0) {
			if (arg1[0] == "enable" || arg1[0] == "disable") {
				allowNC = (arg1[0] == "enable" ? true : false);
				return;
			}
		}
		if (allowNC == false) {
			arg0.sendChatToPlayer("NoClip is disabled currently.");
		}
		EntityPlayerMP target;
		try {
			target = (EntityPlayerMP) getCommandSenderAsPlayer(arg0);
		}
		catch(PlayerNotFoundException e) {
			if (arg1.length == 0) {
				throw new WrongUsageException("/noclip <player>");
			}
			else {
				target = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(arg1[0]);
			}
		}
		if (target.noClip) {
			target.sendChatToPlayer("Disabled noclip");
			if (target.username != arg0.getCommandSenderName()) {
				arg0.sendChatToPlayer("Disabled noclip for " + arg1[0]);
			}
			target.capabilities.disableDamage = true;
			target.noClip = false;
			target.capabilities.allowFlying = false;
			// this (theoretically) tells the client that it can't noclip.
			Packet202PlayerAbilities pack = new Packet202PlayerAbilities(target.capabilities);
			NetworkManager man = ConHandler.netman;
			man.addToSendQueue(pack);
			
		}
		else {
			target.sendChatToPlayer("Noclip enabled!");
			if (target.username != arg0.getCommandSenderName()) {
				arg0.sendChatToPlayer("Enabled noclip for " + arg1[0]);
			}
			target.capabilities.allowFlying = true;
			target.capabilities.isFlying = true;
			target.noClip = true;
			// this (theoretically) tells the client that it can noclip.
			Packet202PlayerAbilities pack = new Packet202PlayerAbilities(target.capabilities);
			NetworkManager man = ConHandler.netman;
			man.addToSendQueue(pack);
			
		}
		
	}

}
