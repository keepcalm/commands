package keepcalm.mods.sCommands;

import keepcalm.mods.sCommands.Commands.CommandBack;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommandLogger {
	/*@ForgeSubscribe
	public void onPlayerCommand(CommandEvent ev) {
		FMLCommonHandler.instance().getFMLLogger().info("[PLAYER COMMAND]: " + ev.sender.getCommandSenderName() + ": " + ev.command.getCommandName() + " " + CommandBase.joinString(ev.parameters, 0));
	}*/
	@ForgeSubscribe
	public void onLivingDeath(LivingDeathEvent ev) {
		if (ev.entityLiving instanceof EntityPlayer) {
			EntityPlayerMP player = (EntityPlayerMP) ev.entityLiving;
			CommandBack.setLastLoc(player.getEntityName(), player.posX, player.posY, player.posZ);
			if (FMLCommonHandler.instance().getMinecraftServerInstance().canCommandSenderUseCommand("back")) {
				player.addChatMessage("You died! Type /back to get back to your death point.");
			} /* I might look into overriding death messages... */
			//String msg = ev.source.getDeathMessage(player);
			
		}
	}/*
	@ForgeSubscribe
	public void onLivingDamage(LivingHurtEvent ev) {
		
	}*/
}
