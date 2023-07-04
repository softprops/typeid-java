package typeid.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class UUIDv7Test {
  @Test
  void generate() {
    var clock = Clock.fixed(Instant.parse("2023-06-04T00:00:00.00Z"), ZoneId.of("GMT"));
    var rand = new Random(1L);
    assertEquals("018883b4-73d5-7abb-8000-1abb0000b819", UUIDv7.generate(clock, rand).toString());
  }
}
