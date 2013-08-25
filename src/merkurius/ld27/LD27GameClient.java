package merkurius.ld27;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.network.GameClient;

public class LD27GameClient implements GameClient {
	protected InetAddress 	address;
	protected int			portIn;
	protected int			portOut;
	protected int			id;
	protected Entity		player;
	
	
	public LD27GameClient(InetAddress address, int portIn, int portOut, int id, Entity player) {
		this.address 	= address;
		this.portIn 	= portIn;
		this.portOut 	= portOut;
		this.id 		= id;
		this.player 	= player;
	}
	
	public LD27GameClient(InetAddress address, int portIn) {
		this.address 	= address;
		this.portIn 	= portIn;
	}
	
	public InetAddress getAddress() { return address; }
	public int getPort() { return portIn; }
	public int getId() { return this.id; }
	public Entity getEntity() { return player; }
	
	@Override
	public boolean checkPacket(DatagramPacket packet) {
		return false;
	}
	

}
