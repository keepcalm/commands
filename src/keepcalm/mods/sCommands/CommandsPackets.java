package keepcalm.mods.sCommands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.src.Packet250CustomPayload;
/*
 * Miscellaneous functions to do with packets
 */
public class CommandsPackets {
	/*
	 * returns a new Packet250CustomPayload on channel channel and with the data encoded as bytes.
	 * @param channel - the channel to send on
	 * @param data - the data to encode as a byte[] in the packet.
	 */
	public static Packet250CustomPayload createNewPacket(String channel, String data) {
		//System.out.println("Creating new packet - channel " + channel + " data: '" + data + "'" );
		Packet250CustomPayload installed = new Packet250CustomPayload();
		installed.channel = channel;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length() + 5);
		DataOutputStream out = new DataOutputStream(bos);
		try {
			out.writeChars(data);
			/*out.writeInt(5);
			out.writeInt(6);
			out.writeInt(7);
			out.writeInt(8);*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		installed.data = bos.toByteArray();
		installed.length = bos.size();
		return installed;
	}
}
