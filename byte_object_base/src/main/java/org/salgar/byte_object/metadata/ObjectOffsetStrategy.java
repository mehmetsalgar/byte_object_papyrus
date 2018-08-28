package org.salgar.byte_object.metadata;

public class ObjectOffsetStrategy<T> implements OffsetStrategy<T> {
    public int findOffset(byte[] data, int linksize) {
        //return AbstractMetaData.intFromByte(fieldMetaData.getOffset(), data) + fieldMetaData.getOffset() + 4;
    	int length = AbstractMetaData.intFromByte(linksize, data);
        linksize += length + 4;
        
        return linksize;
    }
}