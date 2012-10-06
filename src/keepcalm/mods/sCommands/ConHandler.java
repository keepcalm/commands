package keepcalm.mods.sCommands;

import java.util.Iterator;

import keepcalm.mods.sCommands.Commands.CommandBack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetLoginHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
/*
 * Commands custom packet handler - used to
 * check if the client has commands. 
 */
public class ConHandler implements IConnectionHandler {
	public static boolean isCommandsOnClient;
	public static NetworkManager netman;
	//public NetServerHandler netserv;
	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			NetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub
		Packet250CustomPayload installed = CommandsPackets.createNewPacket("CommandsInst", "hascommands");
		
		EntityClientPlayerMP guy = (EntityClientPlayerMP) clientHandler.getPlayer();
		guy.sendQueue.addToSendQueue(installed);
		
		

	}
	
	@Override
	public void connectionClosed(NetworkManager manager) {
		// TODO Auto-generated method stub
		

	}
	
	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, NetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, NetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			NetworkManager manager) {
		// TODO Auto-generated method stub
		Packet250CustomPayload request = CommandsPackets.createNewPacket("CommandsInst", "Anybody there?");
		//System.out.println("Requesting response from the client...");
		manager.addToSendQueue(request);
		return "";
	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			NetworkManager manager) {
		//File motd = new File( + "/motd.txt");
		EntityPlayer guy = (EntityPlayer) player;
		CommandBack.clearPlayerData(guy.username);
		// this happens too soon
		netman = manager;
		if (!CommandsMain.motd.isEmpty()) {
			Iterator<String> it = CommandsMain.motd.iterator();
			while (it.hasNext()) {
				guy.sendChatToPlayer(it.next());
			}
		}
		if (!CustomPacketHandler.hasMod(guy.username)) {
			guy.sendChatToPlayer("Get Commands if you wish to completely hide muted messages!");
		}
		if (CommandsMain.isSinglePlayer) {
			CommandsMain.addPowerUserSilently(guy.username);
			
		}
		CommandsMain.isSinglePlayer = false;
		/*if (!isCommandsOnClient) {
			((EntityPlayer) player).sendChatToPlayer("Get Commands to completely hide muted messages!");
		}*/
		// TODO Auto-generated method stub

	}

}
