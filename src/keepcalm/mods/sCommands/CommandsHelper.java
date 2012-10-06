package keepcalm.mods.sCommands;

import java.util.Iterator;

import keepcalm.mods.sCommands.Commands.CommandJailAdd;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.Vec3;
import cpw.mods.fml.common.FMLCommonHandler;
/*
 * Helper functions for Commands
 */
public class CommandsHelper {
	/*
	 * Get the player target block. Works on both client-side and server-side.
	 * @param target - the EntityPlayer to find the target block of
	 * @double maxDist - the distance to raytrace for.
	 */
	public static MovingObjectPosition getPlayerTargetBlock(EntityPlayerMP target, double maxDist) throws NullPointerException {
		double par1 = maxDist;
		// ^ distance
	  //\\ but what's a partialTickTime ?
		float par3 = 0.0F;
		Vec3 j = Vec3.createVectorHelper(target.posX, target.posY, target.posZ);
		//Vec3 var4 = target.getPosition(par3);
        Vec3 var5 = target.getLook(par3);
        Vec3 var6 = j.addVector(var5.xCoord * par1, var5.yCoord * par1, var5.zCoord * par1);
        MovingObjectPosition pos = target.worldObj.rayTraceBlocks(j, var6);
        return pos;
	}
	/*
	 * Get the player target block. Works on both client-side and server-side. Limited to 64 blocks when used like this.
	 * @param target - the EntityPlayer to find the target block of
	 */
	public static MovingObjectPosition getPlayerTargetBlock(EntityPlayerMP target) throws NullPointerException {
		return getPlayerTargetBlock(target, 64);
	}
	/*
	 * Load the jails from the overworld's NBT.
	 * 
	 */
	public static void loadJails() {
		NBTTagCompound ret;// = new NBTTagCompound();
		NBTTagCompound base;
		//DataInputStream in;
		//FileInputStream jin;
		//File jails = new File(CommandsMain.configDir.getAbsolutePath() + "/jails.dat");
		try {
			//jin = new FileInputStream(jails);
			//in = new DataInputStream(jin);
			ret = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0).getWorldInfo().getNBTTagCompound();
			base = ret.getCompoundTag("JAILS");
			if (base == null) {
				
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		NBTTagList keys = base.getTagList("KEYS");
		//List<String> ks = new ArrayList<String>();
		for (int i = 0; i < keys.tagCount(); i++) {
			CommandJailAdd.jails.put(keys.tagAt(i).getName(), base.getIntArray(keys.tagAt(i).getName()));
		}
		
	}
	/*
	 * Save the jails to the Overworld's NBT
	 */
	public static void saveJails() {
		NBTTagCompound ret;// = new NBTTagCompound();
		NBTTagCompound base = new NBTTagCompound();
		NBTTagList keys = new NBTTagList();
		Iterator it = CommandJailAdd.jails.keySet().iterator();
		while (it.hasNext()) {
			String nk = (String) it.next();
			keys.appendTag(new NBTTagString(nk));
			base.setIntArray(nk, CommandJailAdd.jails.get(nk));
			
		}
		base.setTag("KEYS", keys);
		MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo().getNBTTagCompound().setCompoundTag("JAILS", base);
		
	}
}
