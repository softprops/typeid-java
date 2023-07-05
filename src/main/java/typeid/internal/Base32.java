package typeid.internal;

import java.util.Optional;

/** a basic <a href="https://www.crockford.com/base32.html">crockford</a> impl */
public class Base32 {
  private static final char[] ALPHABET = "0123456789abcdefghjkmnpqrstvwxyz".toCharArray();
  private static final int DECODE[] = {
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x00, 0x01,
    0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E,
    0x0F, 0x10, 0x11, 0xFF, 0x12, 0x13, 0xFF, 0x14, 0x15, 0xFF,
    0x16, 0x17, 0x18, 0x19, 0x1A, 0xFF, 0x1B, 0x1C, 0x1D, 0x1E,
    0x1F, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x0A, 0x0B, 0x0C,
    0x0D, 0x0E, 0x0F, 0x10, 0x11, 0xFF, 0x12, 0x13, 0xFF, 0x14,
    0x15, 0xFF, 0x16, 0x17, 0x18, 0x19, 0x1A, 0xFF, 0x1B, 0x1C,
    0x1D, 0x1E, 0x1F, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF
  };

  public static Optional<byte[]> decode(byte[] src) {
    if (src.length != 26) {
      return Optional.empty();
    }
    if (DECODE[src[0]] == 0xFF
        || DECODE[src[1]] == 0xFF
        || DECODE[src[2]] == 0xFF
        || DECODE[src[3]] == 0xFF
        || DECODE[src[4]] == 0xFF
        || DECODE[src[5]] == 0xFF
        || DECODE[src[6]] == 0xFF
        || DECODE[src[7]] == 0xFF
        || DECODE[src[8]] == 0xFF
        || DECODE[src[9]] == 0xFF
        || DECODE[src[10]] == 0xFF
        || DECODE[src[11]] == 0xFF
        || DECODE[src[12]] == 0xFF
        || DECODE[src[13]] == 0xFF
        || DECODE[src[14]] == 0xFF
        || DECODE[src[15]] == 0xFF
        || DECODE[src[16]] == 0xFF
        || DECODE[src[17]] == 0xFF
        || DECODE[src[18]] == 0xFF
        || DECODE[src[19]] == 0xFF
        || DECODE[src[20]] == 0xFF
        || DECODE[src[21]] == 0xFF
        || DECODE[src[22]] == 0xFF
        || DECODE[src[23]] == 0xFF
        || DECODE[src[24]] == 0xFF
        || DECODE[src[25]] == 0xFF) {
      return Optional.empty();
    }

    return Optional.of(
        new byte[] {
          // 6 bytes timestamp (48 bits)
          (byte) ((DECODE[src[0]] << 5) | DECODE[src[1]]),
          (byte) ((DECODE[src[2]] << 3) | (DECODE[src[3]] >> 2)),
          (byte) (((DECODE[src[3]] & 3) << 6) | (DECODE[src[4]] << 1) | (DECODE[src[5]] >> 4)),
          (byte) (((DECODE[src[5]] & 15) << 4) | (DECODE[src[6]] >> 1)),
          (byte) (((DECODE[src[6]] & 1) << 7) | (DECODE[src[7]] << 2) | (DECODE[src[8]] >> 3)),
          (byte) (((DECODE[src[8]] & 7) << 5) | DECODE[src[9]]),

          // 10 bytes of entropy (80 bits)
          (byte) ((DECODE[src[10]] << 3) | (DECODE[src[11]] >> 2)),
          (byte) (((DECODE[src[11]] & 3) << 6) | (DECODE[src[12]] << 1) | (DECODE[src[13]] >> 4)),
          (byte) (((DECODE[src[13]] & 15) << 4) | (DECODE[src[14]] >> 1)),
          (byte) (((DECODE[src[14]] & 1) << 7) | (DECODE[src[15]] << 2) | (DECODE[src[16]] >> 3)),
          (byte) (((DECODE[src[16]] & 7) << 5) | DECODE[src[17]]),
          (byte) ((DECODE[src[18]] << 3) | (DECODE[src[19]] >> 2)),
          (byte) (((DECODE[src[19]] & 3) << 6) | (DECODE[src[20]] << 1) | (DECODE[src[21]] >> 4)),
          (byte) (((DECODE[src[21]] & 15) << 4) | (DECODE[src[22]] >> 1)),
          (byte) (((DECODE[src[22]] & 1) << 7) | (DECODE[src[23]] << 2) | (DECODE[src[24]] >> 3)),
          (byte) (((DECODE[src[24]] & 7) << 5) | DECODE[src[25]])
        });
  }

  public static Optional<String> encode(byte[] src) {
    if (src.length != 16) {
      return Optional.empty();
    }
    // 10 byte timestamp
    return Optional.of(
        new StringBuilder(26)
            // 10 byte timestamp
            .append(ALPHABET[(src[0] & 224) >> 5])
            .append(ALPHABET[src[0] & 31])
            .append(ALPHABET[(src[1] & 248) >> 3])
            .append(ALPHABET[((src[1] & 7) << 2) | ((src[2] & 192) >> 6)])
            .append(ALPHABET[(src[2] & 62) >> 1])
            .append(ALPHABET[((src[2] & 1) << 4) | ((src[3] & 240) >> 4)])
            .append(ALPHABET[((src[3] & 15) << 1) | ((src[4] & 128) >> 7)])
            .append(ALPHABET[(src[4] & 124) >> 2])
            .append(ALPHABET[((src[4] & 3) << 3) | ((src[5] & 224) >> 5)])
            .append(ALPHABET[src[5] & 31])

            // 16 bytes of randomness
            .append(ALPHABET[(src[6] & 248) >> 3])
            .append(ALPHABET[((src[6] & 7) << 2) | ((src[7] & 192) >> 6)])
            .append(ALPHABET[(src[7] & 62) >> 1])
            .append(ALPHABET[((src[7] & 1) << 4) | ((src[8] & 240) >> 4)])
            .append(ALPHABET[((src[8] & 15) << 1) | ((src[9] & 128) >> 7)])
            .append(ALPHABET[(src[9] & 124) >> 2])
            .append(ALPHABET[((src[9] & 3) << 3) | ((src[10] & 224) >> 5)])
            .append(ALPHABET[src[10] & 31])
            .append(ALPHABET[(src[11] & 248) >> 3])
            .append(ALPHABET[((src[11] & 7) << 2) | ((src[12] & 192) >> 6)])
            .append(ALPHABET[(src[12] & 62) >> 1])
            .append(ALPHABET[((src[12] & 1) << 4) | ((src[13] & 240) >> 4)])
            .append(ALPHABET[((src[13] & 15) << 1) | ((src[14] & 128) >> 7)])
            .append(ALPHABET[(src[14] & 124) >> 2])
            .append(ALPHABET[((src[14] & 3) << 3) | ((src[15] & 224) >> 5)])
            .append(ALPHABET[src[15] & 31])
            .toString());
  }
}
