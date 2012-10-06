package keepcalm.mods.sCommands.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;

import keepcalm.mods.sCommands.CommandsMain;

import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.World;
/*
 * Accept a TPA request.
 */
public class CommandTpAcc extends CommandBase {
	
	public static HashMap<EntityPlayerMP,EntityPlayerMP> waiting ;//= new HashMap();
	public static HashMap<EntityPlayerMP, int[]> waitingCoords;
	//					Player to tp			TP them to	  array with 2 entries - has player 1 agreed, and has player 2 agreed.
	public static HashMap<EntityPlayerMP,HashMap<EntityPlayerMP,boolean[]>> waitingTwoWay;
	//public static HashMap<EntityPlayerMP> needBothSides;
	public CommandTpAcc() {
		waiting = new HashMap();
		waitingCoords = new HashMap();
		//needBothSides = new HashMap();
	}
	public String getCommandUsage(ICommandSender guy) {
		return "/tpyes";
	}
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "tpyes";
	}
	//@SuppressWarnings("null")
	public List getCommandAliases() {
		List<String> ret = new ArrayList<String> ();
		ret.add("tpacc");
		//ret.add("tpno");
		//ret.add("tpdeny");
		ret.add("yes");
		ret.add("y");
		//ret.add("no");
		//ret.add("n");
		return ret;
	}
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		
		EntityPlayerMP target = (EntityPlayerMP) getCommandSenderAsPlayer(var1);
		if (waiting.containsKey(target)) {
			EntityPlayerMP sender = waiting.get(target);
			sender.addChatMessage(target.username + " accepted! You will now be teleported...");
			double targetX = target.posX;
			double targetY = target.posY;
			double targetZ = target.posZ;
			CommandBack.setLastLoc(sender.username, sender.posX, sender.posY, sender.posZ);
			World sendersWorld = sender.worldObj;
			
			//targetX += 1; // so they don't appear on top of each other
			// TODO	if (sendersWorld.blockExists(par1, par2, par3)) ...
			sender.setPositionAndUpdate(targetX, targetY, targetZ);
			waiting.remove(target);
		}
		else if (waitingCoords.containsKey(target)) {
			int[] targetCoords = waitingCoords.get(target);
			double targetX = (double) targetCoords[0];
			double targetY = (double) targetCoords[1];
			double targetZ = (double) targetCoords[2];
			CommandBack.setLastLoc(target.username, target.posX, target.posY, target.posZ);
			target.setPositionAndUpdate(targetX, targetY, targetZ);
			
			waitingCoords.remove(target);
		}
		else {
			boolean foundWaitingTwoWay = false;
			HashMap<EntityPlayerMP,boolean[]> temp = new HashMap();
			EntityPlayerMP mainKey = null;
			if (waitingTwoWay.containsKey(target)) {
				mainKey = target;
				foundWaitingTwoWay = true;
				temp = waitingTwoWay.get(target);
			}
			
			if (foundWaitingTwoWay == false) {
				
				Iterator<HashMap<EntityPlayerMP,boolean[]>> it = waitingTwoWay.values().iterator();
				while (it.hasNext()) {
					temp = it.next();
					if (temp.containsKey(target)) {
						Iterator<EntityPlayerMP> it2 = waitingTwoWay.keySet().iterator();
						while (it2.hasNext()) {
							mainKey = it2.next();
							if (waitingTwoWay.get(mainKey) == temp) {
								break;
							}
						}
						foundWaitingTwoWay = true;
						break;
					}
				}
			}
			
			if (foundWaitingTwoWay == true) {
				EntityPlayerMP targ;
				if (temp.containsKey(target)) {
					targ = target;
					boolean[] bool = temp.get(target);
					temp.remove(target);
					bool[1] = true;
					temp.put(target, bool);
					
				}
				else {
					
					boolean[] bool = temp.get((targ = temp.keySet().iterator().next()));
					temp.remove(target);
					bool[0] = true;
					temp.put(target, bool);
				}
				if ( (temp.get(targ))[0] == true && (temp.get(targ))[1] == true ) {
					// ok, here we go.
					if (mainKey == null) { throw new CommandException("Ouch. mainKey == null in TpAcc. Tell someone!"); }
					targ.sendChatToPlayer(mainKey.getEntityName() + " is teleporting to you...");
					mainKey.sendChatToPlayer("Teleporting you to " + targ.getEntityName());
					try {
						Thread.sleep(CommandsMain.tpDelay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						FMLCommonHandler.instance().getFMLLogger().warning("[Commands] TPA pause failed - " + e.getMessage());
					}
					mainKey.setPositionAndUpdate(targ.posX, targ.posY, targ.posZ);
					waitingTwoWay.remove(mainKey);
					
				}
				return;
			} // end of if (waitingTwoWay.containsKey
			
			target.addChatMessage("You have no teleport requests at the moment.");
		}

	}

	public static void addToWaiting(EntityPlayerMP sender, EntityPlayerMP target) {
		//bothSidesAgree = bothSidesAgree ? true : false;
		// TODO Auto-generated method stub
		waiting.put(target, sender);
		
		//needBothSides.put(target, );
		
	}
	public static void addToWaiting(EntityPlayerMP target, HashMap<EntityPlayerMP,boolean[]> map) {
		waitingTwoWay.put(target, map);
	}
	public static void addToWaiting(EntityPlayerMP target, int targetX, int targetY, int targetZ) {
		int[] coords = new int[2];
		coords[0] = targetX;
		coords[1] = targetY;
		coords[2] = targetZ;
		waitingCoords.put(target, coords);
		
	}
	public static HashMap<EntityPlayerMP, EntityPlayerMP> getWaiting() {
		return waiting;
	}
	public static HashMap<EntityPlayerMP, int[]> getWaitingCoords() {
		return waitingCoords;
	}
	public static HashMap<EntityPlayerMP,HashMap<EntityPlayerMP,boolean[]>> getTwoSidedWaiting() {
		return waitingTwoWay;
	}
	public static boolean isWaitingTwoWay(EntityPlayerMP target) {
		boolean foundWaitingTwoWay = false;
		HashMap<EntityPlayerMP,boolean[]> temp = new HashMap();
		EntityPlayerMP mainKey = null;
		if (waitingTwoWay.containsKey(target)) {
			mainKey = target;
			foundWaitingTwoWay = true;
			temp = waitingTwoWay.get(target);
			return true;
		}
		
		if (foundWaitingTwoWay == false) {
			
			Iterator<HashMap<EntityPlayerMP,boolean[]>> it = waitingTwoWay.values().iterator();
			while (it.hasNext()) {
				temp = it.next();
				if (temp.containsKey(target)) {
					return true;
					/*Iterator<EntityPlayerMP> it2 = waitingTwoWay.keySet().iterator();
					while (it2.hasNext()) {
						mainKey = it2.next();
						if (waitingTwoWay.get(mainKey) == temp) {
							break;
						}
					}
					foundWaitingTwoWay = true;
					break;*/
				}
			}
		}
		return false;
		/*if (foundWaitingTwoWay == true) {
			EntityPlayerMP targ;
			if (temp.containsKey(target)) {
				targ = target;
				boolean[] bool = temp.get(target);
				temp.remove(target);
				bool[1] = true;
				temp.put(target, bool);
				
			}
			else {
				
				boolean[] bool = temp.get((targ = temp.keySet().iterator().next()));
				temp.remove(target);
				bool[0] = true;
				temp.put(target, bool);
			}
		}*/
	}
	public static void removeFromWaiting(EntityPlayerMP target) {
		if (waiting.containsKey(target)) {
			// ok
			waiting.remove(target);
		}
		else if (waitingCoords.containsKey(target)) {
			waitingCoords.remove(target);
		}
		else if (waitingTwoWay.containsKey(target)) {
			waitingTwoWay.remove(target);
		}
		else {
			Iterator<HashMap<EntityPlayerMP,boolean[]>> it = waitingTwoWay.values().iterator();
			HashMap<EntityPlayerMP,boolean[]> x = new HashMap<EntityPlayerMP,boolean[]>();
			boolean found = false;
			while (it.hasNext()) {
				x = it.next();
				if (x.containsKey(target)) {
					found = true;
					break;
				}
			}
			if (!found) { return; }
			Iterator<EntityPlayerMP> it2 = waitingTwoWay.keySet().iterator();
			while (it2.hasNext()) {
				EntityPlayerMP h = it2.next();
				if (waitingTwoWay.get(h) == x) {
					waitingTwoWay.remove(h);
					return;
				}
			}
		}
		
			
	}

}
