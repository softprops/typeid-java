package typeid;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import typeid.internal.Base32;
import typeid.internal.UUIDv7;

/**
 * A human friendly, k-sortable, type prefixed, uuid7 compatible identifier.
 *
 * <p>Values are expected to be constructed with <code>new TypeID("typename");</code>
 */
public record TypeID(String prefix, String suffix) {
  private static final Predicate<String> PREFIX =
      Pattern.compile("^[a-z]{0,62}$").asMatchPredicate();
  private static final Predicate<String> SUFFIX =
      Pattern.compile("^[0-7][0123456789abcdefghjkmnpqrstvwxyz]{1,25}$").asMatchPredicate();
  private static final String DELIMETER = "_";

  public TypeID {
    if (!PREFIX.test(prefix)) {
      throw new IllegalArgumentException("%s is not a valid prefix".formatted(prefix));
    }
    if (!SUFFIX.test(suffix) || Base32.decode(suffix.getBytes()).isEmpty()) {
      throw new IllegalArgumentException("%s is not a valid suffix".formatted(suffix));
    }
  }

  /**
   * create a new TypeID with a given prefix
   *
   * @param prefix prefix indicating the type of this id
   */
  public TypeID(String prefix) {
    this(prefix, Base32.encode(UUIDv7.generate().bytes()).get());
  }

  /** return suffix in standard UUID format */
  public UUID uuid() {
    return new UUIDv7(Base32.decode(suffix.getBytes()).get()).uuid();
  }

  @Override
  public String toString() {
    return switch (prefix) {
      case "" -> suffix;
      default -> prefix + DELIMETER + suffix;
    };
  }

  /**
   * Parses the string representation of a TypeID into an instance of a TypeID type
   *
   * @return Optional of a new TypeID present when valid, absent when not
   */
  public static Optional<TypeID> fromString(String value) {
    var parts = value.split(DELIMETER);
    return switch (parts.length) {
      case 1 -> of("", parts[0]);
      case 2 -> parts[0].isBlank() ? Optional.empty() : of(parts[0], parts[1]);
      default -> Optional.empty();
    };
  }

  private static Optional<TypeID> of(String prefix, String suffix) {
    try {
      return Optional.of(new TypeID(prefix, suffix));
    } catch (IllegalArgumentException __) {
      return Optional.empty();
    }
  }
}
