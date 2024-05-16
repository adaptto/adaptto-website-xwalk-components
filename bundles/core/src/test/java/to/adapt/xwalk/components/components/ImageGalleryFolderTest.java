package to.adapt.xwalk.components.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import to.adapt.xwalk.components.testcontext.AppAemContext;

@ExtendWith(AemContextExtension.class)
class ImageGalleryFolderTest {

  private final AemContext context = AppAemContext.newAemContext();

  private Page page;

  @BeforeEach
  void setUp() {
    page = context.create().page("/content/test");
  }

  @Test
  @SuppressWarnings("null")
  void testNoAssetFolderPath() {
    context.currentResource(context.create().resource(page, "imageGallery"));

    ImageGalleryFolder underTest = context.request().adaptTo(ImageGalleryFolder.class);

    List<String> urls = underTest.getImageUrls();
    assertTrue(urls.isEmpty());
  }

  @Test
  @SuppressWarnings("null")
  void testEmptyAssetFolderPath() {
    context.create().resource("/content/dam/folder");

    context.currentResource(context.create().resource(page, "imageGallery",
        "assetFolderPath", "/content/dam/folder"));

    ImageGalleryFolder underTest = context.request().adaptTo(ImageGalleryFolder.class);

    List<String> urls = underTest.getImageUrls();
    assertTrue(urls.isEmpty());
  }

  @Test
  @SuppressWarnings("null")
  void testWithAssets() {
    context.create().resource("/content/dam/folder");

    context.create().asset("/content/dam/folder/asset1.jpg", 10, 10, "image/jpeg");
    context.create().asset("/content/dam/folder/asset2.jpg", 10, 10, "image/jpeg");

    context.currentResource(context.create().resource(page, "imageGallery",
        "assetFolderPath", "/content/dam/folder"));

    ImageGalleryFolder underTest = context.request().adaptTo(ImageGalleryFolder.class);

    List<String> urls = underTest.getImageUrls();
    assertEquals(List.of("/content/dam/folder/asset1.jpg", "/content/dam/folder/asset2.jpg"), urls);
  }

}
