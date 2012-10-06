package keepcalm.mods.sCommands;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
/*
 * Commands custom packet handler. Currently is only used for 
 */
public class CustomPacketHandler implements IPacketHandler {
	private static HashMap<String,boolean[]> usersWithMod = new HashMap<String,boolean[]>();
	public String lastUserRequested;
	//public NetworkManager manager;
	//TODO @SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(NetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		EntityPlayer dude = (EntityPlayer) player;
		System.out.println("Got message on channel " + packet.channel);
		if (packet.channel.equalsIgnoreCase("CommandsInst")) {
			ByteArrayInputStream bos = new ByteArrayInputStream(packet.data, 0, packet.length);
			DataInputStream out = new DataInputStream(bos);
			String data = "";
			try {
				
			
				for (int i = 0; i < packet.length; i++) {
					data += out.readChar();
				}
			}
			catch (Exception e) {
				// ignore it:)
			}
			
			System.out.println("The message is " + data);
			if (data.equalsIgnoreCase("Anybody there?")) {
				Side side = FMLCommonHandler.instance().getEffectiveSide();
				if (side == Side.SERVER || side == Side.BUKKIT /* you never know... I probably won't port it over... */) {
					// ignore it
					//return;
					//FMLServerHandler.instance().
					return;
				}
				String username = null;
				for (int i = 0; username == null; i++) {
					switch(i) {
					case 0:
						try {
							username = dude.username;
						}
						catch(Exception e) {
							
						}
						
					case 1:
						try {
							username = ModLoader.getMinecraftInstance().thePlayer.username;
						}
						catch(Exception e) {}
					case 2:
						username = CommandsMain.username;
					}
				}
				System.out.println("Informing server we have the mod...");
				Packet250CustomPayload response = CommandsPackets.createNewPacket("CommandsInst", "hascommands " + username);
				manager.addToSendQueue(response);
				return;
			}
			else if (data.startsWith("hascommands")) {
				String[] info = data.split(" ");
				//EntityPlayer dude = (EntityPlayer) player;
				String username;
				if (dude == null) {
					// ??? how does this happen?
					System.out.println("Ach! Can't get the username!");
					System.out.println("Using " + info[1] + " instead...");
					username = info[1];
				}
				else {
					username = dude.username;
				}
				System.out.println(username + " has got commands!");
				boolean[] yes = new boolean[1];
				yes[0] = true;
				usersWithMod.put(username, yes);
				System.out.println("[SERVER]: " + username + " has commands installed!");
			}
			// ok! 
			//ConHandler.isCommandsOnClient = true;
			
		}

		
	}
	public static boolean hasMod(String username) {
		if (CommandsMain.isSinglePlayer) {
			System.out.println("This is singleplayer!");
		}
		if (usersWithMod.get(username) != null ||CommandsMain.isSinglePlayer) {
			return true;
		}
		return false;
	}

}
