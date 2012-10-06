package keepcalm.mods.sCommands.Commands;

import java.util.HashMap;

import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;

/*
 * Allows the player to go back to their previous location. Upon use, it sets their previous co-ords to the ones that they had
 * BEFORE using /back.
 * 
 * i.e. - /back twice, and you'd end up in the same place.
 */
public class CommandBack extends CommandBase {
	private static HashMap<String,double[]> lastLoc = new HashMap<String,double[]>();
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "back";
	}
	public boolean canCommandSenderUseCommand(ICommandSender guy) {
		return true;
	}
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		EntityPlayerMP guy = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
		String name = guy.username;
		double[] coords;
		double lastX = guy.posX;
		double lastY = guy.posY;
		double lastZ = guy.posZ;
		coords = lastLoc.get(name);
		setLastLoc(name, lastX, lastY, lastZ);
		if (coords != null) {
			guy.setPositionAndUpdate(coords[0], coords[1], coords[2]);
		}
		else {
			guy.sendChatToPlayer("You haven't TP'd anywhere during this session.");
		}
		
	}
	public static void setLastLoc(String playerName, double X, double Y, double Z) {
		double[] coords = new double[3] ;
		//coords[0] = X;
		coords[0] = X;
		coords[1] = Y;
		coords[2] = Z;
		lastLoc.put(playerName, coords);
	}
	public static double[] getLastLoc(String playerName) {
		return lastLoc.get(playerName);
	}

	public static void clearPlayerData(String username) {
		// TODO Auto-generated method stub
		lastLoc.remove(username);
		
	}

}
