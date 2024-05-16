package to.adapt.xwalk.components.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.IterableUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.jetbrains.annotations.NotNull;

import com.day.cq.dam.api.Asset;

import to.adapt.xwalk.components.util.NameResourceComparator;

/**
 * Provides list of image gallery asset URLs located in a configurable asset folder paths, ordered by name.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ImageGalleryFolder {

  private List<String> imageUrls;

  @SlingObject
  private ResourceResolver resourceResolver;
  @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
  private String assetFolderPath;

  @PostConstruct
  private void activate() {
    if (assetFolderPath != null) {
      this.imageUrls = getImageUrlFromAssetFolder(assetFolderPath);
    }
    else {
      this.imageUrls = Collections.emptyList();
    }
  }

  private List<String> getImageUrlFromAssetFolder(@NotNull String folderPath) {
    List<String> result = new ArrayList<>();
    Resource folder = resourceResolver.getResource(folderPath);
    if (folder != null) {
      // sort media assets by name
      SortedSet<Resource> assetResources = new TreeSet<>(new NameResourceComparator());
      assetResources.addAll(IterableUtils.toList(folder.getChildren()));

      for (Resource assetResource : assetResources) {
        Asset asset = assetResource.adaptTo(Asset.class);
        if (asset != null) {
          result.add(asset.getPath());
        }
      }
    }
    return result;
  }

  /**
   * @return Image Gallery URLs
   */
  public List<String> getImageUrls() {
    return Collections.unmodifiableList(this.imageUrls);
  }

}
