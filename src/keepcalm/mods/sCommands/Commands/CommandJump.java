package keepcalm.mods.sCommands.Commands;

import keepcalm.mods.sCommands.CommandsHelper;
import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;

/*
 * Jump to where you're pointing. It's a bit buggy if you're pointing below the horizon.
 */

public class CommandJump extends CommandBase {
	//@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "jump";
	}
	MinecraftServer server = ModLoader.getMinecraftServerInstance();
	public boolean canCommandSenderUseCommand(ICommandSender sender) {								// this is checking if the player is an Op
		if (server.isSinglePlayer() || server.getServerConfigurationManager(server).getOps().contains((EntityPlayer) getCommandSenderAsPlayer(sender))) {
			return true;
		}
		else {
			return false;
		}
	}

	//@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		EntityPlayerMP guy;
		try {
			guy = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
		}
		catch (Exception e) {
			var1.sendChatToPlayer("You need to be in-game to jump to a place...");
			return;
		}
		guy.noClip = true;
		MovingObjectPosition pos;
		try {
			 pos = CommandsHelper.getPlayerTargetBlock(guy, 128.0);
			 if (pos == null) {
				 throw new Exception(); // why not?
			 }
		}
		catch (Exception e) {
			guy.noClip = false;
			guy.addChatMessage(ChatColour.red + "Sorry, that's too far for me - I can only move you 128 blocks at a time. Aim lower.");
			e.printStackTrace();
			return;
		}
		guy.addChatMessage("Jumping...");
		try {
			Thread.sleep(CommandsMain.tpDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("[Commands] had a problem sleeping before a jump. Stack trace:");
			e.printStackTrace();
		}
		int targetX = pos.blockX;
		int targetY = pos.blockY;
		int targetZ = pos.blockZ;
		System.out.println("Jump to: X: " + targetX + " Y: " + targetY + " Z: " + targetZ);
		int i = 0;
		
		while (!guy.worldObj.canBlockSeeTheSky(targetX, targetY, targetZ)) {
			System.out.println("TP to: " + targetX + "," + targetY + "," + targetZ);
			targetY += 1;
			//targetY += 1;
			i += 1;
			
		}
		//targetY += 2;
		guy.setPositionAndUpdate(targetX, targetY, targetZ);
		guy.noClip = false;
	}
	
}
