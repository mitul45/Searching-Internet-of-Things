/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'PullMsg'
 * message type.
 */

public class PullMsg extends net.tinyos.message.Message {

    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 6;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = 7;

    /** Create a new PullMsg of size 6. */
    public PullMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new PullMsg of the given data_length. */
    public PullMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new PullMsg with the given data_length
     * and base offset.
     */
    public PullMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new PullMsg using the given byte array
     * as backing store.
     */
    public PullMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new PullMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public PullMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new PullMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public PullMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new PullMsg embedded in the given message
     * at the given base offset.
     */
    public PullMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new PullMsg embedded in the given message
     * at the given base offset and length.
     */
    public PullMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <PullMsg> \n";
      try {
        s += "  [requiredid=0x"+Long.toHexString(get_requiredid())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [mediatorid=0x"+Long.toHexString(get_mediatorid())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [flag=0x"+Long.toHexString(get_flag())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: requiredid
    //   Field type: int, unsigned
    //   Offset (bits): 0
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'requiredid' is signed (false).
     */
    public static boolean isSigned_requiredid() {
        return false;
    }

    /**
     * Return whether the field 'requiredid' is an array (false).
     */
    public static boolean isArray_requiredid() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'requiredid'
     */
    public static int offset_requiredid() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'requiredid'
     */
    public static int offsetBits_requiredid() {
        return 0;
    }

    /**
     * Return the value (as a int) of the field 'requiredid'
     */
    public int get_requiredid() {
        return (int)getUIntBEElement(offsetBits_requiredid(), 16);
    }

    /**
     * Set the value of the field 'requiredid'
     */
    public void set_requiredid(int value) {
        setUIntBEElement(offsetBits_requiredid(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'requiredid'
     */
    public static int size_requiredid() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'requiredid'
     */
    public static int sizeBits_requiredid() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: mediatorid
    //   Field type: int, unsigned
    //   Offset (bits): 16
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'mediatorid' is signed (false).
     */
    public static boolean isSigned_mediatorid() {
        return false;
    }

    /**
     * Return whether the field 'mediatorid' is an array (false).
     */
    public static boolean isArray_mediatorid() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'mediatorid'
     */
    public static int offset_mediatorid() {
        return (16 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'mediatorid'
     */
    public static int offsetBits_mediatorid() {
        return 16;
    }

    /**
     * Return the value (as a int) of the field 'mediatorid'
     */
    public int get_mediatorid() {
        return (int)getUIntBEElement(offsetBits_mediatorid(), 16);
    }

    /**
     * Set the value of the field 'mediatorid'
     */
    public void set_mediatorid(int value) {
        setUIntBEElement(offsetBits_mediatorid(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'mediatorid'
     */
    public static int size_mediatorid() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'mediatorid'
     */
    public static int sizeBits_mediatorid() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: flag
    //   Field type: int, unsigned
    //   Offset (bits): 32
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'flag' is signed (false).
     */
    public static boolean isSigned_flag() {
        return false;
    }

    /**
     * Return whether the field 'flag' is an array (false).
     */
    public static boolean isArray_flag() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'flag'
     */
    public static int offset_flag() {
        return (32 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'flag'
     */
    public static int offsetBits_flag() {
        return 32;
    }

    /**
     * Return the value (as a int) of the field 'flag'
     */
    public int get_flag() {
        return (int)getUIntBEElement(offsetBits_flag(), 16);
    }

    /**
     * Set the value of the field 'flag'
     */
    public void set_flag(int value) {
        setUIntBEElement(offsetBits_flag(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'flag'
     */
    public static int size_flag() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'flag'
     */
    public static int sizeBits_flag() {
        return 16;
    }

}
