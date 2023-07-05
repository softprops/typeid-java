package typeid;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class TypeIDBench {

  @Benchmark
  public void fromString(Blackhole bh) {
    bh.consume(TypeID.fromString("prefix_01h455vb4pex5vsknk084sn02q"));
  }

  @Benchmark
  public void create(Blackhole bh) {
    bh.consume(new TypeID("bench"));
  }

  @Benchmark
  public void createToString(Blackhole bh) {
    bh.consume(new TypeID("bench").toString());
  }
}