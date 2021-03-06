/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.mymock.nutch.generated;  
@SuppressWarnings("all")
public class DoubleZi extends org.apache.gora.persistency.impl.PersistentBase implements org.apache.avro.specific.SpecificRecord, org.apache.gora.persistency.Persistent {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"DoubleZi\",\"namespace\":\"com.mymock.nutch.generated\",\"fields\":[{\"name\":\"zipair\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"metric\",\"type\":\"long\",\"default\":0}],\"default\":null}");

  /** Enum containing all data bean's fields. */
  public static enum Field {
    ZIPAIR(0, "zipair"),
    METRIC(1, "metric"),
    ;
    /**
     * Field's index.
     */
    private int index;

    /**
     * Field's name.
     */
    private String name;

    /**
     * Field's constructor
     * @param index field's index.
     * @param name field's name.
     */
    Field(int index, String name) {this.index=index;this.name=name;}

    /**
     * Gets field's index.
     * @return int field's index.
     */
    public int getIndex() {return index;}

    /**
     * Gets field's name.
     * @return String field's name.
     */
    public String getName() {return name;}

    /**
     * Gets field's attributes to string.
     * @return String field's attributes to string.
     */
    public String toString() {return name;}
  };

  public static final String[] _ALL_FIELDS = {
  "zipair",
  "metric",
  };

  /**
   * Gets the total field count.
   * @return int field count
   */
  public int getFieldsCount() {
    return DoubleZi._ALL_FIELDS.length;
  }

  private java.lang.CharSequence zipair;
  private long metric;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return zipair;
    case 1: return metric;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value) {
    switch (field$) {
    case 0: zipair = (java.lang.CharSequence)(value); break;
    case 1: metric = (java.lang.Long)(value); break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'zipair' field.
   */
  public java.lang.CharSequence getZipair() {
    return zipair;
  }

  /**
   * Sets the value of the 'zipair' field.
   * @param value the value to set.
   */
  public void setZipair(java.lang.CharSequence value) {
    this.zipair = value;
    setDirty(0);
  }
  
  /**
   * Checks the dirty status of the 'zipair' field. A field is dirty if it represents a change that has not yet been written to the database.
   * @param value the value to set.
   */
  public boolean isZipairDirty() {
    return isDirty(0);
  }

  /**
   * Gets the value of the 'metric' field.
   */
  public java.lang.Long getMetric() {
    return metric;
  }

  /**
   * Sets the value of the 'metric' field.
   * @param value the value to set.
   */
  public void setMetric(java.lang.Long value) {
    this.metric = value;
    setDirty(1);
  }
  
  /**
   * Checks the dirty status of the 'metric' field. A field is dirty if it represents a change that has not yet been written to the database.
   * @param value the value to set.
   */
  public boolean isMetricDirty() {
    return isDirty(1);
  }

  /** Creates a new DoubleZi RecordBuilder */
  public static com.mymock.nutch.generated.DoubleZi.Builder newBuilder() {
    return new com.mymock.nutch.generated.DoubleZi.Builder();
  }
  
  /** Creates a new DoubleZi RecordBuilder by copying an existing Builder */
  public static com.mymock.nutch.generated.DoubleZi.Builder newBuilder(com.mymock.nutch.generated.DoubleZi.Builder other) {
    return new com.mymock.nutch.generated.DoubleZi.Builder(other);
  }
  
  /** Creates a new DoubleZi RecordBuilder by copying an existing DoubleZi instance */
  public static com.mymock.nutch.generated.DoubleZi.Builder newBuilder(com.mymock.nutch.generated.DoubleZi other) {
    return new com.mymock.nutch.generated.DoubleZi.Builder(other);
  }
  
  private static java.nio.ByteBuffer deepCopyToReadOnlyBuffer(
      java.nio.ByteBuffer input) {
    java.nio.ByteBuffer copy = java.nio.ByteBuffer.allocate(input.capacity());
    int position = input.position();
    input.reset();
    int mark = input.position();
    int limit = input.limit();
    input.rewind();
    input.limit(input.capacity());
    copy.put(input);
    input.rewind();
    copy.rewind();
    input.position(mark);
    input.mark();
    copy.position(mark);
    copy.mark();
    input.position(position);
    copy.position(position);
    input.limit(limit);
    copy.limit(limit);
    return copy.asReadOnlyBuffer();
  }
  
  /**
   * RecordBuilder for DoubleZi instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<DoubleZi>
    implements org.apache.avro.data.RecordBuilder<DoubleZi> {

    private java.lang.CharSequence zipair;
    private long metric;

    /** Creates a new Builder */
    private Builder() {
      super(com.mymock.nutch.generated.DoubleZi.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.mymock.nutch.generated.DoubleZi.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing DoubleZi instance */
    private Builder(com.mymock.nutch.generated.DoubleZi other) {
            super(com.mymock.nutch.generated.DoubleZi.SCHEMA$);
      if (isValidValue(fields()[0], other.zipair)) {
        this.zipair = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.zipair);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.metric)) {
        this.metric = (java.lang.Long) data().deepCopy(fields()[1].schema(), other.metric);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'zipair' field */
    public java.lang.CharSequence getZipair() {
      return zipair;
    }
    
    /** Sets the value of the 'zipair' field */
    public com.mymock.nutch.generated.DoubleZi.Builder setZipair(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.zipair = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'zipair' field has been set */
    public boolean hasZipair() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'zipair' field */
    public com.mymock.nutch.generated.DoubleZi.Builder clearZipair() {
      zipair = null;
      fieldSetFlags()[0] = false;
      return this;
    }
    
    /** Gets the value of the 'metric' field */
    public java.lang.Long getMetric() {
      return metric;
    }
    
    /** Sets the value of the 'metric' field */
    public com.mymock.nutch.generated.DoubleZi.Builder setMetric(long value) {
      validate(fields()[1], value);
      this.metric = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'metric' field has been set */
    public boolean hasMetric() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'metric' field */
    public com.mymock.nutch.generated.DoubleZi.Builder clearMetric() {
      fieldSetFlags()[1] = false;
      return this;
    }
    
    @Override
    public DoubleZi build() {
      try {
        DoubleZi record = new DoubleZi();
        record.zipair = fieldSetFlags()[0] ? this.zipair : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.metric = fieldSetFlags()[1] ? this.metric : (java.lang.Long) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
  
  public DoubleZi.Tombstone getTombstone(){
  	return TOMBSTONE;
  }

  public DoubleZi newInstance(){
    return newBuilder().build();
  }

  private static final Tombstone TOMBSTONE = new Tombstone();
  
  public static final class Tombstone extends DoubleZi implements org.apache.gora.persistency.Tombstone {
  
      private Tombstone() { }
  
	  		  /**
	   * Gets the value of the 'zipair' field.
		   */
	  public java.lang.CharSequence getZipair() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the 'zipair' field.
		   * @param value the value to set.
	   */
	  public void setZipair(java.lang.CharSequence value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the 'zipair' field. A field is dirty if it represents a change that has not yet been written to the database.
		   * @param value the value to set.
	   */
	  public boolean isZipairDirty() {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
				  /**
	   * Gets the value of the 'metric' field.
		   */
	  public java.lang.Long getMetric() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the 'metric' field.
		   * @param value the value to set.
	   */
	  public void setMetric(java.lang.Long value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the 'metric' field. A field is dirty if it represents a change that has not yet been written to the database.
		   * @param value the value to set.
	   */
	  public boolean isMetricDirty() {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
		  
  }
  
}

