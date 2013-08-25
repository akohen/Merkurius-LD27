package merkurius.ld27.system;

import com.artemis.Entity;

public class ClientSystem extends LD27SyncSystem {

	@Override
	protected void updateEntity(Entity e, EntityUpdate update) {
		if( playerEntity == null ) {
			if( playerMapper.has(e) && this.playerId == playerMapper.get(e).playerId ) {
				this.playerEntity = e;
			}
		} 
		
		if( playerEntity == e ) {
			entitySendUpdate(e);
		} else {
			entityApplyUpdate(e, update);
		}
	}


}
