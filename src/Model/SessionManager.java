package Model;

public class SessionManager {
    private static SessionManager instance;
    private static User currentUser;
    private boolean isLoggedIn;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void logIn(User user) {
        this.currentUser = user;
        this.isLoggedIn = true;
    }

    public void logOut() {
        this.currentUser = null;
        this.isLoggedIn = false;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
