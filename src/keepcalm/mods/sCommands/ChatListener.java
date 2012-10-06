package keepcalm.mods.sCommands;

import java.util.logging.Level;

import keepcalm.mods.sCommands.Commands.CommandMute;

import cpw.mods.fml.common.network.IChatListener;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet3Chat;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ChatListener implements IChatListener {

	/*@ForgeSubscribe
	public void onPlayerChat(ChatEvent ev) {
		
	}*/

	@Override
	public Packet3Chat clientChat(NetHandler handler, Packet3Chat message) {
		//System.out.println("[CLIENT]: The user associated somehow with the message is " + handler.getPlayer().username);
		//System.out.println("[CLIENT]: message => " + message.message);
		// client end handler.getPlayer() returns the EntityPlayer for the user of that client.
		//String regex = "^<.*>$";
		// NOTE: FACT: It is impossible to end a message with "> " in minecraft. The client chops the space automagically.
		if (message.message.startsWith("<") && message.message.endsWith("> ") ) {
			//System.out.println("[CLIENT]: The message is being cancelled.");
			return new Packet3Chat();
		}
		return message;
		
	}

	@Override
	public Packet3Chat serverChat(NetHandler handler, Packet3Chat message) {
		//System.out.println("[SERVER]: message as a string => " + message.toString());
		//System.out.println("[SERVER]: The user associated somehow with the message is " + handler.getPlayer().username);
		//System.out.println("[SERVER]: The message is: " + message.message);
		try {
			if (CommandMute.isMuted(handler.getPlayer().username) && !message.message.startsWith("/")) {
				//System.out.println("That player is MUTED! Not allowing message to continue.");
				//Packet3Chat x = new Packet3Chat();
				//x.
			
				return new Packet3Chat("", false);
			}	// broken - gives bad chat character messages.
			/*else if (CommandsMain.isPowerUser(handler.getPlayer().username) && !message.message.startsWith("/")) {
				String msg = message.message;
				msg = "[" + ChatColour.BOLD + ChatColour.white + "PowerUser" + ChatColour.RESET + "]" + msg;
				return new Packet3Chat(msg);
			}*/
			return message;
		
		}
		catch (Exception e) {
			
			handler.getPlayer().addChatMessage("The Commands chat handler had an issue while processing this message. The error was " + e.getMessage());
			ModLoader.getLogger().log(Level.SEVERE, "The Commands ChatListener had a bad error - " + e.getMessage() + ". The message which caused this issue was sent by " + handler.getPlayer().username + " and the message was " + message.message);
			return message;
		}
	}
}
