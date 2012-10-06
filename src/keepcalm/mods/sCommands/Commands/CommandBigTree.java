package keepcalm.mods.sCommands.Commands;

import java.util.Random;

import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenBigTree;
/* Doesn't spawn a big tree every time. So called gentree in-game. */
public class CommandBigTree extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "gentree";
	}
	
	public String getCommandUsage() {
		return "/gentree";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		EntityPlayerMP guy = (EntityPlayerMP) getCommandSenderAsPlayer(sender);
		World world = guy.worldObj;
		WorldGenBigTree tree = new WorldGenBigTree(true);
		int treeX = (int) Math.floor(guy.posX);
		int treeY = (int) Math.floor(guy.posY);
		int treeZ = (int) Math.floor(guy.posZ);
		guy.setPositionAndUpdate((treeX + 1), treeY, treeZ);
		if (tree.generate(world, new Random(), treeX, treeY, treeZ)) {
			sender.sendChatToPlayer("A tree was generated!");
		}
		else {
			sender.sendChatToPlayer("Couldn't make a tree for some reason... Trees will only grow on grass, and nor will they grow too high up. The don't replace wildgrass either.");
		}

	}

}
