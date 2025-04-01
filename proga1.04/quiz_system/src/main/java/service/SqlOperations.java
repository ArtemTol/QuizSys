package service;


import util.LoggerUtil;

import java.io.InputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlOperations {

	private static final Logger logger = LoggerUtil.getLogger(SqlOperations.class);
	private Connection con;

	public SqlOperations() {
		try {
			logger.info("Загрузка файла настроек для базы данных.");
			InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
			Properties properties = new Properties();
			properties.load(input);

			String url = properties.getProperty("db.url");
			String username = properties.getProperty("db.username");
			String password = properties.getProperty("db.password");
			String driver = properties.getProperty("db.driver");

			logger.info("Параметры подключения: URL = " + url + ", User = " + username);

			logger.info("Подключение к базе данных...");
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);

			logger.info("Соединение с базой данных успешно установлено.");
			logger.info("Параметры подключения: URL = " + url + ", User = " + username);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Ошибка при чтении файла настроек", e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Ошибка загрузки драйвера", e);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Ошибка соединения с базой данных", e);
		}
	}

	public void newUser(String name, String uname, String pass) throws SQLException {
		logger.info("Добавление нового пользователя: " + uname);
		String sql = "INSERT INTO actors (fname, uname, pass) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, uname);
			pstmt.setString(3, pass);
			pstmt.executeUpdate();
			logger.info("Пользователь успешно добавлен: " + uname);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Ошибка при добавлении пользователя: " + uname, e);
			throw e;
		}
	}

	public int authUser(String uname, String pass) throws SQLException {
		logger.info("Авторизация пользователя: " + uname);
		String sql = "SELECT * FROM actors WHERE uname = ?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, uname);
			try (ResultSet rst = pstmt.executeQuery()) {
				if (!rst.next()) {
					logger.warning("Пользователь не найден: " + uname);
					return -1; // Пользователь не найден
				} else {
					boolean isAuthenticated = rst.getString("pass").equals(pass);
					if (isAuthenticated) {
						logger.info("Успешная авторизация пользователя: " + uname);
						return rst.getInt("id");
					} else {
						logger.warning("Неверный пароль для пользователя: " + uname);
						return 0;
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Ошибка при авторизации пользователя: " + uname, e);
			throw e;
		}
	}

	public void newQuestion(String code, String question, String op1, String op2, String op3, String op4) throws SQLException {
		logger.info("Добавление нового вопроса в викторину: " + code);
		String sql = "INSERT INTO questions (quizcode, question, option1, option2, option3, option4) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, code);
			pstmt.setString(2, question);
			pstmt.setString(3, op1);
			pstmt.setString(4, op2);
			pstmt.setString(5, op3);
			pstmt.setString(6, op4);
			pstmt.executeUpdate();
			logger.info("Вопрос успешно добавлен в викторину: " + code);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Ошибка при добавлении вопроса в викторину: " + code, e);
			throw e;
		}
	}

	public void userQuestionAdd(int id, String quizcode) throws SQLException {
		logger.info("Добавление пользователя к викторине: " + quizcode);
		String sql = "INSERT INTO userQuestions (id, quizcode, total) VALUES (?, ?, 0)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, quizcode);
			pstmt.executeUpdate();
			logger.info("Пользователь успешно добавлен к викторине: " + quizcode);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Ошибка при добавлении пользователя к викторине: " + quizcode, e);
			throw e;
		}
	}

	public void answerUpdt(String quizcode, int qno, int option) throws SQLException {
		String sql = "INSERT INTO quizquestions (quizcode, qno, opno) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, quizcode);
			pstmt.setInt(2, qno);
			pstmt.setInt(3, option);
			pstmt.executeUpdate();
		}
	}

	public ResultSet getQuestions(String quizcode) throws SQLException {
		String sql = "SELECT * FROM questions WHERE quizcode = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, quizcode);
		return pstmt.executeQuery();
	}

	public ResultSet surveys(int id, String search) throws SQLException {
		String sql = "SELECT * FROM userQuestions WHERE id = ? AND quizcode LIKE ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.setString(2, "%" + search + "%");
		return pstmt.executeQuery();
	}

	public void addTotal() throws SQLException {
		String sql = "UPDATE userQuestions SET total = total + 1";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.executeUpdate();
		}
	}

	public boolean check(String search) throws SQLException {
		String sql = "SELECT * FROM userQuestions WHERE quizcode = ?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, search);
			try (ResultSet rst = pstmt.executeQuery()) {
				return rst.next();
			}
		}
	}

	public void removeSurvey(String quizcode) throws SQLException {
		String deleteQuizQuestions = "DELETE FROM quizquestions WHERE quizcode = ?";
		String deleteUserQuestions = "DELETE FROM userQuestions WHERE quizcode = ?";
		String deleteQuestions = "DELETE FROM questions WHERE quizcode = ?";

		try (PreparedStatement pstmt1 = con.prepareStatement(deleteQuizQuestions);
			 PreparedStatement pstmt2 = con.prepareStatement(deleteUserQuestions);
			 PreparedStatement pstmt3 = con.prepareStatement(deleteQuestions)) {
			pstmt1.setString(1, quizcode);
			pstmt1.executeUpdate();

			pstmt2.setString(1, quizcode);
			pstmt2.executeUpdate();

			pstmt3.setString(1, quizcode);
			pstmt3.executeUpdate();
		}
	}

	public int getCount(String quizcode, int qno, int op) throws SQLException {
		String sql = "SELECT COUNT(opno) FROM quizquestions WHERE quizcode = ? AND qno = ? AND opno = ?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, quizcode);
			pstmt.setInt(2, qno + 1);
			pstmt.setInt(3, op);
			try (ResultSet rst = pstmt.executeQuery()) {
				return rst.next() ? rst.getInt(1) : 0;
			}
		}
	}

/*	public void closeConnection() {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
				logger.info("Соединение успешно закрыто.");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Не удалось закрыть соединение", e);
		}
	}*/
}
