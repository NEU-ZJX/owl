// code by ynager
package ch.ethz.idsc.owl.glc.rl;

import java.util.Collection;
import java.util.Optional;

import ch.ethz.idsc.owl.glc.core.GlcNode;
import ch.ethz.idsc.owl.math.VectorScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Entrywise;

/** priority queue with ordering defined by {@link NodeMeritComparator} */
public class RLDomainQueue extends RLQueue {
  /** @param glcNode
   * @param slacks
   * @return relaxed lexicographic domain queue that contains given glcNode as single element */
  public static RLDomainQueue singleton(GlcNode glcNode, Tensor slacks) {
    RLDomainQueue domainQueue = new RLDomainQueue(slacks);
    domainQueue.add(glcNode);
    return domainQueue;
  }

  /** @param slacks
   * @return */
  public static RLDomainQueue empty(Tensor slacks) {
    return new RLDomainQueue(slacks);
  }

  // ---
  private RLDomainQueue(Tensor slacks) {
    super(slacks);
  }

  public Optional<Tensor> getMinValues() {
    return getMinValues(collection(), vectorSize);
  }

  /** @param collection
   * @param vectorSize
   * @return tensor with lowest costs entrywise */
  /* package */ static Optional<Tensor> getMinValues(Collection<GlcNode> collection, int vectorSize) {
    return collection.stream() //
        .map(GlcNode::merit) //
        .map(VectorScalar.class::cast) //
        .map(VectorScalar::vector) //
        .reduce(Entrywise.min());
  }
}
