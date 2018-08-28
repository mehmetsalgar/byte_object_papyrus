package org.salgar.byte_object.metadata;

public class FieldMetaData<T> {
    private T field;
    private OffsetStrategy<T> offsetStrategy;
    private int offset;
    private FieldMetaData<T> link;

    public boolean isAssociation() {
        return association;
    }

    public void setAssociation(boolean association) {
        this.association = association;
    }

    private boolean association;

    public FieldMetaData(T field, OffsetStrategy<T> offsetStrategy, int offset, FieldMetaData<T> link, boolean association) {
        this.field = field;
        this.offsetStrategy = offsetStrategy;
        this.offset = offset;
        this.link = link;
        this.association = association;
    }

    public T getField() {
        return field;
    }

    public void setField(T field) {
        this.field = field;
    }

    public OffsetStrategy<T> getOffsetStrategy() {
        return offsetStrategy;
    }

    public void setOffsetStrategy(OffsetStrategy<T> offsetStrategy) {
        this.offsetStrategy = offsetStrategy;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public FieldMetaData<T> getLink() {
        return link;
    }

    public void setLink(FieldMetaData<T> link) {
        this.link = link;
    }
}