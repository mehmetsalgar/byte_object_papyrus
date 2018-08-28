package org.salgar.byte_object.metadata;

import sun.awt.image.ImageWatched;

import java.nio.MappedByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public final class AbstractMetaData {
    private AbstractMetaData() {

    }

    /**
     * Byte object uses Semantic Versioning https://semver.org/ meaning, it allow patch versioning
     * changes (means bug fixes, property name changes) but it will refuse deserialize if the minor (backward
     * compatible changes under normal circumstance, like adding new field, but serilisation mechanism should
     * know exact structure of the object so it can't deal with such scenarios) and major version changes for obvious
     * reasons.
     *
     * The version number and the versioning will be implemented or not, can be controlled via MWE Workflow properties,
     * which should normally lie in the same project containing your Papyrus Model file.
     *
     * @return false is version is not acceptable
     */
    public static boolean verifyVersion(String version, byte[] bytes) {
        String[] versionParts = parseVersion(version);

        int majorByte = intFromByte(0, bytes);
        int minorByte = intFromByte(4, bytes);

        if (majorByte != Integer.valueOf(versionParts[0]).intValue() || minorByte != Integer.valueOf(versionParts[1]).intValue()) {
            return false;
        }

        return true;
    }

    private static String[] parseVersion(String version) {
        StringTokenizer st = new StringTokenizer(version, ".");
        String[] versionParts = new String[3];
        versionParts[0] = st.nextToken();
        versionParts[1] = st.nextToken();
        versionParts[2] = st.nextToken();

        return versionParts;
    }

    public static byte[] placeVersion(String version) {
        byte[] versionedBytes = new byte[12];

        return placeVersionInternal(version, versionedBytes);
    }

    private static byte[] placeVersionInternal(String version, byte[] arrayToPlace) {
        String[] versionParts = parseVersion(version);

        int2Byte(Integer.valueOf(versionParts[0]), 0, arrayToPlace);
        int2Byte(Integer.valueOf(versionParts[1]), 4, arrayToPlace);
        int2Byte(Integer.valueOf(versionParts[2]), 8, arrayToPlace);

        return arrayToPlace;
    }

    public static byte[] placeVersion(String version, byte[] data) {
        byte[] versionedBytes = new byte[12 + data.length];
        System.arraycopy(data, 0, placeVersionInternal(version, versionedBytes), 12, data.length);

        return versionedBytes;
    }

	//TODO List String
	//TODO Map String, String
    public static void long2Byte(long value, int offset, byte[] data) {
        data[offset] = (byte) (value >>> 56);
        data[offset + 1] = (byte) (value >>> 48);
        data[offset + 2] = (byte) (value >>> 40);
        data[offset + 3] = (byte) (value >>> 32);
        data[offset + 4] = (byte) (value >>> 24);
        data[offset + 5] = (byte) (value >>> 16);
        data[offset + 6] = (byte) (value >>> 8);
        data[offset + 7] = (byte) (value);
    }
    
    public static byte[] long2Byte(long value) {
    	byte[] result = new byte[8];
        
    	long2Byte(value, 0, result);
    	
    	return result;
    }

    public static long longFromByte(int offset, byte[] data) {
        long id = (data[offset++] & 0xffL) << 56 | (data[offset++] & 0xffL) << 48 | (data[offset++] & 0xffL) << 40 | (data[offset++] & 0xffL) << 32
                | (data[offset++] & 0xffL) << 24 | (data[offset++] & 0xffL) << 16 | (data[offset++] & 0xffL) << 8 | (data[offset++] & 0xffL);
        /*long val = 0;
        for (int i = 0; i < 8; i++) {
            val = (val << 8) + (data[offset + i] & 0xff);
        }*/
        return id;
    }

    public static void int2Byte(int value, int offset, byte[] data) {
        data[offset] = (byte) (value >>> 24);
        data[offset + 1] = (byte) (value >>> 16);
        data[offset + 2] = (byte) (value >>> 8);
        data[offset + 3] = (byte) (value);
    }

    public static byte[] int2Byte(int value) {
        byte[] result = new byte[4];

        int2Byte(value, 0, result);

        return result;
    }

    public static int intFromByte(int offset, byte[] data) {
        int val = 0;
        for (int i = 0; i < 4; i++) {
            val = (val << 8) + (data[offset + i] & 0xff);
        }
        return val;
    }

    public static void boolean2Byte(Boolean value, int offset , byte[] data) {
        data[offset] = (byte) (value == true ? 1 : 0);
    }
    
    public static byte[] boolean2Byte(Boolean value) {
    	byte[] result = new byte[1];

    	boolean2Byte(value, 0, result);
    	
    	return result;
    }

    public static boolean booleanFromByte(int offset, byte[] data) {
        int val = 0;
        val = (val << 8) + (data[offset] & 0xff);
        return 1 == val;
    }

    public static int searchOffset(FieldMetaData<?> fmd, byte[] data) {
        int linksize;

        if (fmd.getOffset() != -1) {
            linksize = intFromByte(fmd.getOffset(), data) + fmd.getOffset() + 4;
            //linksize = fmd.getOffsetStrategy().findOffset(data);
        } else {
            linksize = searchOffset(fmd.getLink(), data);
            int internalsize = fmd.getOffsetStrategy().findOffset(data, linksize);
            linksize = internalsize;
            //int internalsize = intFromByte(linksize, data);
            //linksize += internalsize + 4;
        }

        return linksize;
    }
    
    public static int findTotalCollectionLength(byte[] data, int offset, int count) {
    	int i = 0;
    	int length = 0;
    	while(i < count) {
    		int collectionElementLength = intFromByte(offset + length, data);
    		length+= 4 + collectionElementLength;
    		i++;
    	}
    	
    	return length;
    }
    
    public static int findMapTotalLength(byte[] data, int offset, int count) {
    	int i = 0;
    	int lenght = 0;
    	
    	while(i < count) {
    		int mapKeyLength = intFromByte(offset + lenght, data);
    		lenght+= 4 + mapKeyLength;
    		int mapElementLength = intFromByte(offset + lenght, data);
    		lenght+= 4 + mapElementLength;
    		i++;
    	}
    	return lenght;
    }
    
    public static byte[] getByte(Long object) {
    	byte[] result = long2Byte( object);
    	
    	return result;
    }

	public static byte[] getByte(Integer object) {
		byte[] result = int2Byte(object);
	
		return result;
	}

    public static byte[] getByte(Boolean object) {
		byte[] result = boolean2Byte( object);
		
		return result;
	}

    public static byte[] getByte(java.util.Date object) {
		byte[] result  = long2Byte(object.getTime());
		
		return result;
	}

/*    public static byte[] getByte(String object) {
		byte[] result  = object.getBytes();
		
		return result;
	}*/

    public static byte[] getByte(String object) {
        List<Byte> linkedList = new LinkedList<>();
        for (int sIndex = 0, sLength = object.length(); sIndex < sLength; sIndex++) {
            char c = object.charAt(sIndex);
            if (c < '\u0080') {
                linkedList.add((byte) c);
            } else if (c < '\u0800') {
                linkedList.add((byte) (192 | c >>> 6));
                linkedList.add((byte) (128 | c & 63));
            } else if (c < '\ud800' || c > '\udfff') {
                linkedList.add((byte) (224 | c >>> 12));
                linkedList.add((byte) (128 | c >>> 6 & 63));
                linkedList.add((byte) (128 | c & 63));
            } else {
                int cp = 0;
                if (++sIndex < sLength) cp = Character.toCodePoint(c, object.charAt(sIndex));
                if ((cp >= 1 << 16) && (cp < 1 << 21)) {
                    linkedList.add((byte) (240 | cp >>> 18));
                    linkedList.add((byte) (128 | cp >>> 12 & 63));
                    linkedList.add((byte) (128 | cp >>> 6 & 63));
                    linkedList.add((byte) (128 | cp & 63));
                } else
                    linkedList.add((byte) '?');
            }
        }
        int n = linkedList.size();
        byte[] buffer = new byte[n];
        //System.arraycopy(linkedList.toArray(new Byte[n]), 0, buffer, 0, n);

        for (int i = 0; i < n; i++) {
            buffer[i] = linkedList.get(i);
        }

        return buffer;
    }

	public static Long setByte(Long value, byte[] payload) {
		value = longFromByte(0, payload);

		return value;
	}
	
	public static Integer setByte(Integer value, byte[] payload) {
		value = intFromByte(0, payload);

		return value;
	}
	
	public static Boolean setByte(Boolean value, byte[] payload) {
		value = booleanFromByte(0, payload);

		return value;
	}
	
	public static java.util.Date setByte(java.util.Date value, byte[] payload) {
		value.setTime(longFromByte(0, payload));
		
		return value;
	}
	
	public static String setByte(String value, byte[] payload) {
        return new String(payload, StandardCharsets.UTF_8);
    }

	public static int getKeyLength(Integer key) {
        return 4;
    }

    public static int getKeyLength(Long key) {
        return 8;
    }

    public static int getKeyLength(java.util.Date key) {
        return 8;
    }

    public static int getKeyLength(String key) {
        return key.length();
    }

    public static byte[] addMap(byte[] data, int offset, List<MapKeyValueByte> mapKeyValueBytes, int totallength) {
    	int count = intFromByte(offset, data);

        if(count == 0) {
        	count = mapKeyValueBytes.size();
        	
        	byte[] count_byte = int2Byte(count);
            final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(data.length + totallength + (8 * count));

            if(offset != 0) {
                //byte[] informationBeforeCount = Arrays.copyOfRange(data, 0, offset);
                buffer.put(data, 0, offset);
            }
            
            buffer.put(count_byte);
            for(int i = 0; i < count; i++) {
                MapKeyValueByte mapKeyValueByte = mapKeyValueBytes.get(i);
                buffer.put(AbstractMetaData.int2Byte(mapKeyValueByte.getKey().length));
                buffer.put(mapKeyValueByte.getKey());
                buffer.put(AbstractMetaData.int2Byte(mapKeyValueByte.getValue().length));
                buffer.put(mapKeyValueByte.getValue());
            }
            
            if(data.length > offset + 4) {
                int restOffset = offset + 4;
				//byte[] informationAfterCount = Arrays.copyOfRange(data, offset + 4, data.length);
				buffer.put(data, restOffset, data.length - restOffset);
			}

            data = buffer.array();
            buffer.clear();            
        } else {
            int restOffset = offset + 4;
        	int mapTotalLength = findMapTotalLength(data, restOffset, count);

        	//byte[] informationAfterCount = Arrays.copyOfRange(data, restOffset, offset + 4 + mapTotalLength);

            count = mapKeyValueBytes.size();
            byte[] count_byte = int2Byte(count);

            int newBufferLength = (data.length - (4 + mapTotalLength)) + (4 + totallength + (8 * count));
            final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(newBufferLength);

            if(offset != 0) {
                //byte[] informationBeforeCount = Arrays.copyOfRange(data, 0, offset);
                buffer.put(data, 0, offset);
            }

            buffer.put(count_byte);
            for(int i = 0; i < count; i++) {
                MapKeyValueByte mapKeyValueByte = mapKeyValueBytes.get(i);
                buffer.put(AbstractMetaData.int2Byte(mapKeyValueByte.getKey().length));
                buffer.put(mapKeyValueByte.getKey());
                buffer.put(AbstractMetaData.int2Byte(mapKeyValueByte.getValue().length));
                buffer.put(mapKeyValueByte.getValue());
            }
            
            if(data.length > offset + 4) {
                int restMapLength = restOffset + mapTotalLength;
				//byte[] informationAfterMap = Arrays.copyOfRange(data, offset + 4 + mapTotalLength
				//	, data.length);
				buffer.put(data, restMapLength, data.length - restMapLength);
			}

            data = buffer.array();
            buffer.clear();
        }
        
    	return data;
	}

    public static byte[] add(byte[] data, int offset, List<byte[]> payload, int totalLength) {
        int count = intFromByte(offset, data);
        int restOffset = offset + 4;

        if(count == 0) {
            final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(data.length + totalLength);
            if(offset != 0) {
                //byte[] informationBeforeCount = Arrays.copyOfRange(data, 0, offset);
                buffer.put(data, 0, offset);
            }

            buffer.put(int2Byte(payload.size()));
            for(int i = 0, n = payload.size(); i < n; i ++) {
                byte[] bytes = payload.get(i);
                buffer.put(int2Byte(bytes.length));
                buffer.put(bytes);
            }

            if(data.length > restOffset) {
                buffer.put(data, restOffset, data.length - restOffset);
            }

            data = buffer.array();
            buffer.clear();
        } else {
            int collectionTotalLength = findTotalCollectionLength(data, restOffset, count);

            int newBufferLength = (data.length - (4 + collectionTotalLength)) + (4 + totalLength);

            final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(newBufferLength);

            if(offset != 0) {
                //byte[] informationBeforeCount = Arrays.copyOfRange(data, 0, offset);
                buffer.put(data, 0, offset);
            }

            buffer.put(int2Byte(payload.size()));
            for(int i = 0, n = payload.size(); i < n; i ++) {
                byte[] bytes = payload.get(i);
                buffer.put(int2Byte(bytes.length));
                buffer.put(bytes);
            }

            if(data.length > restOffset) {
                int restCollectionLength  = restOffset + collectionTotalLength;
                buffer.put(data, restCollectionLength, data.length - restCollectionLength);
            }

            data = buffer.array();
            buffer.clear();
        }
        return data;
    }

    public static byte[] add(byte[] data, int offset, byte[] payload) {
        int count = intFromByte(offset, data);
        int length = payload.length;
        
        final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(data.length + length + 4);
        if(offset != 0) {
			//byte[] informationBeforeCount = Arrays.copyOfRange(data, 0, offset);
			buffer.put(data, 0, offset);
		}
        
        byte[] length_byte = int2Byte(length);
        
        if (count == 0) {
            count = 1;

            byte[] count_byte = int2Byte(count);           
            
            buffer.put(count_byte);
            buffer.put(length_byte);
            buffer.put(payload);
            
            if(data.length > offset + 4) {
                int restOffset = offset + 4;
				//byte[] informationAfterCount = Arrays.copyOfRange(data, restOffset, data.length);
				buffer.put(data, restOffset, data.length - restOffset);
			}

            data = buffer.array();
            buffer.clear();
        } else {
            int restOffset = offset + 4;
			int collectionTotalLength = findTotalCollectionLength(data, restOffset, count);
            //byte[] informationAfterCount = Arrays.copyOfRange(data, restOffset, offset + 4 + collectionTotalLength);
          
			count++;
			
            buffer.put(int2Byte(count));
            buffer.put(data, restOffset, collectionTotalLength);
            buffer.put(length_byte);
            buffer.put(payload);
            
            if(data.length > offset + 4) {
                int restCollectionLength  = restOffset + collectionTotalLength;
				//byte[] informationAfterCollection = Arrays.copyOfRange(data, offset + 4 + collectionTotalLength
				//	, data.length);
				buffer.put(data, restCollectionLength, data.length - restCollectionLength);
			}

            data = buffer.array();
            buffer.clear();
        }
        return data;
    }

    public static byte[] add(byte[] keyData, byte[] data, int offset, byte[] payload) {
        int count = AbstractMetaData.intFromByte(offset, data);
        int length = payload.length;
        if (count == 0) {
            count = 1;

            byte[] count_byte = AbstractMetaData.int2Byte(count);           
            byte[] length_byte = AbstractMetaData.int2Byte(length);

            final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(data.length + length + 4);
            
            if(offset != 0) {
				//byte[] informationBeforeCount = Arrays.copyOfRange(data, 0, offset);
				buffer.put(data, 0, offset);
			}
            
            buffer.put(count_byte);
            buffer.put(length_byte);
            buffer.put(payload);
            
            if(data.length > offset + 4) {
                int restOffset = offset + 4;
				//byte[] informationAfterCount = Arrays.copyOfRange(data, restOffset, data.length);
				buffer.put(data, restOffset, data.length - restOffset);
			}

            data = buffer.array();
            buffer.clear();
        } else {
            final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(data.length + length + 4);
			
			if(offset != 0) {
				//byte[] informationBeforeCount = Arrays.copyOfRange(data, 0, offset);
				buffer.put(data, 0, offset);
			}
			int restOffset = offset + 4;
			int collectionTotalLength = findTotalCollectionLength(data, restOffset, count);
            //byte[] informationAfterCount = Arrays.copyOfRange(data, restOffset, restOffset + collectionTotalLength);
            byte[] length_byte = AbstractMetaData.int2Byte(length);
			
			count++;
			
            buffer.put(AbstractMetaData.int2Byte(count));
            buffer.put(data, restOffset, collectionTotalLength);
            buffer.put(length_byte);
            buffer.put(payload);
            
            if(data.length > offset + 4) {
				//byte[] informationAfterCollection = Arrays.copyOfRange(data, restOffset + collectionTotalLength
				//	, data.length);
				buffer.put(data, restOffset + collectionTotalLength, data.length - (restOffset + collectionTotalLength));
			}

            data = buffer.array();
            buffer.clear();
        }
        return data;
    }

    public static byte[] setObject(byte[] data, int offset, byte[] payload) {
        int length = payload.length;

        int original_length = intFromByte( offset, data);
        byte[] length_byte = int2Byte(length);

        int restOffset = offset + 4 + original_length;
        int restLength = data.length - restOffset;

        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(offset + length_byte.length + length + restLength);
		buffer.put(data, 0, offset);
		buffer.put(length_byte);
		buffer.put(payload);
        buffer.put(data, restOffset, restLength);
		
        data = buffer.array();

        return data;
    }

    /*private static Field getField(Object dataContainer) {
        try {
            Field fieldData = dataContainer.getClass().getDeclaredField("data");
            fieldData.setAccessible(true);
            return fieldData;
        } catch (Throwable t) {
            return null;
        }
    }

    public static void setPackageField(Object dataContainer, byte[] data) {
        try {
            getField(dataContainer).set(dataContainer, data);
        } catch (Throwable t) {
        }
    }*/
}