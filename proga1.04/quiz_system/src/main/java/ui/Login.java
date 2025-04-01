package ui;

import service.SqlOperations;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login {
	int id;

	public void loginView() throws SQLException {
		// Установка системного стиля оформления
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SqlOperations manage = new SqlOperations();

		JFrame frame = new JFrame("Система управления викторинами");
		frame.setSize(700, 550);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLocationRelativeTo(null);

		// Единый шрифт для элементов интерфейса
		Font commonFont = new Font("SansSerif", Font.PLAIN, 14);

		JLabel heading = new JLabel("Система управления викторинами");
		heading.setBounds(0, 50, 700, 50);
		heading.setHorizontalAlignment(JLabel.CENTER);
		heading.setFont(new Font("SansSerif", Font.BOLD, 24));
		frame.add(heading);

		JLabel uname = new JLabel("Имя пользователя:");
		uname.setFont(commonFont);
		uname.setBounds(175, 130, 150, 30);
		frame.add(uname);

		JTextField name = new JTextField();
		name.setFont(commonFont);
		name.setBounds(175, 170, 350, 30);
		name.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		frame.add(name);

		JLabel upass = new JLabel("Пароль:");
		upass.setFont(commonFont);
		upass.setBounds(175, 210, 150, 30);
		frame.add(upass);

		JPasswordField pass = new JPasswordField();
		pass.setFont(commonFont);
		pass.setBounds(175, 250, 350, 30);
		pass.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		frame.add(pass);

		// Кнопка "Войти"
		JButton login = new JButton("Войти");
		login.setFont(commonFont);
		login.setBounds(225, 300, 100, 40);
		login.setBackground(Color.decode("#5cb85c"));
		login.setForeground(Color.BLACK);
		login.setFocusPainted(false);
		frame.add(login);
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = name.getText().trim();
				String password = new String(pass.getPassword()).trim();
				if (username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Пожалуйста, заполните все поля.", "Внимание", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						id = manage.authUser(username, password);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (id == -1) {
						JOptionPane.showMessageDialog(frame, "Пользователь не найден.", "Внимание", JOptionPane.WARNING_MESSAGE);
						JOptionPane.showMessageDialog(frame, "Да", "Внимание", JOptionPane.WARNING_MESSAGE);
					} else if (id == 0) {
						JOptionPane.showMessageDialog(frame, "Неверный пароль. Попробуйте снова.", "Внимание", JOptionPane.WARNING_MESSAGE);
					} else {
						MainPage mainPage = new MainPage();
						try {
							mainPage.mainPageView(id);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						frame.dispose();
					}
				}
			}
		});

		// Кнопка "Регистрация"
		JButton signUp = new JButton("Регистрация");
		signUp.setFont(commonFont);
		signUp.setBounds(375, 300, 150, 40);
		signUp.setBackground(Color.decode("#0275d8"));
		signUp.setForeground(Color.BLACK);
		signUp.setFocusPainted(false);
		frame.add(signUp);
		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SignUp signup = new SignUp();
				signup.signUpView();
			}
		});

		// Кнопка для гостей "Пройти викторину (Гость)"
		JButton attend = new JButton("Пройти опрос (Гость)");
		attend.setFont(commonFont);
		attend.setBounds(225, 350, 250, 40);
		attend.setBackground(Color.decode("#5bc0de"));
		attend.setForeground(Color.BLACK);
		attend.setFocusPainted(false);
		frame.add(attend);
		attend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String surveyCode = JOptionPane.showInputDialog(frame, "Введите уникальный код опроса:", "");
				if (surveyCode != null && !surveyCode.trim().isEmpty() && surveyCode.length() == 5) {
					try {
						if (manage.check(surveyCode)) {
							Guest guest = new Guest();
							guest.guestView(surveyCode);
						} else {
							JOptionPane.showMessageDialog(frame, "Неверный код опроса. Попробуйте снова.", "Внимание", JOptionPane.WARNING_MESSAGE);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} else if (surveyCode != null && surveyCode.trim().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Код опроса не может быть пустым.", "Внимание", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		frame.setVisible(true);
	}
}
