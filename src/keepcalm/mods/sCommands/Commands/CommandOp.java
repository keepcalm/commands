package keepcalm.mods.sCommands.Commands;

import java.util.ArrayList;
import java.util.List;

import keepcalm.mods.sCommands.CommandsMain;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;

/* SMP Op, for Singleplayer - This is only registered in single player. */
public class CommandOp extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "op";
	}
	public String getCommandUsage() {
		return "/op <player>";
	}
	public boolean canCommandSenderUseCommand(ICommandSender guy) {
		if (CommandsMain.isSinglePlayer) {
			return true;
		}
		else {
			return CommandsMain.isPowerUser(guy.getCommandSenderName());
		}
	}
	public List addTabCompleteOptions(ICommandSender guy, String[] args) {
		//List ret = new ArrayList<String>();
		if (args.length == 1)
        {
            String var3 = args[args.length - 1];
            ArrayList var4 = new ArrayList();
            String[] var5 = MinecraftServer.getServer().getAllUsernames();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                String var8 = var5[var7];

                if (!MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var8) && doesStringStartWith(var3, var8))
                {
                    var4.add(var8);
                }
            }

            return var4;
        }
        else
        {
            return null;
        }
		
	}
	/*public List getCommandAliases() {
		List j = new ArrayList<String>();
		j.add("deop");
		return j;
	}*/
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		/*if (this.getCommandName().equals("deop")) {
			// remove player
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			server.getConfigurationManager().removeOp(arg1[0]);
			
			server.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(ChatColour.ITALIC + "[" + arg0.getCommandSenderName() + ": Deopping " + arg1[0] + " ]" ));
		}
		else {*/
			// add player
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (arg1.length < 1) { throw new WrongUsageException("/op <player>"); }
		EntityPlayerMP target = server.getConfigurationManager().getPlayerForUsername(arg1[0]);
		if (target != null) {
			
			server.getConfigurationManager().addOp(arg1[0]);
			server.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(ChatColour.ITALIC + "" + ChatColour.lightGrey + "[" + arg0.getCommandSenderName() + ": Opping " + arg1[0] + " ]" ));
			target.addChatMessage("You are now Op!");
		}
		else {
			arg0.sendChatToPlayer(arg1[0] + " is not online.");
		}
		
		//}
	}

}
