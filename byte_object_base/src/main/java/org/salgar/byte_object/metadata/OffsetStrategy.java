package org.salgar.byte_object.metadata;

public interface OffsetStrategy<T> {
    int findOffset(byte[] data, int linksize);
}
