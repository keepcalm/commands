package keepcalm.mods.sCommands;




/*
 * Commands - adds some commands to minecraft. Will one day maybe become Essentials-like
 * Works on SMP and SSP (with cheats enabled). For a bukkit version, see Essentials (http://dev.bukkit.org/server-mods/essentials)
 * 
 * Requires MinecraftForge
 * 
 * (C) 2012 KeepCalm
 * 
 * Distributed with no warranty. Ifyou want to use this code, you may, so long as you (A) credit me in some way and
 * (B) re-release any of my source code that you modified.
 * 
 * But please, don't copy it line-for-line and say it's yours.
 * 
 * Thanks to whoever made the SimpleCommands mod - I didn't know how to register a command until I saw your source code.
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import keepcalm.mods.sCommands.Commands.CommandBack;
import keepcalm.mods.sCommands.Commands.CommandBigTree;
import keepcalm.mods.sCommands.Commands.CommandBurn;
import keepcalm.mods.sCommands.Commands.CommandDeop;
import keepcalm.mods.sCommands.Commands.CommandFireball;
import keepcalm.mods.sCommands.Commands.CommandFly;
import keepcalm.mods.sCommands.Commands.CommandJail;
import keepcalm.mods.sCommands.Commands.CommandJailAdd;
import keepcalm.mods.sCommands.Commands.CommandJailFree;
import keepcalm.mods.sCommands.Commands.CommandJailList;
import keepcalm.mods.sCommands.Commands.CommandJailRemove;
import keepcalm.mods.sCommands.Commands.CommandJump;
import keepcalm.mods.sCommands.Commands.CommandMute;
import keepcalm.mods.sCommands.Commands.CommandOp;
import keepcalm.mods.sCommands.Commands.CommandPUControl;
import keepcalm.mods.sCommands.Commands.CommandPing;
import keepcalm.mods.sCommands.Commands.CommandServerTp;
import keepcalm.mods.sCommands.Commands.CommandStrike;
import keepcalm.mods.sCommands.Commands.CommandTpAcc;
import keepcalm.mods.sCommands.Commands.CommandTpDeny;
import keepcalm.mods.sCommands.Commands.CommandTpa;
import keepcalm.mods.sCommands.Commands.CommandWhoAmI;
import keepcalm.mods.sCommands.Commands.CommandsLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICommandManager;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ServerCommandManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
//import net.minecraft.client.Minecraft;

@Mod(modid="keepcalm-Commands",name="Commands",version="1.3.2-1")
@NetworkMod(serverSideRequired=false, clientSideRequired=false , channels={"CommandsInst"}, packetHandler= CustomPacketHandler.class)
public class CommandsMain {
	@Instance("keepcalm-Commands")
	public static CommandsMain main;
	/* A list of all power users */
	public static List<String> powerUsers;
	/* Is the server singleplayer? (set to false when someone logs in on a real SP world) */
	public static boolean isSinglePlayer;
	/* The owner of the SP session */
	public static String username;
	/* A list of strings containing the MOTD - loaded in preinit. */
	public static List<String> motd = new ArrayList<String>();
	/* The config file */
	public static File configFile;
	/* The folder containing the MOTD and maybe other stuff */
	public static File configDir;
	/* The tpdelay - loaded in preinit */
	public static long tpDelay;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent ev) {
		configFile = ev.getSuggestedConfigurationFile();
		configDir = new File(ev.getModConfigurationDirectory().getAbsolutePath() + "/commands");
		
		//System.out.println("The mod config dir is " + configDir.getAbsolutePath());
		Configuration config = new Configuration(ev.getSuggestedConfigurationFile());
		config.load();
		Property prop = config.get(Configuration.CATEGORY_GENERAL, "tpDelay", 0);
		prop.comment = "The delay before teleporting someone, in seconds. You can use up to 3 decimal places.";
		long temp = (long) prop.getInt();
		temp *= 1000;
		tpDelay = temp;
		config.save();
		configDir.mkdir();
		File motd = new File( configDir.getAbsolutePath() + "/motd.txt" );
		FileReader input;
		BufferedReader buf;
		for (int i = 0; i ==0; i++) { // just so we can break;
			if (motd.exists()) {
				try {
					input = new FileReader(motd);
				} catch (Exception e) {
					FMLCommonHandler.instance().getFMLLogger().warning("Failed to open motd file - " + e.getMessage());
					break;
				}
				buf = new BufferedReader(input);
				
				String line = "2";
				String msg = "";
				boolean ampIsSafe = false;
				try {
					while (line != null) {
						
						 line = buf.readLine();
						for (int j = 0; j < line.length(); j++) {
							String temp1 = line.charAt(j) + "";
							//System.out.println(c + " => " + temp1);
							if (temp1.equals("&") && !ampIsSafe ) {
								temp1 = ChatColour.colourSign;
								ampIsSafe = false;
							}
							if (ampIsSafe == true) {
								ampIsSafe = false;
							}
							if (temp1.equals("\\")) {
								ampIsSafe = true;
							}
							
							msg += temp1;
							
						}
						System.out.println(msg);
						this.motd.add(msg);
						
					}
					
					//System.out.print("\n");
				} 
				catch (Exception e) {
					FMLCommonHandler.instance().getFMLLogger().warning("Failed to read data from motd file - " + e.getMessage());
					break;
				}
			} // end of if(motd.exists())
			
		}
	}
	
	@Init
	public void Init(FMLInitializationEvent ev) {
		System.out.println("[Commands]: Registering network listeners...");
		NetworkRegistry.instance().registerChatListener(new ChatListener());
		//MinecraftForge.EVENT_BUS.register(new PlayerLoginListener());
		MinecraftForge.EVENT_BUS.register(new CommandLogger());
		NetworkRegistry.instance().registerConnectionHandler(new ConHandler());
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			isSinglePlayer = true;
			username = FMLClientHandler.instance().getClient().session.username;
		}
		else {
			isSinglePlayer = false;
			username = "server";
		}
		powerUsers = new ArrayList<String> ();
	}
	
	
	@ServerStarting
	public void serverStart(FMLServerStartingEvent ev) {
		MinecraftServer server = ModLoader.getMinecraftServerInstance();
		isSinglePlayer = server.isSinglePlayer();
		// does it all by itself
		CommandsHelper.loadJails();
		
		ICommandManager manager = server.getCommandManager();
		ServerCommandManager scm = ((ServerCommandManager) manager);
		registerCommands(scm);
	}
	/* 
	 * Register all the commands.
	 * @param man - the ServerCommandManager (which can be gotten by casting server.getCommandManager()) to register the commands with.
	 */
	// TODO: allow disabling of commands.
	public void registerCommands(ServerCommandManager man) {
		
		man.registerCommand(new CommandPing());
		man.registerCommand(new CommandMute());
		man.registerCommand(new CommandTpa());
		man.registerCommand(new CommandTpAcc());
		man.registerCommand(new CommandTpDeny());
		man.registerCommand(new CommandPUControl());
		man.registerCommand(new CommandBigTree());
		man.registerCommand(new CommandServerTp());
		man.registerCommand(new CommandBack());
		// and now for something completely different!
		if (isSinglePlayer) {
			man.registerCommand(new CommandOp());
			man.registerCommand(new CommandDeop());
		}
		man.registerCommand(new CommandFly());
		man.registerCommand(new CommandsLook());
		man.registerCommand(new CommandBurn());
		man.registerCommand(new CommandJump());
		man.registerCommand(new CommandStrike());
		man.registerCommand(new CommandWhoAmI());
		man.registerCommand(new CommandJail());
		man.registerCommand(new CommandJailAdd());
		man.registerCommand(new CommandJailRemove());
		man.registerCommand(new CommandJailList());
		man.registerCommand(new CommandJailFree());
		man.registerCommand(new CommandFireball());
		
		/* Broken/working out stuff */
		//man.registerCommand(new CommandCCTest());
		//man.registerCommand(new CommandUnlimited());
		//man.registerCommand(new CommandOpTest());
		//man.registerCommand(new CommandNoclip());
		//man.registerCommand(new CommandLooking());
	}
	/* Power user stuff */
	public static boolean addPowerUser(String username, EntityPlayer player) {
		if (powerUsers.contains(username)) {
			player.addChatMessage(username + " is already a power user!");
			return false;
		}
		player.addChatMessage("Add user " + username + " to power users");
		powerUsers.add(username);
		return true;
	}
	public static boolean addPowerUserSilently(String username) {
		if (powerUsers.contains(username)) {
			//player.addChatMessage(username + " is already a power user!");
			return false;
		}
		//player.addChatMessage("Add user " + username + " to power users");
		powerUsers.add(username);
		return true;
	}
	public static boolean rmPowerUser(String username, EntityPlayer player) {
		if (powerUsers.contains(username)) {
			powerUsers.remove(username);
			player.addChatMessage("Commands: " + username + " is not a power user anymore!");
			return true;
		}
		else {
			player.addChatMessage("Commands: " + username + " was not a power user, so not removed.");
			return false;
		}
	}
	public static boolean isPowerUser(String username) {
		//String[] str = new String[999];
		//Iterator it = ModLoader.getMinecraftServerInstance().getConfigurationManager().getOps().iterator();
		//System.out.println("Ops: ");
		//while (it.hasNext()) {
			//System.out.print(", ");
			//String str = (String) it.next();
			//System.out.print(str);
	//	}
		//System.out.println(CommandPing.joinString(ModLoader.getMinecraftServerInstance().getConfigurationManager().getOps().toArray(str), 0));
		if (ModLoader.getMinecraftServerInstance().getConfigurationManager().getOps().contains(username) || ModLoader.getMinecraftServerInstance().isSinglePlayer()) { // include ops and SP games
			
			return true;
		}
		try {
			if( powerUsers.contains(username)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			ModLoader.getLogger().log(Level.WARNING, "Commands had an issue checking if " + username + " is a power user. The error was " + e.getMessage());
			return false;
		}
	}
}
