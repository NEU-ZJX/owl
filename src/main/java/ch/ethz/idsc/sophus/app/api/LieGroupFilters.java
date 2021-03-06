// code by ob, jph
package ch.ethz.idsc.sophus.app.api;

import ch.ethz.idsc.sophus.filter.bm.BiinvariantMeanCenter;
import ch.ethz.idsc.sophus.filter.ga.GeodesicCenter;
import ch.ethz.idsc.sophus.filter.ga.GeodesicCenterMidSeeded;
import ch.ethz.idsc.sophus.filter.ts.TangentSpaceCenter;
import ch.ethz.idsc.sophus.lie.BiinvariantMean;
import ch.ethz.idsc.sophus.lie.LieExponential;
import ch.ethz.idsc.sophus.lie.LieGroup;
import ch.ethz.idsc.sophus.math.GeodesicInterface;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

public enum LieGroupFilters {
  GEODESIC {
    @Override
    public TensorUnaryOperator supply( //
        GeodesicInterface geodesicInterface, ScalarUnaryOperator smoothingKernel, //
        LieGroup lieGroup, LieExponential lieExponential, BiinvariantMean biinvariantMean) {
      return GeodesicCenter.of(geodesicInterface, smoothingKernel);
    }
  }, //
  GEODESIC_MID {
    @Override
    public TensorUnaryOperator supply( //
        GeodesicInterface geodesicInterface, ScalarUnaryOperator smoothingKernel, //
        LieGroup lieGroup, LieExponential lieExponential, BiinvariantMean biinvariantMean) {
      return GeodesicCenterMidSeeded.of(geodesicInterface, smoothingKernel);
    }
  }, //
  TANGENT_SPACE {
    @Override
    public TensorUnaryOperator supply( //
        GeodesicInterface geodesicInterface, ScalarUnaryOperator smoothingKernel, //
        LieGroup lieGroup, LieExponential lieExponential, BiinvariantMean biinvariantMean) {
      return TangentSpaceCenter.of(lieGroup, lieExponential, smoothingKernel);
    }
  }, //
  BIINVARIANT_MEAN {
    @Override
    public TensorUnaryOperator supply( //
        GeodesicInterface geodesicInterface, ScalarUnaryOperator smoothingKernel, //
        LieGroup lieGroup, LieExponential lieExponential, BiinvariantMean biinvariantMean) {
      return BiinvariantMeanCenter.of(biinvariantMean, smoothingKernel);
    }
  }, //
  ;
  // ---
  public abstract TensorUnaryOperator supply( //
      GeodesicInterface geodesicInterface, ScalarUnaryOperator smoothingKernel, //
      LieGroup lieGroup, LieExponential lieExponential, BiinvariantMean biinvariantMean);
}