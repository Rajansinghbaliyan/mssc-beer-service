package guru.springframework.msscbeerservice.services.inventory.services;

import junit.framework.TestCase;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class BeerInventoryRestTemplateTest extends TestCase {

    @Autowired
    private BeerInventoryRestTemplate beerInventoryRestTemplate;

    public void testGetOnHandQuantityByBeerId() {
        beerInventoryRestTemplate.getOnHandQuantityByBeerId(UUID.randomUUID());
    }
}