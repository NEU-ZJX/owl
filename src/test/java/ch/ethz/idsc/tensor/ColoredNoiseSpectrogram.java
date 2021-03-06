// code by jph
package ch.ethz.idsc.tensor;

import java.io.IOException;
import java.util.stream.DoubleStream;

import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.HomeDirectory;

/* package */ enum ColoredNoiseSpectrogram {
  ;
  public static void main(String[] args) throws IOException {
    ColoredNoise coloredNoise = new ColoredNoise(1); // 1 == pink noise
    Tensor tensor = Tensor.of(DoubleStream.generate(coloredNoise::nextValue) //
        .limit(1024 * 4).mapToObj(DoubleScalar::of));
    Tensor image = Spectrogram.of(tensor);
    Export.of(HomeDirectory.Pictures(ColoredNoiseSpectrogram.class.getSimpleName() + ".png"), image);
  }
}
