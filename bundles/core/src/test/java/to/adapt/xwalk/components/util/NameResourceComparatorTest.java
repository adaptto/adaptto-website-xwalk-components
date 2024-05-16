package to.adapt.xwalk.components.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NameResourceComparatorTest {

  @Mock
  private Resource resource1;
  @Mock
  private Resource resource2;

  private NameResourceComparator underTest;

  @BeforeEach
  void setUp() {
    underTest = new NameResourceComparator();
    when(resource1.getName()).thenReturn("resource1");
    when(resource2.getName()).thenReturn("resource2");
  }

  @Test
  void testCompare() {
    SortedSet<Resource> set = new TreeSet<>(underTest);
    set.add(resource2);
    set.add(resource1);

    Resource[] resources = set.toArray(new Resource[set.size()]);
    assertSame(resource1, resources[0]);
    assertSame(resource2, resources[1]);
  }

  @Test
  void testCompareSpecialCases() {
    assertEquals(0, underTest.compare(resource1, resource1));
    assertEquals(1, underTest.compare(null, resource1));
    assertEquals(-1, underTest.compare(resource1, null));
  }

}
