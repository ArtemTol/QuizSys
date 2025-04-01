/*
package util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {

    private static final String LOG_FILE = "application.log"; // Имя файла для логов

    static {
        try {
            // Создаём обработчик для записи логов в файл
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);

            // Добавляем обработчик ко всем логгерам
            Logger rootLogger = Logger.getLogger("");
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println("Ошибка при настройке логгирования: " + e.getMessage());
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
}
*/
package util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {

    private static final String LOG_FILE = "application.log"; // Имя файла для логов
    private static boolean isFileHandlerAdded = false; // Флаг для проверки обработчика

    static {
        try {
            // Проверяем, добавлен ли уже обработчик
            if (!isFileHandlerAdded) {
                // Создаём обработчик для записи логов в файл
                FileHandler fileHandler = new FileHandler(LOG_FILE, true);
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.ALL);

                // Добавляем обработчик ко всем логгерам
                Logger rootLogger = Logger.getLogger("");
                rootLogger.addHandler(fileHandler);
                rootLogger.setLevel(Level.INFO);

                // Устанавливаем флаг, чтобы избежать повторного добавления
                isFileHandlerAdded = true;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при настройке логгирования: " + e.getMessage());
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
}

