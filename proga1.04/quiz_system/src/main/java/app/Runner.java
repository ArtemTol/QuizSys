package app;

import ui.Login;
import util.LoggerUtil;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Runner {
    private static final Logger logger = LoggerUtil.getLogger(Runner.class);
    public static void main(String args[]) throws SQLException {

        logger.info("Запуск программы...");
        try {
            Login login = new Login();
            login.loginView();
        } catch (Exception e) {
            logger.severe("Ошибка при запуске программы: " + e.getMessage());
        }

    }
}