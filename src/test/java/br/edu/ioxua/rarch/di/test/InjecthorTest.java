package br.edu.ioxua.rarch.di.test;

import br.edu.ioxua.rarch.di.Injecthor;
import br.edu.ioxua.rarch.di.annotation.Grouped;
import br.edu.ioxua.rarch.di.test.complex.ComplexTestService;
import br.edu.ioxua.rarch.di.test.complex.ComplexTestServiceImpl;
import br.edu.ioxua.rarch.di.test.grouped.GroupedTestService;
import br.edu.ioxua.rarch.di.test.multiple.MultipleTestService;
import br.edu.ioxua.rarch.di.test.named.NamedTestService;
import br.edu.ioxua.rarch.di.test.simple.SimpleTestService;
import br.edu.ioxua.rarch.di.test.simple.SimpleTestServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

/**
 * The wiring is made EVERY time. This is far from ideal, but since we need an instance for the wiring to take place,
 * it is needed.
 */
public class InjecthorTest {

    @Inject
    private SimpleTestService simpleTestService;

    @Inject
    private ComplexTestService complexTestService;

    @Inject
    private Set<MultipleTestService> multipleTestServices;

    @Inject
    private Map<String, NamedTestService> namedTestServices;

    @Inject
    @Grouped(value = { "a" }, classes = String.class)
    private Map<String, Set<GroupedTestService>> groupedTestServices;

    @Before
    public void setUp() {
        Injecthor.autowire(this, "br.edu.ioxua.rarch.di.test");
    }

    // Simple
    @Test
    public void whenInjectingSimpleService_shouldBeNotNull() {
        Assert.assertNotNull(this.simpleTestService);
        Assert.assertTrue(this.simpleTestService instanceof SimpleTestServiceImpl);
    }

    // Complex
    @Test
    public void whenInjectingComplexService_shouldBeNotNull() {
        Assert.assertNotNull(this.complexTestService);
        Assert.assertTrue(this.complexTestService instanceof ComplexTestServiceImpl);
    }

    @Test
    public void whenInjectingComplexService_shouldHaveDependenciesMet() {
        Assert.assertNotNull(this.complexTestService.getSimpleTestService());
        Assert.assertTrue(this.complexTestService.getSimpleTestService() instanceof SimpleTestServiceImpl);
    }

    @Test
    public void whenInjectingComplexService_simpleServiceShouldBeSingleton() {
        Assert.assertSame(this.simpleTestService, this.complexTestService.getSimpleTestService());
    }

    // Multiple
    @Test
    public void whenInjectingMultipleServices_shouldExistTwoServices() {
        Assert.assertNotNull(this.multipleTestServices);
        Assert.assertEquals(2, this.multipleTestServices.size());
    }

    // Named
    @Test
    public void whenInjectingNamedServices_shouldExistTwoServices() {
        Assert.assertNotNull(this.namedTestServices);
        Assert.assertEquals(2, this.namedTestServices.size());
    }

    // Grouped
    @Test
    public void whenInjectingGroupedServices_shouldExistTwoGroups() {
        Assert.assertNotNull(this.groupedTestServices);
        Assert.assertEquals(2, this.groupedTestServices.keySet().size());
        Assert.assertEquals(2, this.groupedTestServices.get("a").size());
        Assert.assertEquals(2, this.groupedTestServices.get(String.class.getName()).size());
    }
}
