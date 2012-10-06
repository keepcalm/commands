package keepcalm.mods.sCommands.Commands;

import java.util.HashMap;

import keepcalm.mods.sCommands.CommandsMain;
import keepcalm.mods.sCommands.ConHandler;
import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet202PlayerAbilities;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;
/* Jail a player */
public class CommandJail extends CommandBase {
	public static HashMap<String,double[]> locBeforeJail = new HashMap<String,double[]>();
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "jail";
	}
	public String getCommandUsage(ICommandSender var1) {
		return "/jail <jail name> <player>";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return CommandsMain.isSinglePlayer ? true : CommandsMain.isPowerUser(arg0.getCommandSenderName());
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length < 2 ) {
			throw new WrongUsageException("/jail <jail name> <player>");
		}
		if (!CommandJailAdd.jails.containsKey(arg1[0])) {
			throw new CommandException("Not a valid jail");
		}
		EntityPlayerMP target = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(arg1[1]);
		if (target == null) {
			arg0.sendChatToPlayer("No such player!");
			return;
		}
		double[] tpos = {target.posX, target.posY, target.posZ, target.worldObj.getWorldInfo().getDimension()};
		locBeforeJail.put(target.username, tpos);
		target.capabilities.allowEdit = false;
		target.capabilities.allowFlying = false;
		target.capabilities.isCreativeMode = false;
		if (target.worldObj.getWorldInfo().getDimension() != CommandJailAdd.jails.get(arg1[0])[3]) {
			FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension(target, CommandJailAdd.jails.get(arg1[0])[3]);
		}
		target.setPositionAndUpdate(CommandJailAdd.jails.get(arg1[0])[0], CommandJailAdd.jails.get(arg1[0])[1], CommandJailAdd.jails.get(arg1[0])[2]);
		Packet202PlayerAbilities pack = new Packet202PlayerAbilities(target.capabilities);
		NetworkManager man = ConHandler.netman;
		man.addToSendQueue(pack);
		target.sendChatToPlayer("You have been jailed!");
		arg0.sendChatToPlayer("Jailed.");
		
	}

}
