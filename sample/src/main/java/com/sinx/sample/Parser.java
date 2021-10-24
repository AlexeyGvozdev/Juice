package com.sinx.sample;

public class Parser {

    static void read(byte [] data) {

        int dataBegin = data [0];           // Once field is 1-byte, it is simple!
        int version = data [1] & 0x0F;      // Use shift (>>>) and binary "and" (&)
        int returnCode =                    // to extract value of fields that are
                (data [1] >>> 4) & 0x0F;        // smaller than one byte
        byte [] productCode =               // Copy fixed-size portions of data
                new byte [] {                   // into separate arrays using hardcode
                        data [2], data [3],         // (as here),  or System.arrayCopy
                        data [4], data [5],         // in case field occupies quite
                        data [6]};                  // a many bytes.
        int dataLength =                    // Use shift (<<) binary or (|) to
                (data [7] & 0xFF) |             // Combine several bytes into one integer
                        ((data [8] & 0xFF) << 8);
        int i = 0;
    }
}
