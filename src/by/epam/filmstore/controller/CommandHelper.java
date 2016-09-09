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
        commands.put(CommandName.GET_FILMS_BY_YEAR, new GetFilmsByYearCommand());
        commands.put(CommandName.LOAD_MAIN_PAGE, new LoadMainPageCommand());
        commands.put(CommandName.GET_FILM_BY_ID, new GetFilmByIdCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.GET_USER, new GetUserByIdCommand());

        commands.put(CommandName.USER_GET_ORDERS, new UserGetOrdersCommand());
        commands.put(CommandName.USER_GET_COMMENTS, new UserGetCommentsCommand());
        commands.put(CommandName.USER_GET_FAVORITE_GENRES, new GetFavoriteGenresCommand());
        commands.put(CommandName.USER_SAVE_FAVORITE_GENRES, new SaveFavoriteGenresCommand());
        commands.put(CommandName.USER_GET_DISCOUNT, new UserGetDiscountCommand());
        commands.put(CommandName.USER_ADD_TO_CART, new UserAddToCartCommand());
        commands.put(CommandName.USER_CART, new UserCartCommand());
        commands.put(CommandName.USER_DELETE_ORDER, new UserDeleteOrderCommand());
        commands.put(CommandName.USER_PAY_ORDER, new UserPayOrderCommand());
        commands.put(CommandName.USER_ADD_COMMENT, new UserAddCommentCommand());

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

        commands.put(CommandName.ADMIN_SAVE_COUNTRY, new AdminAddCountryCommand());
        commands.put(CommandName.ADMIN_SAVE_DISCOUNT, new AdminAddDiscountCommand());
        commands.put(CommandName.ADMIN_SAVE_FILM_MAKER, new AdminAddFilmMakerCommand());
        commands.put(CommandName.ADMIN_SAVE_GENRE, new AdminAddGenreCommand());
        commands.put(CommandName.ADMIN_SAVE_FILM, new AdminAddFilmCommand());
        commands.put(CommandName.ADMIN_ADD_PAGE_FILM, new AdminAddPageFilmCommand());

        commands.put(CommandName.ADMIN_UPDATE_COMMENT, new AdminUpdateCommentCommand());
        commands.put(CommandName.ADMIN_UPDATE_USER, new AdminUpdateUserCommand());

    }

    public Command getCommand(String name) {
        name = name.replace('-', '_');
        CommandName commandName = null;
        try {
           commandName  = CommandName.valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException e){

        }

        return commands.get(commandName);
    }

    public CommandName getCommandName(String name) {
        name = name.replace('-', '_');
        try{
            return CommandName.valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException e){
            return null;
        }

    }

    public static CommandHelper getInstance() {
        return instance;
    }
}
