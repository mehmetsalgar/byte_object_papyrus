package org.salgar.byte_object.metadata;

public class CollectionOffsetStrategy<T> implements OffsetStrategy<T> {
    public int findOffset(byte[] data, int linksize) {
    	int count = AbstractMetaData.intFromByte(linksize, data);
    	linksize+= 4;
    	if(count != 0) {
    		int i = 0;
    		while(i < count) {
    			int length = AbstractMetaData.intFromByte(linksize, data);
    			linksize+= 4 + length;
    			i++;
    		}
    	}
        return linksize;
    }
}