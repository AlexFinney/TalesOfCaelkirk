package skeeter144.toc.entity.mob.mount.flying;

import skeeter144.toc.network.Network;
import skeeter144.toc.network.UpdatePlayerFlyingPKT;

public class EntityFlyingController {

	public static boolean isClientFlyingUp = false;
	public static boolean isClientFlyingDown = false;
	public static void sendServerFlyingUpdate(boolean isFlyingUp, boolean isFlyingDown) {
		isClientFlyingUp = isFlyingUp;
		isClientFlyingDown = isFlyingDown;
		Network.INSTANCE.sendToServer(new UpdatePlayerFlyingPKT(isFlyingUp, isFlyingDown));
	}
	
}
