package com.diachuk.library.services;

import java.util.Arrays;

/**
 * Created by VA-N_ on 01.02.2017.
 */
public class MessageBuilder {
    /**
     * The value is used for character storage.
     */
    char[] value;
    /**
     * The count is the number of characters used.
     */
    int count;

    /**
     * This no-arg constructor is necessary for serialization of subclasses.
     */
    MessageBuilder() {
    }

    /**
     * Creates an AbstractStringBuilder of the specified capacity.
     */
    MessageBuilder(int capacity) {
        value = new char[capacity];
    }

    /**
     * Returns the length (character count).
     *
     * @return  the length of the sequence of characters currently
     *          represented by this object
     */
    public int length() {
        return count;
    }

    /**
     * Returns the current capacity. The capacity is the amount of storage
     * available for newly inserted characters, beyond which an allocation
     * will occur.
     *
     * @return  the current capacity
     */
    public int capacity() {
        return value.length;
    }

    /**
     * Ensures that the capacity is at least equal to the specified minimum.
     * If the current capacity is less than the argument, then a new internal
     * array is allocated with greater capacity. The new capacity is the
     * larger of:
     * <ul>
     * <li>The {@code minimumCapacity} argument.
     * <li>Twice the old capacity, plus {@code 2}.
     * </ul>
     * If the {@code minimumCapacity} argument is nonpositive, this
     * method takes no action and simply returns.
     * Note that subsequent operations on this object can reduce the
     * actual capacity below that requested here.
     *
     * @param   minimumCapacity   the minimum desired capacity.
     */
    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > 0)
            ensureCapacityInternal(minimumCapacity);
    }

    /**
     * This method has the same contract as ensureCapacity, but is
     * never synchronized.
     */
    private void ensureCapacityInternal(int minimumCapacity) {
        if (minimumCapacity - value.length > 0)
            expandCapacity(minimumCapacity);
    }

    /**
     * This implements the expansion semantics of ensureCapacity with no
     * size check or synchronization.
     */
    void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        if (newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value, newCapacity);
    }

    /**
     * Attempts to reduce storage used for the character sequence.
     * If the buffer is larger than necessary to hold its current sequence of
     * characters, then it may be resized to become more space efficient.
     * Calling this method may, but is not required to, affect the value
     * returned by a subsequent call to the {@link #capacity()} method.
     */
    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    /**
     * Characters are copied from this sequence into the
     * destination character array {@code dst}. The first character to
     * be copied is at index {@code srcBegin}; the last character to
     * be copied is at index {@code srcEnd-1}. The total number of
     * characters to be copied is {@code srcEnd-srcBegin}. The
     * characters are copied into the subarray of {@code dst} starting
     * at index {@code dstBegin} and ending at index:
     * <pre>{@code
     * dstbegin + (srcEnd-srcBegin) - 1
     * }</pre>
     *
     * @param      srcBegin   start copying at this offset.
     * @param      srcEnd     stop copying at this offset.
     * @param      dst        the array to copy the data into.
     * @param      dstBegin   offset into {@code dst}.
     * @throws     IndexOutOfBoundsException  if any of the following is true:
     *             <ul>
     *             <li>{@code srcBegin} is negative
     *             <li>{@code dstBegin} is negative
     *             <li>the {@code srcBegin} argument is greater than
     *             the {@code srcEnd} argument.
     *             <li>{@code srcEnd} is greater than
     *             {@code this.length()}.
     *             <li>{@code dstBegin+srcEnd-srcBegin} is greater than
     *             {@code dst.length}
     *             </ul>
     */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    /**
     * Appends the string representation of the {@code Object} argument.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(Object)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   obj   an {@code Object}.
     * @return  a reference to this object.
     */
    public MessageBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }

    /**
     * Appends the specified string to this character sequence.
     * <p>
     * The characters of the {@code String} argument are appended, in
     * order, increasing the length of this sequence by the length of the
     * argument. If {@code str} is {@code null}, then the four
     * characters {@code "null"} are appended.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in the new character sequence is equal to the character
     * at index <i>k</i> in the old character sequence, if <i>k</i> is less
     * than <i>n</i>; otherwise, it is equal to the character at index
     * <i>k-n</i> in the argument {@code str}.
     *
     * @param   str   a string.
     * @return  a reference to this object.
     */
    public MessageBuilder append(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }

    private MessageBuilder appendNull() {
        int c = count;
        ensureCapacityInternal(c + 4);
        final char[] value = this.value;
        value[c++] = 'n';
        value[c++] = 'u';
        value[c++] = 'l';
        value[c++] = 'l';
        count = c;
        return this;
    }

    @Override
    public String toString() {
        return new String(value, 0, count);
    }

}
