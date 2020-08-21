package engine.util.user;

public final class UserMapping {
    private static final String BASIC_PATH = "/api/auth";

    public static final String REGISTER_USER = BASIC_PATH + "/register";
    public static final String LOGIN_USER = BASIC_PATH + "/login";
    public static final String REFRESH_TOKEN = BASIC_PATH + "/token/refresh";
    public static final String LOGOUT_USER = BASIC_PATH + "/logout";

    public static final String LOGOUT_INFO = "Refresh token deleted successfully";
}
