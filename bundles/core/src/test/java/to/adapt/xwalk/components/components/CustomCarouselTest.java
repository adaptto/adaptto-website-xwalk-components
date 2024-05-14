package to.adapt.xwalk.components.components;

import static com.day.cq.commons.DownloadResource.PN_REFERENCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.cq.wcm.core.components.models.Image;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static to.adapt.xwalk.components.components.CustomCarousel.NN_SLIDES;
import to.adapt.xwalk.components.testcontext.AppAemContext;

@ExtendWith(AemContextExtension.class)
class CustomCarouselTest {

  private final AemContext context = AppAemContext.newAemContext();

  private Page page;
  private Resource resource;

  @BeforeEach
  void setUp() {
    page = context.create().page("/content/mypage");
    resource = context.create().resource(page, "myresource");
    context.currentResource(resource);
  }

  @Test
  void testId() {
    CustomCarousel underTest = context.request().adaptTo(CustomCarousel.class);
    assertNotNull(underTest.getId());
  }

  @Test
  void testSlideImageUrls() {
    context.create().asset("/content/dam/slides/slide1.png", 1200, 450, "image/png");
    context.create().asset("/content/dam/slides/slide2.png", 1200, 450, "image/png");

    context.build().resource(resource.getPath() + "/" + NN_SLIDES)
        .siblingsMode()
        .resource("item1", PN_REFERENCE, "/content/dam/slides/slide1.png")
        .resource("item2", PN_REFERENCE, "/content/dam/slides/slide2.png");

    CustomCarousel underTest = context.request().adaptTo(CustomCarousel.class);
    assertEquals(List.of(
        "/content/mypage/_jcr_content/myresource/slides/item1.img.png",
        "/content/mypage/_jcr_content/myresource/slides/item2.img.png"),
        underTest.getSlideImages().stream()
            .map(Image::getSrc)
            .collect(Collectors.toList()));
  }

  @Test
  void testEmptySlideImageUrls() {
    CustomCarousel underTest = context.request().adaptTo(CustomCarousel.class);
    assertTrue(underTest.getSlideImages().isEmpty());
  }

}
