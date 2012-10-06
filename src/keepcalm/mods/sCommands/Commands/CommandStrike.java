package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.CommandsHelper;
import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;

/*
 * Add some lightning where you're looking.
 */

public class CommandStrike extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "strike";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return CommandsMain.isSinglePlayer ? true : CommandsMain.isPowerUser(arg0.getCommandSenderName());
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		EntityPlayerMP guy;
		try {
			guy = (EntityPlayerMP) getCommandSenderAsPlayer(arg0);
		}
		catch (Exception e) {
			if (arg1.length < 1) {
				throw new WrongUsageException("/strike <player>");
			}
			else {
				guy = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(arg1[0]);
			}
			if (guy == null) {
				throw new CommandException("No such player");
			}
		}
		arg0.sendChatToPlayer("Striking...");
		if (arg1.length < 1) {
			// use targeting
			MovingObjectPosition pos;
			try {
				pos = CommandsHelper.getPlayerTargetBlock(guy);
			}
			catch(Exception e) {
				arg0.sendChatToPlayer("Aim closer!");
				return;
			}
			EntityLightningBolt lightning = new EntityLightningBolt(guy.worldObj, pos.blockX, pos.blockY, pos.blockZ);
			guy.worldObj.addWeatherEffect(lightning);
		}
		else {
			// strike player
			EntityLightningBolt lightning = new EntityLightningBolt(guy.worldObj, guy.posX, guy.posY, guy.posZ);
			guy.worldObj.addWeatherEffect(lightning);
		}
		
	}

}
