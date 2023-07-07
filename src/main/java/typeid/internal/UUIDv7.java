package typeid.internal;

import java.nio.ByteBuffer;
import java.time.Clock;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A time based <a
 * href="https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#section-5.2">v7
 * UUID</a>
 */
public record UUIDv7(byte[] bytes) {

  /**
   * generates a new UUID with the default system clock and current thread local random
   *
   * @return a new UUIDv7 instance
   */
  public static UUIDv7 generate() {
    return generate(Clock.systemDefaultZone(), ThreadLocalRandom.current());
  }

  public static UUIDv7 generate(Clock clock, Random random) {
    var rand = new byte[10];
    random.nextBytes(rand);
    return create(clock.millis(), rand);
  }

  private static UUIDv7 create(long millis, byte[] rand) {
    var l1 = (millis << 16) | toShort(rand, 0);
    var l2 = toLong(rand, 2);
    l1 &= ~0xF000L;
    l1 |= (long) (7 << 12);
    l2 = ((l2 << 2) >>> 2);
    l2 |= (2L << 62);
    var buf = ByteBuffer.wrap(new byte[16]);
    buf.putLong(l1);
    buf.putLong(l2);
    return new UUIDv7(buf.array());
  }

  private static long toLong(byte[] buf, int offset) {
    long l1 = toInt(buf, offset);
    long l2 = toInt(buf, offset + 4);
    long l = (l1 << 32) + ((l2 << 32) >>> 32);
    return l;
  }

  private static long toShort(byte[] buf, int offset) {
    return (buf[offset] << 24)
        + ((buf[++offset] & 0xFF) << 16)
        + ((buf[++offset] & 0xFF) << 8)
        + (buf[++offset] & 0xFF);
  }

  static long toInt(byte[] buf, int offset) {
    return ((buf[offset] & 0xFF) << 8) + (buf[++offset] & 0xFF);
  }

  /**
   * Return a UUID representation of this TypeID
   *
   * @return a uuid instance
   */
  public UUID uuid() {
    var buf = ByteBuffer.wrap(bytes);
    return new UUID(buf.getLong(), buf.getLong());
  }

  public static UUIDv7 from(UUID uuid) {
    var buf = ByteBuffer.wrap(new byte[16]);
    buf.putLong(uuid.getMostSignificantBits());
    buf.putLong(uuid.getLeastSignificantBits());
    return new UUIDv7(buf.array());
  }

  @Override
  public String toString() {
    return uuid().toString();
  }
}
