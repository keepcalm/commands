package keepcalm.mods.sCommands.Commands.Dimension;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.SaveHandler;
import net.minecraft.src.WorldServer;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;
import net.minecraft.src.WrongUsageException;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;
/*
 * Add a world
 */
public class CommandAddWorld extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "worldcreate";
	}
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return CommandsMain.isPowerUser(sender.getCommandSenderName());
	}
	public String getCommandUsage(ICommandSender sender) {
		return "/worldcreate <worldname> -g <generator> [-t type] [-i dimension id] [-s seed] [-m gamemode]";
	}
	public List addTabCompleteOptions(ICommandSender sender, String[] arg1) {
		List<String> ret = new ArrayList<String>();
		if (arg1.length == 1) {
			return null;
		}
		else if (arg1.length % 2 == 0) {
			ret.add("-g");
			ret.add("-t");
			ret.add("-i");
			ret.add("-s");
			ret.add("-m");
		}
		else if (arg1[-1] == "-g") {
			ret.add("overworld");
			ret.add("nether");
			ret.add("end");
			
		}
		else if (arg1[-1] == "-t") {
			ret.add("default");
			ret.add("superflat");
			ret.add("largebiomes");
		}
		else if (arg1[-1] == "-m") {
			ret.add("survival");
			ret.add("creative");
			ret.add("adventure");
		}
		return ret;
	}
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		if (arg1.length < 3) { throw new WrongUsageException("/worldcreate <worldname> -g <generator> [-t type] [-i dimension id] [-s seed] [-m gamemode]"); }
		String name = "world_custom"; // for now
		int gen = 0;
		long seed = System.currentTimeMillis();
		int id = -2;
		String worldtype = "default";
		EnumGameType gm = EnumGameType.SURVIVAL;
		List<String> args = new ArrayList<String>(); // for easier looping
		for (int i = 0; i < arg1.length; i++) {
			args.add(arg1[i]);
		}
		Iterator<String> it = args.iterator();
		String expect = "";
		int l = 0;
		while (it.hasNext()) {
			String arg = it.next();
			if (l == 0) {
				name = arg;
			}
			else {
				if (arg.startsWith("-") && expect.isEmpty()) {
					expect = arg;
				}
				else {
					// because Java 6 doesn't have string switches...
					if (expect == "-g") {
						if (arg.equalsIgnoreCase("overworld")) {
							gen = 0;
						}
						else if (arg.equalsIgnoreCase("nether")) {
							gen = -1;
						}
						else if (arg.equalsIgnoreCase("end")) {
							gen = 1;
						}
						else {
							try {
								gen = this.parseInt(arg0, arg);
							}
							catch (Exception e) {
								arg0.sendChatToPlayer("Invalid dimension number/name: " + arg + ". Choosing 'overworld'.");
								gen = 0;
							}
						}
					}
					else if (expect == "-t") {
						worldtype = arg;
					}
					else if (expect == "-i") {
						id = this.parseIntWithMin(arg0, arg, 15);
					}
					else if (expect == "-s") {
						seed = Long.parseLong(arg);
					}
					else if (expect == "-m") {
						gm = EnumGameType.getByName(arg);
					}
					expect = "";
				}
			}
			l++;
		}
		if (id == -2 || id < 15 ) {
			arg0.sendChatToPlayer("Invalid id. Finding another one...");
			Integer[] j = DimensionManager.getIDs();
			int highestUsed = -2;
			for (int i = 0; i < j.length; i++) {
				if (j[i] > highestUsed) {
					highestUsed = j[i];
				}
			}
			if (highestUsed < 15) {
				id = 15;
			}
			else {
				id = highestUsed;
			}
		}
		EntityPlayerMP targ = (EntityPlayerMP) getCommandSenderAsPlayer(arg0);
		SaveHandler save = new SaveHandler(new File(CommandsMain.configDir.getParentFile().getAbsolutePath()), name, true);
		//WorldInfo info = new WorldInfo();
		//Profiler worldp = new Profiler();
		
		WorldSettings settings = new WorldSettings(seed,gm, true, false, WorldType.parseWorldType(worldtype));
		WorldServer newWorld = new WorldServer(FMLCommonHandler.instance().getMinecraftServerInstance(), save, name, gen, settings, MinecraftServer.getServer().theProfiler);
		//DimensionManager.registerDimension(id, gen);
		DimensionManager.createProviderFor(id);
		//DimensionManager.getProvider(id).terrainType = WorldType.parseWorldType(worldtype);
		//DimensionManager.getProvider(id).worldObj.getWorldInfo().setGameType(gm);
		DimensionManager.getProvider(id).registerWorld(newWorld);
		DimHelpers.addDim(id);
		
		arg0.sendChatToPlayer("Success! World " + name + " created.");
		//WorldServerMulti j = new WorldServerMulti(null, null, expect, l, null, j, null);
		//DimensionManager.setWorld(id, newWorld);
		
		//DimensionManager.registerDimension(id, gen);
		//MinecraftServer.getServer().loadAllWorlds(expect, expect, seed, null);
	}

}
