package by.epam.filmstore.controller;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class CommandHelper {

    private static final CommandHelper instance = new CommandHelper();

    private Map<CommandName, Command> commands = new HashMap<>();

    private CommandHelper(){

        commands.put(CommandName.USER_AUTHORIZATION, new LoginationCommand());
        commands.put(CommandName.SAVE_NEW_USER, new SaveNewUserCommand());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(CommandName.GET_FILMS_BY_YEAR, new GetFilmsByYearCommand());
        commands.put(CommandName.LOAD_MAIN_PAGE, new LoadMainPageCommand());
        commands.put(CommandName.GET_FILM_BY_ID, new GetFilmByIdCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.GET_USER, new GetUserByIdCommand());
        commands.put(CommandName.GET_ORDERS_OF_USER, new GetOrdersOfUserCommand());
        commands.put(CommandName.GET_COMMENTS_OF_USER, new GetCommentsOfUserCommand());
        commands.put(CommandName.GET_FAVORITE_GENRES, new GetFavoriteGenresCommand());
        commands.put(CommandName.SAVE_FAVORITE_GENRES, new SaveFavoriteGenresCommand());
        commands.put(CommandName.GET_DISCOUNT, new GetDiscountCommand());

        commands.put(CommandName.ADMIN_GET_COMMENTS, new AdminGetCommentsCommand());
        commands.put(CommandName.ADMIN_GET_COUNTRIES, new AdminGetCountriesCommand());
        commands.put(CommandName.ADMIN_GET_DISCOUNTS, new AdminGetDiscountsCommand());
        commands.put(CommandName.ADMIN_GET_FILM_MAKERS, new AdminGetFilmMakersCommand());
        commands.put(CommandName.ADMIN_GET_FILMS, new AdminGetFilmsCommand());
        commands.put(CommandName.ADMIN_GET_GENRES, new AdminGetGenresCommand());
        commands.put(CommandName.ADMIN_GET_USERS, new AdminGetUsersCommand());

        commands.put(CommandName.ADMIN_DELETE_COMMENT, new AdminDeleteCommentCommand());
        commands.put(CommandName.ADMIN_DELETE_COUNTRY, new AdminDeleteCountryCommand());
        commands.put(CommandName.ADMIN_DELETE_DISCOUNT, new AdminDeleteDiscountCommand());
        commands.put(CommandName.ADMIN_DELETE_FILM_MAKER, new AdminDeleteFilmMakerCommand());
        commands.put(CommandName.ADMIN_DELETE_FILM, new AdminDeleteFilmCommand());
        commands.put(CommandName.ADMIN_DELETE_GENRE, new AdminDeleteGenreCommand());
        commands.put(CommandName.ADMIN_DELETE_USER, new AdminDeleteUserCommand());

        commands.put(CommandName.ADMIN_ADD_COUNTRY, new AdminAddCountryCommand());
        commands.put(CommandName.ADMIN_ADD_DISCOUNT, new AdminAddDiscountCommand());
        commands.put(CommandName.ADMIN_ADD_FILM_MAKER, new AdminAddFilmMakerCommand());
        commands.put(CommandName.ADMIN_ADD_FILM, new AdminAddFilmCommand());
        commands.put(CommandName.ADMIN_ADD_PAGE_FILM, new AdminAddPageFilmCommand());
        commands.put(CommandName.ADMIN_ADD_GENRE, new AdminAddGenreCommand());

        commands.put(CommandName.ADMIN_UPDATE_COMMENT, new AdminUpdateCommentCommand());
        commands.put(CommandName.ADMIN_UPDATE_COUNTRY, new AdminUpdateCountryCommand());
        commands.put(CommandName.ADMIN_UPDATE_DISCOUNT, new AdminUpdateDiscountCommand());
        commands.put(CommandName.ADMIN_UPDATE_FILM_MAKER, new AdminUpdateFilmMakerCommand());
        commands.put(CommandName.ADMIN_UPDATE_FILM, new AdminUpdateFilmCommand());
        commands.put(CommandName.ADMIN_UPDATE_GENRE, new AdminUpdateGenreCommand());
        commands.put(CommandName.ADMIN_UPDATE_USER, new AdminUpdateUserCommand());




    }

    public Command getCommand(String name) {
        name = name.replace('-', '_');
        CommandName commandName = CommandName.valueOf(name.toUpperCase());

        return commands.get(commandName);
    }

    public CommandName getCommandName(String name) {
        name = name.replace('-', '_');
        return CommandName.valueOf(name.toUpperCase());
    }

    public static CommandHelper getInstance() {
        return instance;
    }
}
