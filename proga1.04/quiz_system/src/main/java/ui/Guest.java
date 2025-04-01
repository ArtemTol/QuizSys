package ui;

import service.SqlOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Guest {

	SqlOperations manage;
	int[] opt;
	int k;

	public void guestView(String surveyCode) throws SQLException {
		// Установка системного стиля оформления
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		manage = new SqlOperations();
		ResultSet rst = manage.getQuestions(surveyCode);
		opt = new int[50];

		Font commonFont = new Font("SansSerif", Font.PLAIN, 16);
		Font headingFont = new Font("SansSerif", Font.BOLD, 30);
		Font questionFont = new Font("SansSerif", Font.BOLD, 18);
		Font optionFont = new Font("SansSerif", Font.PLAIN, 16);

		JFrame frame = new JFrame("Участие в опросе");
		frame.setSize(800, 600);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.WHITE);

		JLabel start = new JLabel("УЧАСТИЕ В ОПРОСЕ");
		start.setBounds(0, 50, 800, 50);
		start.setHorizontalAlignment(JLabel.CENTER);
		start.setFont(headingFont);
		frame.add(start);

		JLabel ques = new JLabel("Вопрос:");
		ques.setBounds(80, 200, 700, 30);
		ques.setFont(questionFont);
		frame.add(ques);

		JRadioButton op1 = new JRadioButton();
		JRadioButton op2 = new JRadioButton();
		JRadioButton op3 = new JRadioButton();
		JRadioButton op4 = new JRadioButton();

		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(op1);
		bgroup.add(op2);
		bgroup.add(op3);
		bgroup.add(op4);

		op1.setBounds(100, 250, 600, 30);
		op2.setBounds(100, 300, 600, 30);
		op3.setBounds(100, 350, 600, 30);
		op4.setBounds(100, 400, 600, 30);

		op1.setFont(optionFont);
		op2.setFont(optionFont);
		op3.setFont(optionFont);
		op4.setFont(optionFont);

		if (rst.next()) {//внутри ГетКвештион........сделат
			ques.setText(rst.getString("question"));
			op1.setText(rst.getString("option1"));
			op2.setText(rst.getString("option2"));
			op3.setText(rst.getString("option3"));
			op4.setText(rst.getString("option4"));
		}

		frame.add(op1);
		frame.add(op2);
		frame.add(op3);
		frame.add(op4);
		k = 0;

		JButton nextButton = new JButton("Далее");
		nextButton.setFont(commonFont);
		nextButton.setBounds(100, 470, 600, 50);
		nextButton.setBackground(Color.decode("#5bc0de"));
		nextButton.setForeground(Color.WHITE);
		nextButton.setFocusPainted(false);
		frame.add(nextButton);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int x;
				if (op1.isSelected()) {
					x = 1;
				} else if (op2.isSelected()) {
					x = 2;
				} else if (op3.isSelected()) {
					x = 3;
				} else if (op4.isSelected()) {
					x = 4;
				} else
					x = 0;

				if (x != 0) {
					opt[k] = x;
					k++;
					try {
						if (rst.next()) {
							ques.setText(rst.getString("question"));
							op1.setText(rst.getString("option1"));
							op2.setText(rst.getString("option2"));
							op3.setText(rst.getString("option3"));
							op4.setText(rst.getString("option4"));
						} else {
							for (int j = 0; j < k; j++) {
								manage.answerUpdt(surveyCode, j + 1, opt[j]);
							}
							JOptionPane.showMessageDialog(frame, "Опрос завершен. Спасибо.", "Поздравляем", JOptionPane.PLAIN_MESSAGE);
							manage.addTotal();
							frame.dispose();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Выберите вариант ответа!", "Внимание", JOptionPane.WARNING_MESSAGE);
				}
				bgroup.clearSelection();
			}
		});

		frame.setVisible(true);
	}
}
