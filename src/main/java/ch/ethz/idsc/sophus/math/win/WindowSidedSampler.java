// code by ob
package ch.ethz.idsc.sophus.math.win;

import java.util.function.Function;

import ch.ethz.idsc.sophus.util.MemoFunction;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

// TODO JPH OWL 045 remove
public class WindowSidedSampler extends WindowBaseSampler {
  /** @param windowFunction for evaluation in the interval [-1/2, +1/2] */
  public static Function<Integer, Tensor> of(ScalarUnaryOperator windowFunction) {
    return MemoFunction.wrap(new WindowSidedSampler(windowFunction));
  }

  // ---
  private WindowSidedSampler(ScalarUnaryOperator windowFunction) {
    super(windowFunction);
  }

  @Override // from WindowBaseSampler
  protected Tensor samples(int extent) {
    return isContinuous //
        ? Subdivide.of(RationalScalar.HALF.negate(), RealScalar.ZERO, extent + 1) //
            .map(windowFunction) //
            .extract(1, extent + 2)
        : Subdivide.of(RationalScalar.HALF.negate(), RealScalar.ZERO, extent) //
            .map(windowFunction);
  }
}
