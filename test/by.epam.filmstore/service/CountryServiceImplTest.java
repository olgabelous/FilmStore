package by.epam.filmstore.service;

import by.epam.filmstore.domain.Country;
import by.epam.filmstore.service.exception.ServiceConnectionPoolManagerException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * @author Olga Shahray
 */
public class CountryServiceImplTest {
    private static final Logger LOG = LogManager.getLogger(CountryServiceImplTest.class);

    @BeforeClass
    public static void beforeTest() throws ServiceConnectionPoolManagerException {
        LOG.info("*******Before tests*******");
        IConnectionPoolManager poolManager = ServiceFactory.getInstance().getPoolManager();
        poolManager.init();
    }

    @AfterClass
    public static void afterTest() throws ServiceConnectionPoolManagerException {
        LOG.info("*******After tests*******");
        IConnectionPoolManager poolManager = ServiceFactory.getInstance().getPoolManager();
        poolManager.destroy();
    }

    @Test
    public void testSave() throws Exception {
        LOG.info("test save");
        ICountryService countryService = ServiceFactory.getInstance().getCountryService();
        countryService.save("Germany");
    }

    @Test
    public void testDelete() throws Exception {
        LOG.info("test delete");
        ICountryService countryService = ServiceFactory.getInstance().getCountryService();
        countryService.delete(1);
    }

    @Test
    public void testGetAll() throws Exception {
        LOG.info("test getAll");
        ICountryService countryService = ServiceFactory.getInstance().getCountryService();
        List<Country> list = countryService.getAll();
    }
}
