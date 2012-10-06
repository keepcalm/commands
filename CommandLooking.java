package keepcalm.mods.sCommands;

import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.Vec3;

// Debug class + command for CommandJump
/*lic class CommandLooking extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "getlook";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		EntityPlayerMP guy = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
		Vec3 look = guy.getLook(1.0F); 
		double X = guy.posX;
		double Y = guy.posY;
		double Z = guy.posZ;
		//guy.
		guy.
		float pitch = guy.rotationPitch;
		float yaw = guy.rotationYaw;
		guy.addChatMessage("You're looking at X: " + look.xCoord + " Y: " + (look.yCoord) + " Z: " + (look.zCoord));
		guy.addChatMessage("Your position is: X: " + X + " Y: " + Y + " Z: " + Z);
		guy.addChatMessage("Your rotation pitch is " + pitch + " Your rotation YAW is " + yaw);
		/* Interesting code here - from endermen */
		 //Vec3 var3 = guy.getLook(1.0F).normalize();
		 
         //Vec3 var4 = Vec3.getVec3Pool().getVecFromPool(this.posX - guy.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - (par1EntityPlayer.posY + (double)par1EntityPlayer.getEyeHeight()), this.posZ - par1EntityPlayer.posZ);
         //double var5 = var4.lengthVector();
         //var4 = var4.normalize();
         //double var7 = var3.dotProduct(var4);
	/*}
	public boolean canCommandSenderUseCommand(ICommandSender bob) {
		return true;
	}

}*/
