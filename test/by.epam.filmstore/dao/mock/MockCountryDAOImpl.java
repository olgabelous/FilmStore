package by.epam.filmstore.dao.mock;

import by.epam.filmstore.dao.ICountryDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Country;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Olga Shahray
 */
public class MockCountryDAOImpl implements ICountryDAO{
    private static final Logger LOG = LogManager.getLogger(MockCountryDAOImpl.class);

    private Map<Integer, Country> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void save(Country country) throws DAOException {
        LOG.info("save country");
        Objects.requireNonNull(country);
        if (country.getId() == 0) {
            country.setId(counter.incrementAndGet());
            repository.put(country.getId(), country);
        }
    }

    @Override
    public void update(Country country) throws DAOException {
        LOG.info("update country");
        Objects.requireNonNull(country);
        if (country.getId() != 0) {
            repository.put(country.getId(), country);
        }
    }

    @Override
    public boolean delete(int countryId) throws DAOException {
        LOG.info("delete country " + countryId);
        return repository.remove(countryId) != null;
    }

    @Override
    public List<Country> getAll() throws DAOException {
        LOG.info("get all country");
        return repository.values().stream().collect(Collectors.toList());
    }

    public Country get(int id) {
        return repository.get(id);
    }
}
