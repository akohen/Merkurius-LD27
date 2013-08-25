package merkurius.ld27.system;

import merkurius.ld27.ServerlistThread;

import com.artemis.Entity;

public class ServerSystem extends LD27SyncSystem {
	
	public ServerSystem(float delta, int port) {
		super(delta, port);
		ServerlistThread updateThread = new ServerlistThread();
		updateThread.start();
	}
	
	@Override
	protected void updateEntity(Entity e, EntityUpdate update) {
		entityApplyUpdate(e, update);
	}
	
	protected void process(Entity e) {
		super.process(e);
		entitySendUpdate(e);
	}
	
	@Override
	protected void newEntity(EntityUpdate update, int id) {
	}
	
	protected void removed(Entity e) {
		send( "removed " + syncMapper.get(e).getId() );
	}


}
