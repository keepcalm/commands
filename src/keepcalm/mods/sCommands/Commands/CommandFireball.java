package keepcalm.mods.sCommands.Commands;

import java.util.Random;

import keepcalm.mods.sCommands.CommandsHelper;
import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityFireball;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
/* Shoot a fireball - at present, shoots in the wrong direction. */
public class CommandFireball extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "fireball";
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
			throw new CommandException("Only players can throw fireballs...");
		}
		MovingObjectPosition pos = null;
		EntityFireball fire;
		try {
			pos = CommandsHelper.getPlayerTargetBlock(guy, 1024);
			double xAccel = guy.posX - pos.blockX;
			double yAccel = guy.posY - pos.blockY;
			double zAccel = guy.posZ - pos.blockZ;
			fire = new EntityFireball(guy.worldObj, guy, xAccel, yAccel, zAccel);
			fire.setLocationAndAngles(guy.posX, guy.posY, guy.posZ, guy.cameraPitch, guy.cameraYaw);
		}
		catch (Exception e) {
			
			fire = new EntityFireball(guy.worldObj);
			
			fire.setLocationAndAngles(guy.posX, guy.posY, guy.posZ, guy.cameraPitch, guy.cameraYaw);
			Random rand = new Random();
			double par3 = 0;
			double par5 = 0;
			double par7 = 0;
			par3 += rand.nextGaussian() * 0.4D;
	        par5 += rand.nextGaussian() * 0.4D;
	        par7 += rand.nextGaussian() * 0.4D;
	        double var9 = (double)MathHelper.sqrt_double(par3 * par3 + par5 * par5 + par7 * par7);
	        fire.accelerationX = par3 / var9 * 0.1D;
	        fire.accelerationY = par5 / var9 * 0.1D;
	        fire.accelerationZ = par7 / var9 * 0.1D;
		}
		
		
		guy.worldObj.spawnEntityInWorld(fire);
	}

}
