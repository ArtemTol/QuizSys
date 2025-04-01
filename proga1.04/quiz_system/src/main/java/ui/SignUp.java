package ui;

import service.SqlOperations;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUp {
	public void signUpView() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		JFrame frame = new JFrame("Создание нового пользователя");
		frame.setSize(450, 450);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.WHITE);

		// Общий шрифт
		Font commonFont = new Font("SansSerif", Font.PLAIN, 14);

		JLabel heading = new JLabel("Создать пользователя");
		heading.setBounds(0, 30, 450, 50);
		heading.setHorizontalAlignment(JLabel.CENTER);
		heading.setFont(new Font("SansSerif", Font.BOLD, 24));
		frame.add(heading);

		JLabel fName = new JLabel("Имя:");
		fName.setFont(commonFont);
		fName.setBounds(50, 100, 150, 30);
		frame.add(fName);

		JTextField fNameField = new JTextField();
		fNameField.setFont(commonFont);
		fNameField.setBounds(50, 130, 350, 30);
		fNameField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		frame.add(fNameField);

		JLabel uName = new JLabel("Имя пользователя:");
		uName.setFont(commonFont);
		uName.setBounds(50, 170, 150, 30);
		frame.add(uName);

		JTextField uNameField = new JTextField();
		uNameField.setFont(commonFont);
		uNameField.setBounds(50, 200, 350, 30);
		uNameField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		frame.add(uNameField);

		JLabel uPass = new JLabel("Пароль:");
		uPass.setFont(commonFont);
		uPass.setBounds(50, 240, 150, 30);
		frame.add(uPass);

		JPasswordField uPassField = new JPasswordField();
		uPassField.setFont(commonFont);
		uPassField.setBounds(50, 270, 150, 30);
		uPassField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		frame.add(uPassField);

		JLabel uPass2 = new JLabel("Подтвердите пароль:");
		uPass2.setFont(commonFont);
		uPass2.setBounds(250, 240, 150, 30);
		frame.add(uPass2);

		JPasswordField uPassField2 = new JPasswordField();
		uPassField2.setFont(commonFont);
		uPassField2.setBounds(250, 270, 150, 30);
		uPassField2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		frame.add(uPassField2);

		JButton submit = new JButton("ГОТОВО");
		submit.setFont(commonFont);
		submit.setBackground(Color.decode("#32C7FB"));
		submit.setForeground(Color.WHITE);
		submit.setFocusPainted(false);
		submit.setBounds(175, 330, 100, 40);
		frame.add(submit);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fname = fNameField.getText().trim();
				String uname = uNameField.getText().trim();
				String pass1 = new String(uPassField.getPassword()).trim();
				String pass2 = new String(uPassField2.getPassword()).trim();

				if (fname.isEmpty() || uname.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Пожалуйста, введите все данные.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
				} else {
					if (pass1.equals(pass2)) {
						try {
							SqlOperations manage = new SqlOperations();
							manage.newUser(fname, uname, pass1);
							fNameField.setText("");
							uNameField.setText("");
							uPassField.setText("");
							uPassField2.setText("");
							JOptionPane.showMessageDialog(frame, "Пользователь успешно создан.", "Успех", JOptionPane.PLAIN_MESSAGE);
							frame.dispose();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(frame, "Попробуйте снова.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(frame, "Пароли не совпадают.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		frame.setVisible(true);
	}
}
