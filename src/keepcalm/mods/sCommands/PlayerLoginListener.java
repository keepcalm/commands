package keepcalm.mods.sCommands;

import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/* Not registered - there are better ways of doing this... */
public class PlayerLoginListener {
	@ForgeSubscribe
	public void onEntityJoin(EntityJoinWorldEvent ev) {
		if (ModLoader.getMinecraftServerInstance().isSinglePlayer()) {
			return;
		}
		if (ev.entity instanceof EntityPlayer || ev.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) ev.entity;
			World world = ev.world;
			ChunkCoordinates spawn = world.getSpawnPoint();
			ChunkCoordinates plpos = new ChunkCoordinates((int) player.posX, (int) player.posY, (int) player.posZ);
			if (spawn == plpos) {
				player.addChatMessage(ChatColour.yellow + "Hey there, " + player.username + ChatColour.RESET);
				player.addChatMessage("This server uses the Commands mod! If you don't want to hear muted players on chat, we suggest you download it.");
			}
		}
	}
}
