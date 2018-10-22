// code by astoll
package ch.ethz.idsc.owl.balloon;

import ch.ethz.idsc.owl.math.StateSpaceModel;
import ch.ethz.idsc.owl.math.StateSpaceModels;
import ch.ethz.idsc.owl.math.flow.EulerIntegrator;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class BalloonStateSpaceModelTest extends TestCase {
  public void testValidity() {
    Tensor x = Tensors.fromString("{2[m],2[m*s^-1],4[K]}");
    Tensor u = Tensors.fromString("{3[K*s^-1]}");
    Tensor expected = Tensors.fromString("{2[m*s^-1],2[m*s^-2],-1[K*s^-1]}");
    StateSpaceModel stateSpaceModel = BalloonStateSpaceModels.defaultWithUnits();
    assertEquals(expected, stateSpaceModel.f(x, u));
    Flow flow = StateSpaceModels.createFlow(stateSpaceModel, u);
    Tensor tensor = EulerIntegrator.INSTANCE.step(flow, x, Quantity.of(2, "s"));
    assertEquals(tensor, Tensors.fromString("{6[m], 6[m*s^-1], 2[K]}"));
    assertTrue(ExactScalarQ.all(tensor));
  }
}