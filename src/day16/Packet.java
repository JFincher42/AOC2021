package day16;

import java.util.ArrayList;

class Packet{
	public int version;
	public int type;

	// If type == 4, this will have something in it
	public int value;

	// If type != 4, this will have something in it
	public ArrayList<Packet> subPackets;
	
	Packet(){
		this.version = 0;
		this.type = 0;
		this.value = 0;
		this.subPackets = null;
	}
	
	Packet(int version, int type, int value){
		this.version = version;
		this.type = type;
		this.value = value;
	}

	Packet(int version, int type){
		this.version = version;
		this.type = type;
		this.value = 0;
	}
	
	

}