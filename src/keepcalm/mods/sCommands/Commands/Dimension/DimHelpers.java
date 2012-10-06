package keepcalm.mods.sCommands.Commands.Dimension;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NBTTagCompound;
/*
 * Miscellaneous helpers for dimension work.
 */
public class DimHelpers {
	/*
	 * Save a dimension to NBT.
	 * @param id - the dimension ID to add
	 */
	public static void addDim( int id) {
		NBTTagCompound mcnbt = MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo().getNBTTagCompound();
		int[] dimids;
		dimids = mcnbt.getIntArray("COMMAND_DIMS");
		dimids[dimids.length] = id;
		mcnbt.setIntArray("COMMAND_DIMS", dimids);
		MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo().getNBTTagCompound().setIntArray("COMMAND_DIMS", dimids);
	}
	/*
	 * find a dimension with the given name
	 * @throws Exception
	 * @param name - the name of the dimension.
	 */
	public static int findDimWithName(String name) throws Exception {
		NBTTagCompound mcnbt = MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo().getNBTTagCompound();
		int[] dimids;
		dimids = mcnbt.getIntArray("COMMAND_DIMS");
		if (name.equalsIgnoreCase("overworld") || name.equalsIgnoreCase("world")) {
			return 0;
		}
		else if (name.equalsIgnoreCase("nether")) {
			return -1;
		}
		else if (name.equalsIgnoreCase("end")) {
			return 1;
		}
		for (int i = 0; i < dimids.length; i++) {
			if (MinecraftServer.getServer().worldServerForDimension(dimids[i]).getWorldInfo().getWorldName().equalsIgnoreCase(name)) {
				return dimids[i];
			}
		}
		throw new Exception("No dimension found for " + name);
	}
	
	/*
	 * returns the name of the Dimension given
	 * @param id - the id of the dimension to lookup
	 */
	public static String getDimName(int id) {
		return MinecraftServer.getServer().worldServerForDimension(id).getWorldInfo().getWorldName();
	}
	/*
	 * returns false if the transfer failed
	 * @param dimId - the target dimension
	 * @param player - the player to transfer
	 */
	public static boolean sendPlayerToDimension(int dimId, EntityPlayerMP player) {
		int cId = player.worldObj.getWorldInfo().getDimension();
		MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, dimId);
		if (player.worldObj.getWorldInfo().getDimension() == cId) {
			return false;
		}
		return true;
	}

	
}
