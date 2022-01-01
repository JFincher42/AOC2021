package day16;

import java.util.ArrayList;

class Packet{
	public int version;
	public int type;

	// If type == 4, this will have something in it
	public long value;

	// If type != 4, this will have something in it
	// Type 0: sum all the subpackets
	// Type 1: multiply all the subpackets
	// Type 2: min value of all the subpackets
	// Type 3: max value of all the subpackets
	// Type 5: Greater than, returns 1 if subpacket[0]>subpacket[1]
	// Type 6: Less than, returns 1 if subpacket[0]<subpacket[1]
	// Type 7: Equal to, returns 1 if subpacket[0]==subpacket[1]
	public ArrayList<Packet> subPackets;
	
	Packet(){
		this.version = 0;
		this.type = 0;
		this.value = 0;
		this.subPackets = null;
	}
	
	Packet(int version, int type, long value){
		this.version = version;
		this.type = type;
		this.value = value;
	}

	Packet(int version, int type){
		this.version = version;
		this.type = type;
		this.value = 0l;
	}
	
	

}