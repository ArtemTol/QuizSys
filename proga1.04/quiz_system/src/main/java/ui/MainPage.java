package ui;

import service.SqlOperations;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPage {
	SqlOperations manage;
	JButton submit;
	String[] questionsArray, option1Array, option2Array, option3Array, option4Array;
	static DefaultTableModel model;
	String cd;

	int i=0, h=0;
	String[] queStr = new String[50];
	String[] op1Str = new String[50];
	String[] op2Str = new String[50];
	String[] op3Str = new String[50];
	String[] op4Str = new String[50];
	int id;

	public void mainPageView(int id) throws SQLException {
		this.id=id;
		questionsArray = new String[25];
		option1Array = new String[25];
		option2Array = new String[25];
		option3Array = new String[25];
		option4Array = new String[25];

		manage = new SqlOperations();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		JFrame frame = new JFrame("Управлять опросами");
		frame.setSize(800, 600);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);

		// Установим единый стиль шрифта
		Font commonFont = new Font("SansSerif", Font.PLAIN, 14);

		// Панель добавления опроса
		JPanel addPanel = new JPanel();
		addPanel.setBounds(250, 0, 550, 600);
		addPanel.setLayout(null);
		addPanel.setBackground(Color.WHITE);
		addPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

		JLabel start = new JLabel("Создать опрос");
		start.setBounds(0, 10, 550, 50);
		start.setHorizontalAlignment(JLabel.CENTER);
		start.setFont(new Font("SansSerif", Font.BOLD, 32));
		addPanel.add(start);

		JLabel question = new JLabel("Вопрос:");
		question.setFont(commonFont);
		question.setBounds(50, 100, 100, 20);
		addPanel.add(question);

		JTextField questionField = new JTextField();
		questionField.setFont(commonFont);
		questionField.setBounds(50, 125, 450, 30);
		addPanel.add(questionField);

		JLabel option1 = new JLabel("Пункт 1:");
		option1.setFont(commonFont);
		option1.setBounds(50, 165, 100, 20);
		addPanel.add(option1);

		JTextField option1Field = new JTextField();
		option1Field.setFont(commonFont);
		option1Field.setBounds(50, 190, 200, 30);
		addPanel.add(option1Field);

		JLabel option2 = new JLabel("Пункт 2:");
		option2.setFont(commonFont);
		option2.setBounds(50, 230, 100, 20);
		addPanel.add(option2);

		JTextField option2Field = new JTextField();
		option2Field.setFont(commonFont);
		option2Field.setBounds(50, 255, 200, 30);
		addPanel.add(option2Field);

		JLabel option3 = new JLabel("Пункт 3:");
		option3.setFont(commonFont);
		option3.setBounds(50, 295, 100, 20);
		addPanel.add(option3);

		JTextField option3Field = new JTextField();
		option3Field.setFont(commonFont);
		option3Field.setBounds(50, 320, 200, 30);
		addPanel.add(option3Field);

		JLabel option4 = new JLabel("Пункт 4:");
		option4.setFont(commonFont);
		option4.setBounds(50, 360, 100, 20);
		addPanel.add(option4);

		JTextField option4Field = new JTextField();
		option4Field.setFont(commonFont);
		option4Field.setBounds(50, 385, 200, 30);
		addPanel.add(option4Field);

		JButton next = new JButton("Добавить опрос");
		next.setFont(commonFont);
		next.setBounds(50, 440, 450, 35);
		addPanel.add(next);

		submit = new JButton("Принять");
		submit.setFont(commonFont);
		submit.setBounds(50, 490, 200, 50);
		submit.setEnabled(false);
		addPanel.add(submit);

		JButton cancel = new JButton("Отменить");
		cancel.setFont(commonFont);
		cancel.setBounds(300, 490, 200, 50);
		addPanel.add(cancel);

		frame.add(addPanel);

		// Панель просмотра опросов
		JPanel viewPanel = new JPanel();
		viewPanel.setBounds(250, 0, 550, 600);
		viewPanel.setLayout(null);
		viewPanel.setBackground(Color.WHITE);
		viewPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

		JLabel searchLabel = new JLabel("Искать:");
		searchLabel.setFont(commonFont);
		searchLabel.setBounds(100, 20, 100, 50);
		viewPanel.add(searchLabel);

		JTextField search = new JTextField();
		search.setFont(commonFont);
		search.setBounds(160, 30, 290, 30);
		viewPanel.add(search);

		JTable table=new JTable(){
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		model = (DefaultTableModel)table.getModel();
		table.setFont(commonFont);
		table.setRowHeight(25);
		model.addColumn("Ваши опросы");
		tableupdate("");
		JScrollPane scPane=new JScrollPane(table);
		scPane.setBounds(100, 70, 350, 225);
		viewPanel.add(scPane);

		JLabel quesView = new JLabel();
		quesView.setFont(commonFont);
		quesView.setBounds(50, 340, 450, 30);
		viewPanel.add(quesView);

		JLabel op1View = new JLabel();
		op1View.setFont(commonFont);
		op1View.setBounds(70, 380, 450, 30);
		viewPanel.add(op1View);

		JLabel op2View = new JLabel();
		op2View.setFont(commonFont);
		op2View.setBounds(70, 420, 450, 30);
		viewPanel.add(op2View);

		JLabel op3View = new JLabel();
		op3View.setFont(commonFont);
		op3View.setBounds(70, 460, 450, 30);
		viewPanel.add(op3View);

		JLabel op4View = new JLabel();
		op4View.setFont(commonFont);
		op4View.setBounds(70, 500, 450, 30);
		viewPanel.add(op4View);

		JLabel op1Select = new JLabel();
		op1Select.setFont(commonFont);
		op1Select.setForeground(Color.BLUE);
		op1Select.setBounds(100, 400, 150, 30);
		viewPanel.add(op1Select);

		JLabel op2Select = new JLabel();
		op2Select.setFont(commonFont);
		op2Select.setForeground(Color.BLUE);
		op2Select.setBounds(100, 440, 150, 30);
		viewPanel.add(op2Select);

		JLabel op3Select = new JLabel();
		op3Select.setFont(commonFont);
		op3Select.setForeground(Color.BLUE);
		op3Select.setBounds(100, 480, 150, 30);
		viewPanel.add(op3Select);

		JLabel op4Select = new JLabel();
		op4Select.setFont(commonFont);
		op4Select.setForeground(Color.BLUE);
		op4Select.setBounds(100, 520, 150, 30);
		viewPanel.add(op4Select);

		JButton delete = new JButton("Удалить");
		delete.setFont(commonFont);
		delete.setBounds(210, 300, 130, 50);
		delete.setEnabled(false);
		viewPanel.add(delete);

		JButton viewPrev = new JButton("Предыдущий");
		viewPrev.setFont(commonFont);
		viewPrev.setBounds(100, 300, 100, 50);
		viewPrev.setEnabled(false);
		viewPanel.add(viewPrev);

		JButton viewNext = new JButton("Далее");
		viewNext.setFont(commonFont);
		viewNext.setBounds(350, 300, 100, 50);
		viewNext.setEnabled(false);
		viewPanel.add(viewNext);

		frame.add(viewPanel);

		// Левая панель опций
		JPanel optionPanel = new JPanel();
		optionPanel.setBounds(0, 0, 250, 600);
		optionPanel.setBackground(Color.decode("#F0F0F0"));
		optionPanel.setLayout(null);

		JLabel titleLabel = new JLabel("Меню опросов");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 30, 250, 40);
		optionPanel.add(titleLabel);

		JButton addSurvey = new JButton("Добавить опрос");
		addSurvey.setFont(commonFont);
		addSurvey.setBounds(50, 113, 150, 50);
		optionPanel.add(addSurvey);

		JButton viewSurvey = new JButton("Показать опрос");
		viewSurvey.setFont(commonFont);
		viewSurvey.setBounds(50, 276, 150, 50);
		optionPanel.add(viewSurvey);

		JButton logout = new JButton("Выйти");
		logout.setFont(commonFont);
		logout.setBounds(50, 440, 150, 50);
		optionPanel.add(logout);

		frame.add(optionPanel);

		// Действия на кнопках
		addSurvey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewPanel.setVisible(false);
				addPanel.setVisible(true);
			}
		});

		viewSurvey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableupdate(search.getText());
				viewPanel.setVisible(true);
				addPanel.setVisible(false);
			}
		});

		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				try {
					login.loginView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});

		next.addActionListener(new ActionListener( ) {
			@Override
			public void actionPerformed(ActionEvent e) {
				String qn = questionField.getText();
				String op1 = option1Field.getText();
				String op2 = option2Field.getText();;
				String op3 = option3Field.getText();;
				String op4 = option4Field.getText();;
				if(qn.equals("") || op1.equals("") || op2.equals("") || op3.equals("") || op4.equals("")) {
					JOptionPane.showMessageDialog(frame, "Please Fill All Options.", "Warning Message", JOptionPane.WARNING_MESSAGE);
				} else {
					questionField.setText("");
					option1Field.setText("");
					option2Field.setText("");
					option3Field.setText("");
					option4Field.setText("");
					queStr[i] = qn;
					op1Str[i] = op1;
					op2Str[i] = op2;
					op3Str[i] = op3;
					op4Str[i] = op4;
					i++;
					submit.setEnabled(true);
				}
			}
		});

		submit.addActionListener(new ActionListener( ) {
			@Override
			public void actionPerformed(ActionEvent e) {
				String code = stringGenerator();
				String qn = questionField.getText();
				String op1 = option1Field.getText();
				String op2 = option2Field.getText();;
				String op3 = option3Field.getText();;
				String op4 = option4Field.getText();;
				if(!(qn.equals("") && op1.equals("") && op2.equals("") && op3.equals("") && op4.equals(""))) {
					JOptionPane.showMessageDialog(frame, "Last details are not added. If not required, Please clear the fields.", "Warning Message", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(i==0) {
						JOptionPane.showMessageDialog(frame, "No Questions Added", "Warning Message", JOptionPane.WARNING_MESSAGE);
					} else {
						try {
							manage.userQuestionAdd(id, code);
							for(int j=0; j<i; j++) {
								manage.newQuestion(code, queStr[j], op1Str[j], op2Str[j], op3Str[j], op4Str[j]);
							}
							JOptionPane.showMessageDialog(frame, "Quiz Added. Quiz Code : " + code, "Congratulations", JOptionPane.PLAIN_MESSAGE);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				submit.setEnabled(false);
			}
		});

		cancel.addActionListener(new ActionListener( ) {
			@Override
			public void actionPerformed(ActionEvent e) {
				questionField.setText("");
				option1Field.setText("");
				option2Field.setText("");
				option3Field.setText("");
				option4Field.setText("");
				i=0;
			}
		});

		search.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				tableupdate(search.getText());
			}
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});

		viewPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(h>=1) {
					h--;
					quesView.setText(questionsArray[h]);
					op1View.setText(option1Array[h]);
					op2View.setText(option2Array[h]);
					op3View.setText(option3Array[h]);
					op4View.setText(option4Array[h]);
					try {
						op1Select.setText(String.valueOf(manage.getCount(cd, h, 1)));
						op2Select.setText(String.valueOf(manage.getCount(cd, h, 2)));
						op3Select.setText(String.valueOf(manage.getCount(cd, h, 3)));
						op4Select.setText(String.valueOf(manage.getCount(cd, h, 4)));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manage.removeSurvey(cd);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableupdate(search.getText());
				delete.setEnabled(false);
				viewNext.setEnabled(false);
				viewPrev.setEnabled(false);
				quesView.setText("");
				op1View.setText("");
				op2View.setText("");
				op3View.setText("");
				op4View.setText("");
				op1Select.setText("");
				op2Select.setText("");
				op3Select.setText("");
				op4Select.setText("");
			}
		});

		viewNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(questionsArray[h+1]!=null) {
					h++;
					quesView.setText(questionsArray[h]);
					op1View.setText(option1Array[h]);
					op2View.setText(option2Array[h]);
					op3View.setText(option3Array[h]);
					op4View.setText(option4Array[h]);
					try {
						op1Select.setText(String.valueOf(manage.getCount(cd, h, 1)));
						op2Select.setText(String.valueOf(manage.getCount(cd, h, 2)));
						op3Select.setText(String.valueOf(manage.getCount(cd, h, 3)));
						op4Select.setText(String.valueOf(manage.getCount(cd, h, 4)));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				h=0;
				delete.setEnabled(true);
				viewNext.setEnabled(true);
				viewPrev.setEnabled(true);
				int row = table.getSelectedRow();
				cd = String.valueOf(model.getValueAt(row, 0));
				try {
					ResultSet rst1 = manage.getQuestions(cd);
					questionsArray = new String[25];
					option1Array = new String[25];
					option2Array = new String[25];
					option3Array = new String[25];
					option4Array = new String[25];
					int x=0;
					while(rst1.next()) {
						questionsArray[x] = rst1.getString("question");
						option1Array[x] = rst1.getString("option1");
						option2Array[x] = rst1.getString("option2");
						option3Array[x] = rst1.getString("option3");
						option4Array[x] = rst1.getString("option4");
						x++;
					}
					op1Select.setText(String.valueOf(manage.getCount(cd, h, 1)));
					op2Select.setText(String.valueOf(manage.getCount(cd, h, 2)));
					op3Select.setText(String.valueOf(manage.getCount(cd, h, 3)));
					op4Select.setText(String.valueOf(manage.getCount(cd, h, 4)));
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				quesView.setText(questionsArray[h]);
				op1View.setText(option1Array[h]);
				op2View.setText(option2Array[h]);
				op3View.setText(option3Array[h]);
				op4View.setText(option4Array[h]);
			}
		});

		viewPanel.setVisible(false);
		frame.setVisible(true);
	}

	public String stringGenerator() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String sb = "";
		for (int i = 0; i < 5; i++) {
			int index = (int)(AlphaNumericString.length() * Math.random());
			sb += (AlphaNumericString.charAt(index));
		}
		return sb;
	}

	public void tableupdate(String str) {
		try {
			SqlOperations man = new SqlOperations();
			ResultSet res = man.surveys(id, str);
			int rowCount = model.getRowCount();
			int i;
			for (i = rowCount - 1; i >= 0; i--)
				model.removeRow(i);
			for(i=0; res.next(); i++) {
				model.addRow(new Object[0]);
				model.setValueAt(res.getString("quizcode"), i, 0);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
