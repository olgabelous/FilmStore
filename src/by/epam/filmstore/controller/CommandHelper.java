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
        commands.put(CommandName.SAVE_NEW_FILM, new SaveNewFilmCommand());
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


    }

    public Command getCommand(String name) {
        name = name.replace('-', '_');
        CommandName commandName = CommandName.valueOf(name.toUpperCase());

        return commands.get(commandName);
    }

    public static CommandHelper getInstance() {
        return instance;
    }
}
