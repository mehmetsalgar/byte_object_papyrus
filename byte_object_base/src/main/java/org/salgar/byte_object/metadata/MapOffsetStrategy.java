package org.salgar.byte_object.metadata;

public class MapOffsetStrategy<T> implements OffsetStrategy<T>{

	@Override
	public int findOffset(byte[] data, int linksize) {
		int count = AbstractMetaData.intFromByte(linksize, data);
		//TODO make count configurable as int or long
		linksize+= 4;
    	if(count != 0) {
    		for(int i=0; i < count; i++) {
    			int key_length = AbstractMetaData.intFromByte(linksize, data);
    			linksize+= 4 + key_length;

    			int object_length = AbstractMetaData.intFromByte(linksize, data);
    			linksize+= 4 + object_length;
    		}
    	}
        return linksize;
	}
}