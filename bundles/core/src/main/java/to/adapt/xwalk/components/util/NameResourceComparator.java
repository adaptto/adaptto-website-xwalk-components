package to.adapt.xwalk.components.util;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.sling.api.resource.Resource;
import org.osgi.annotation.versioning.ProviderType;

/**
 * Orders resources alphabetically by name (ascending).
 */
@ProviderType
public final class NameResourceComparator implements Comparator<Resource>, Serializable {
  private static final long serialVersionUID = 1L;

  @Override
  public int compare(Resource o1, Resource o2) {
    if (o1 == o2) { //NOPMD
      return 0;
    }
    if (o1 == null) {
      return 1;
    }
    if (o2 == null) {
      return -1;
    }
    return o1.getName().compareTo(o2.getName());
  }

}
