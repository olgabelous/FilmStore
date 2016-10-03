package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;
import by.epam.filmstore.domain.Country;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @author Olga Shahray
 */
public class GetFilteredFilmsCommand implements Command {

    private static final String PAGE = "page";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String FILM_LIST = "filmList";
    private static final String GENRE_LIST = "genreList";
    private static final String COUNTRY_LIST = "countryList";
    private static final String ORDER = "order";
    private static final String GENRES_FILTER = "genresFilter";
    private static final String COUNTRY_FILTER = "countryFilter";
    private static final String YEAR_FILTER = "yearFilter";
    private static final String GENRE = "genre";
    private static final String COUNTRY = "country";
    private static final String YEAR = "year";
    private static final String RATING = "rating";
    private static final String WARNING = "warning";
    private static final String WARNING_TEXT = "Sorry, we don't have such films. Please choose another params.";
    private static final String QUERY = "query";
    private static final int ERROR_STATUS = 404;

    private static final Logger LOG = LogManager.getLogger(GetFilteredFilmsCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, List<String>> filterParams = new HashMap<>();
        Map<String, String> genresFilterToSend = new HashMap<>();
        Map<String, String> countriesFilterToSend = new HashMap<>();
        List<String> yearFilterToSend = new ArrayList<>();

        String[] genres = request.getParameterValues(GENRE);
        String[] countries = request.getParameterValues(COUNTRY);
        String[] year = request.getParameterValues(YEAR);
        String rating = request.getParameter(RATING);
        String order = request.getParameter(ORDER);
        try {
            int page = 1;
            int recordsPerPage = 6;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            HttpSession session = request.getSession();
            List<Genre> genreList  = (List<Genre>) session.getAttribute(GENRE_LIST);
            List<Country> countryList = (List<Country>) session.getAttribute(COUNTRY_LIST);

            if (genres != null && genres.length != 0) {
                filterParams.put(GENRE, Arrays.asList(genres));
                for (Genre g : genreList) {
                    for (String s : genres) {
                        if (s.equals(String.valueOf(g.getId()))) {
                            genresFilterToSend.put(s, g.getGenreName());
                        }
                    }
                }
            }
            request.setAttribute(GENRES_FILTER, genresFilterToSend);
            if (countries != null && countries.length != 0) {
                filterParams.put(COUNTRY, Arrays.asList(countries));
                for (Country c : countryList) {
                    for (String s : countries) {
                        if (s.equals(String.valueOf(c.getId()))) {
                            countriesFilterToSend.put(s, c.getCountryName());
                        }
                    }
                }
            }
            request.setAttribute(COUNTRY_FILTER, countriesFilterToSend);

            if (year != null && year.length != 0) {
                if(year[0].equals("2016")){
                    yearFilterToSend = Arrays.asList(year);
                }
                else{
                    int yearFrom = Integer.parseInt(year[0]);
                    int yearTo = yearFrom + 10;
                    yearFilterToSend = Arrays.asList(new String[]{year[0], String.valueOf(yearTo)});
                }

                filterParams.put(YEAR, yearFilterToSend);
            }
            request.setAttribute(YEAR_FILTER, year);

            if (rating != null && !rating.isEmpty()) {
                filterParams.put(RATING, Collections.singletonList(rating));
            }

            IFilmService filmService = ServiceFactory.getInstance().getFilmService();

            PagingListDTO<Film> result = filmService.getFilteredFilms(filterParams, order, (page - 1) * recordsPerPage, recordsPerPage);

            int numOfRecords = result.getCount();
            List<Film> filteredFilmList = result.getObjectList();

            if (numOfRecords == 0) {
                request.setAttribute(WARNING, WARNING_TEXT);
            }

            int totalPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);
            String requestQueryString = request.getQueryString().split("&page")[0];

            request.setAttribute(QUERY, requestQueryString);
            request.setAttribute(FILM_LIST, filteredFilmList);
            request.setAttribute(TOTAL_PAGES, totalPages);
            request.setAttribute(CURRENT_PAGE, page);
            request.setAttribute(CURRENT_PAGE, page);

            request.getRequestDispatcher(PageName.COMMON_FILMS_PAGE).forward(request, response);

        } catch (ServiceValidationException e) {
            LOG.warn("Data is not valid", e);
            response.sendError(ERROR_STATUS);

        } catch (ServiceException e) {
            LOG.error("Exception is caught", e);
            response.sendError(ERROR_STATUS);
        }
    }
}
