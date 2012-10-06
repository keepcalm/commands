package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.CommandsHelper;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3;
/*
 * Get where you're looking. Mostly just for testing.
 */
public class CommandsLook extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "look";
	}
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return true;
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		EntityPlayerMP target = (EntityPlayerMP) getCommandSenderAsPlayer(arg0);
		MovingObjectPosition pos = CommandsHelper.getPlayerTargetBlock(target, 128.0);
		
		//MovingObjectPosition pos = target.worldObj.rayTraceBlocks(target.getLook(6.0F), target.getPosition(1));
		target.addChatMessage("You are looking at: X " + pos.blockX + " Y " + pos.blockY + " Z " + pos.blockZ);
		
	}

}
