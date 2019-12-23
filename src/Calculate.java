import java.awt.event.*;
import javax.swing.*;


public class Calculate extends JFrame{ //Наследуя от JFrame мы получаем всю функциональность окна
	private static final long serialVersionUID = 1L;
	
	//Проверка на присутствие запятых в полях ввода
	boolean comma(int id) {//Цикл в цикле
		//Происходит полный перебор символов
		for (int i = 0; i < len-1; i++) {
			String text = textbox_collection[i].getText();
			for (char c: text.toCharArray()) {
				if (c == '.') {
					return true;
				}
			}
			
		}
		return false;
	}
	//Ограничение ввода
	KeyListener getInputListener(int id) {//id - номер текстового поля (для ограничения)
		return new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				
				int keyc = e.getKeyCode();//номер кнопки
				String text = textbox_collection[id].getText();//Получение текста из поля
				if (keyc == KeyEvent.VK_BACK_SPACE) {//BackSpace
					if (text.length()==1) {
						textbox_collection[id].setEditable(false);//запрещается ввод символов в определённое поле ввода
						textbox_collection[id].setText("0");//"Закидывает" ноль
					}
				}
				else {//Другие кнопки
					char key = e.getKeyChar();
					if (Character.isDigit(key)) {//проверка на цифры
						if (text.compareTo("0") == 0) {
							textbox_collection[id].setText("");
						}
					}
					else {//Не цифры
						if ((key == ',' || key == '.') && !comma(id)) {//Запятая или точка
							textbox_collection[id].setEditable(false);
							if (text.compareTo("0") == 0 || text.isEmpty()) {
								textbox_collection[id].setText("0.");
							}
							else {
								text+='.';
								textbox_collection[id].setText(text);
							}
						}
						else {//Остальные кнопки
							textbox_collection[id].setEditable(false);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e) {
				textbox_collection[id].setEditable(true);
			}
		};
	}
	String[] textLabel1 = {"Вода","Электричество",""};
	String[] textLabel2 = {"Холодная","Горячая","Ночь","День","Газ","Площадь квартиры"};
	JTextField[] textbox_collection = {//Текстовые поля
			new JTextField("0"),//холодная вода
			new JTextField("0"),//горячая
			new JTextField("0"),//электричество день
			new JTextField("0"),//ночь
			new JTextField("0"),//газ
			new JTextField("0"),//пллощадь
			new JTextField("0"),//люди
			new JTextField("0"),//Result
	};
	int len = textbox_collection.length;//количество
	
	JButton clean = new JButton("Отчистить");//Кнопка для отчистки полей вводы и поля вывода
	JButton result = new JButton("Расчёт");//Кнопка расчёта 
	JPanel panel = new JPanel();//Панель
	//Расчёт (Бессмысленные вычисления)
	void get_result() {
		double[] data = new double[len];
		int counter = 0;
		int len2 = len - 1;
		for (int i = 0; i < len2; i++) {
			data[i] = Double.valueOf(textbox_collection[i].getText());
			if (data[i] == 0) {
				counter++;
			}
		}
		if (counter == len2) {
			textbox_collection[len2].setText("0");
			return;
		}//Умножение значения на записанное в поле ввода
		data[0] *= 30.5;//Холодная вода
		data[1] *= 50.3;//Горячая вода
		data[2] *= 10.9;//Электричесво днём
		data[3] *= 4.1;//Электричесво ночью
		data[4] *= 2.7;//Газ
		data[5] *= 10;//Площадь дома/квартиры
		
		double result =  0.0;
		for (int i = 0; i < len2; i++) {
			result += data[i];
		}
		result += ((data[6]*70)-data[6]);//Число прописанныз в квартире
		result += (220);//Независимо какие значения буду в полях ввода к ним прибавляется 220,
												   // 100 за Кап.ремонт и 120 за уборку мусора, 
		textbox_collection[len2].setText(String.format("%.2f", result));//Текстовая строка, куда вывыдится результат
	}
	
	int dx = 120;//Ширина объектов
	int dy = 30;//Высота объектов
	
	int x = 20;//Левая граница интерфейса
	int y = 30;//Верхняя граница интерфейса
	
	Calculate(){
		super("Калькулятор");//Загаловок 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setLayout(null);
		
		//строительство интерфейса
		for (int i = 0; i < 3; i++) {
			JLabel label = new JLabel(textLabel1[i]);
			label.setSize(100, 15);//Ширина и высота объекта
			label.setLocation(x, y);//Граници объекта
			panel.add(label);//Добавляем на панель (импорт в интрерфейс)
			y += 20;//Увеличиваем на 20, дабы избежать создание следующего объекта на предидущем
			
			for (int j = 0; j < 2; j++) {
				int id = 2*i+j;
				JLabel label1 = new JLabel(textLabel2[id]);
				label1.setSize(dx, 15);//Ширина и высота объекта
				label1.setLocation(x, y);//Граници объекта
				panel.add(label1);
				
				//Задаются параметры объекта
				textbox_collection[id].setSize(dx, dy);//Ширина и высота объекта
				textbox_collection[id].setLocation(x, y + 20);//Граници объекта
				textbox_collection[id].setHorizontalAlignment(JTextField.RIGHT);//Задаем ориентацию действий от правого края объекта
				textbox_collection[id].addKeyListener(getInputListener(id));//Добавляем свойство чтения c клавиатуры
				panel.add(textbox_collection[id]);//Добавлеие объекта на панель (импорт в интрерфейс)
				
				JLabel label2 = new JLabel(".");
				label2.setSize(100, 15);//Ширина и высота объекта
				label2.setLocation(x+2*dx+8, y+10);//Граници объекта
				panel.add(label2);//Добавлеие объекта на панель (импорт в интрерфейс)
				
				x += dx+10;
			}
			x = 20;
			y += 80;//Увеличиваем значение, дабы избежать создание следующего объекта на предидущем
		}
		
		JLabel label = new JLabel("Количество прописанных человек");//Текстовое поле
		label.setSize(250, 15);//Ширина и высота объекта
		label.setLocation(x, y);//Граници объекта
		panel.add(label);//Добавляем объект на панель (импорт в интрерфейс)
		
		//Задаются параметры объекта
		textbox_collection[6].setSize(dx, dy);//Ширина и высота объекта
		textbox_collection[6].setLocation(x, y + 20);//Граници объекта
		textbox_collection[6].setHorizontalAlignment(JTextField.RIGHT);//Задаем ориентацию действий от правого края объекта
		textbox_collection[6].addKeyListener(getInputListener(6));//Добавляем свойство чтения c клавиатуры
		panel.add(textbox_collection[6]);//Добавляем объект на панель (импорт в интрерфейс)
		
		x = 20;
		y += 80;//Увеличиваем значение, дабы избежать создание следующего объекта на предидущем
		
		JLabel label1 = new JLabel("Результат");
		label1.setSize(dx, 15);
		label1.setLocation(x, y);
		panel.add(label1); 
		y += 15;//Увеличиваем значение, дабы избежать создание следующего объекта на предидущем
		
		//Задаются параметры объекта
		textbox_collection[7].setEditable(false);//Запрет на воод в поле
		textbox_collection[7].setSize(2*dx, dy);//Ширина и высота объекта
		textbox_collection[7].setLocation(x, y);//Граници объекта
		textbox_collection[7].setHorizontalAlignment(JTextField.RIGHT);//Задаем ориентацию действий от правого края объекта
		panel.add(textbox_collection[7]);//Добавляем объект на панель (импорт в интрерфейс)
		
		JLabel label2 = new JLabel("руб.");
		label2.setSize(100, 15);
		label2.setLocation(x+2*dx+8, y+10);
		panel.add(label2);
		
		y += 40;//Увеличиваем значение, дабы избежать создание следующего объекта на предидущем
		
		clean.setSize(dx,dy);
		clean.setLocation(x,y);
		clean.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					for (int i = 0; i < len; i++) {
						textbox_collection[i].setText("0");
					}
				}
			}
		);
		x += dx;
		panel.add(clean);
		//Расположение и размеры поля в которое выводятся результаты
		result.setSize(dx,dy);
		result.setLocation(x,y);
		result.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					get_result();
				}
			}
		);
		panel.add(result);
			
		setContentPane(panel);//Панелька
		setSize(350, 600);//Размеры панели
		setVisible(true);
	}
	public static void main(String[] args) {
		new Calculate();
	}
}
