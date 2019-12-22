import java.awt.event.*;
import javax.swing.*;


public class Calculate extends JFrame{ //�������� �� JFrame �� �������� ��� ���������������� ����
	private static final long serialVersionUID = 1L;
	
	//�������� �� ����������� ������� � ����� �����
	boolean comma(int id) {//���� � �����
		//���������� ������ ������� ��������
		String text = textbox_collection[id].getText();
		for (char c: text.toCharArray()) {
			if (c == '.') {
				return true;
			}
		}	
		return false;
	}
	//����������� �����
	KeyListener getInputListener(int id) {//id - ����� ���������� ���� (��� �����������)
		return new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				
				int keyc = e.getKeyCode();//����� ������
				String text = textbox_collection[id].getText();//��������� ������ �� ����
				if (keyc == KeyEvent.VK_BACK_SPACE) {//BackSpace
					if (text.length()==1) {
						textbox_collection[id].setEditable(false);//����������� ���� �������� � ����������� ���� �����
						textbox_collection[id].setText("0");//"����������" ����
					}
				}
				else {//������ ������
					char key = e.getKeyChar();
					if (Character.isDigit(key)) {//�������� �� �����
						if (text.compareTo("0") == 0) {
							textbox_collection[id].setText("");
						}
					}
					else {//�� �����
						if ((key == ',' || key == '.') && !comma(id)) {//������� ��� �����
							textbox_collection[id].setEditable(false);
							if (text.compareTo("0") == 0 || text.isEmpty()) {
								textbox_collection[id].setText("0.");
							}
							else {
								text+='.';
								textbox_collection[id].setText(text);
							}
						}
						else {//��������� ������
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
	String[] textLabel1 = {"����","�������������",""};
	String[] textLabel2 = {"��������","�������","����","����","���","������� ��������"};
	JTextField[] textbox_collection = {//��������� ����
			new JTextField("0"),//�������� ����
			new JTextField("0"),//�������
			new JTextField("0"),//������������� ����
			new JTextField("0"),//����
			new JTextField("0"),//���
			new JTextField("0"),//��������
			new JTextField("0"),//����
			new JTextField("0"),//Result
	};
	int len = textbox_collection.length;//����������
	
	JButton clean = new JButton("���������");//������ ��� �������� ����� ����� � ���� ������
	JButton result = new JButton("������");//������ ������� 
	JPanel panel = new JPanel();//������
	//������ (������������� ����������)
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
		}//��������� �������� �� ���������� � ���� �����
		data[0] *= 30.5;//�������� ����
		data[1] *= 50.3;//������� ����
		data[2] *= 10.9;//������������ ���
		data[3] *= 4.1;//������������ �����
		data[4] *= 2.7;//���
		data[5] *= 10;//������� ����/��������
		
		double result =  0.0;
		for (int i = 0; i < len2; i++) {
			result += data[i];
		}
		result += ((data[6]*70)-data[6]);//����� ����������� � ��������
		result += (220);//���������� ����� �������� ���� � ����� ����� � ��� ������������ 220,
												   // 100 �� ���.������ � 120 �� ������ ������, 
		textbox_collection[len2].setText(String.format("%.2f", result));//��������� ������, ���� ��������� ���������
	}
	
	int dx = 120;//������ ��������
	int dy = 30;//������ ��������
	
	int x = 20;//����� ������� ����������
	int y = 30;//������� ������� ����������
	
	Calculate(){
		super("�����������");//��������� 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setLayout(null);
		
		//������������� ����������
		for (int i = 0; i < 3; i++) {
			JLabel label = new JLabel(textLabel1[i]);
			label.setSize(100, 15);//������ � ������ �������
			label.setLocation(x, y);//������� �������
			panel.add(label);//��������� �� ������ (������ � ����������)
			y += 20;//����������� �� 20, ���� �������� �������� ���������� ������� �� ����������
			
			for (int j = 0; j < 2; j++) {
				int id = 2*i+j;
				JLabel label1 = new JLabel(textLabel2[id]);
				label1.setSize(dx, 15);//������ � ������ �������
				label1.setLocation(x, y);//������� �������
				panel.add(label1);
				
				//�������� ��������� �������
				textbox_collection[id].setSize(dx, dy);//������ � ������ �������
				textbox_collection[id].setLocation(x, y + 20);//������� �������
				textbox_collection[id].setHorizontalAlignment(JTextField.RIGHT);//������ ���������� �������� �� ������� ���� �������
				textbox_collection[id].addKeyListener(getInputListener(id));//��������� �������� ������ c ����������
				panel.add(textbox_collection[id]);//��������� ������� �� ������ (������ � ����������)
				
				JLabel label2 = new JLabel(".");
				label2.setSize(100, 15);//������ � ������ �������
				label2.setLocation(x+2*dx+8, y+10);//������� �������
				panel.add(label2);//��������� ������� �� ������ (������ � ����������)
				
				x += dx+10;
			}
			x = 20;
			y += 80;//����������� ��������, ���� �������� �������� ���������� ������� �� ����������
		}
		
		JLabel label = new JLabel("���������� ����������� �������");//��������� ����
		label.setSize(250, 15);//������ � ������ �������
		label.setLocation(x, y);//������� �������
		panel.add(label);//��������� ������ �� ������ (������ � ����������)
		
		//�������� ��������� �������
		textbox_collection[6].setSize(dx, dy);//������ � ������ �������
		textbox_collection[6].setLocation(x, y + 20);//������� �������
		textbox_collection[6].setHorizontalAlignment(JTextField.RIGHT);//������ ���������� �������� �� ������� ���� �������
		textbox_collection[6].addKeyListener(getInputListener(6));//��������� �������� ������ c ����������
		panel.add(textbox_collection[6]);//��������� ������ �� ������ (������ � ����������)
		
		x = 20;
		y += 80;//����������� ��������, ���� �������� �������� ���������� ������� �� ����������
		
		JLabel label1 = new JLabel("���������");
		label1.setSize(dx, 15);
		label1.setLocation(x, y);
		panel.add(label1); 
		y += 15;//����������� ��������, ���� �������� �������� ���������� ������� �� ����������
		
		//�������� ��������� �������
		textbox_collection[7].setEditable(false);//������ �� ���� � ����
		textbox_collection[7].setSize(2*dx, dy);//������ � ������ �������
		textbox_collection[7].setLocation(x, y);//������� �������
		textbox_collection[7].setHorizontalAlignment(JTextField.RIGHT);//������ ���������� �������� �� ������� ���� �������
		panel.add(textbox_collection[7]);//��������� ������ �� ������ (������ � ����������)
		
		JLabel label2 = new JLabel("���.");
		label2.setSize(100, 15);
		label2.setLocation(x+2*dx+8, y+10);
		panel.add(label2);
		
		y += 40;//����������� ��������, ���� �������� �������� ���������� ������� �� ����������
		
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
		//������������ � ������� ���� � ������� ��������� ����������
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
			
		setContentPane(panel);//��������
		setSize(350, 600);//������� ������
		setVisible(true);
	}
	public static void main(String[] args) {
		new Calculate();
	}
}