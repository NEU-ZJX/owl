// code by jph
package ch.ethz.idsc.sophus.app.misc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.stream.Collectors;

import ch.ethz.idsc.owl.gui.GraphicsUtil;
import ch.ethz.idsc.owl.gui.win.GeometricLayer;
import ch.ethz.idsc.owl.math.map.Se2Utils;
import ch.ethz.idsc.owl.math.planar.Arrowhead;
import ch.ethz.idsc.sophus.app.api.AbstractDemo;
import ch.ethz.idsc.sophus.dubins.DubinsPath;
import ch.ethz.idsc.sophus.dubins.DubinsPathLengthComparator;
import ch.ethz.idsc.sophus.dubins.FixedRadiusDubins;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ColorDataIndexed;
import ch.ethz.idsc.tensor.img.ColorDataLists;

/* package */ class DubinsPathDemo extends AbstractDemo {
  private static final Tensor ARROWHEAD = Arrowhead.of(.5);
  private static final ColorDataIndexed COLOR_DATA_INDEXED = ColorDataLists._097.cyclic();

  @Override // from RenderInterface
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    GraphicsUtil.setQualityHigh(graphics);
    final Tensor mouse = geometricLayer.getMouseSe2State();
    {
      graphics.setColor(Color.GREEN);
      geometricLayer.pushMatrix(Se2Utils.toSE2Matrix(mouse));
      graphics.fill(geometricLayer.toPath2D(ARROWHEAD));
      geometricLayer.popMatrix();
    }
    // ---
    FixedRadiusDubins fixedRadiusDubins = new FixedRadiusDubins(mouse, RealScalar.of(1));
    graphics.setColor(COLOR_DATA_INDEXED.getColor(0));
    for (DubinsPath dubinsPath : fixedRadiusDubins.allValid().collect(Collectors.toList()))
      graphics.draw(geometricLayer.toPath2D(sample(dubinsPath)));
    {
      DubinsPath dubinsPath = fixedRadiusDubins.allValid().min(DubinsPathLengthComparator.INSTANCE).get();
      graphics.setColor(COLOR_DATA_INDEXED.getColor(1));
      graphics.setStroke(new BasicStroke(1.5f));
      graphics.draw(geometricLayer.toPath2D(sample(dubinsPath)));
      graphics.setStroke(new BasicStroke(1f));
    }
  }

  private static Tensor sample(DubinsPath dubinsPath) {
    return Subdivide.of(RealScalar.ZERO, dubinsPath.length(), 200) //
        .map(dubinsPath.sampler(Array.zeros(3)));
  }

  public static void main(String[] args) {
    AbstractDemo abstractDemo = new DubinsPathDemo();
    abstractDemo.timerFrame.jFrame.setBounds(100, 100, 1000, 600);
    abstractDemo.timerFrame.jFrame.setVisible(true);
  }
}