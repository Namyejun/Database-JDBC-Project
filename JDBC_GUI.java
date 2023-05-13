import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class JDBC_GUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	//로그인 창
	private JPanel panel = new JPanel();
	private JLabel idLabel = new JLabel("아이디");
	private JLabel pwdLabel = new JLabel("비밀번호");
	private JTextField idInput = new JTextField();
	private JPasswordField pwdInput = new JPasswordField();
	private JButton loginButton = new JButton("로그인");
	private JFrame frame = new JFrame();
	
	//구매패널
	private JLabel modelLabel = new JLabel("모댈번호");
	private JLabel countLabel = new JLabel("개수");
	
	private JButton buyButton = new JButton("구매");
	private JButton quitButton = new JButton("닫기");
	
	private JLabel pcLabel = new JLabel("PC");
	private JTextField pcInput = new JTextField();
	private JComboBox<Integer> pcnum = new JComboBox<Integer>();
	
	private JLabel laptopLabel = new JLabel("Laptop");
	private JTextField laptopInput = new JTextField();
	private JComboBox<Integer> laptopnum = new JComboBox<Integer>();
	
	private JLabel printerLabel = new JLabel("Printer");
	private JTextField printerInput = new JTextField();
	private JComboBox<Integer> printernum = new JComboBox<Integer>();
	
	String pcmodel = "";
	int pcprice = 0;
	int pcquantity = 0;
	
	String laptopmodel = "";
	int laptopprice = 0;
	int laptopquantity = 0;
	
	String printermodel = "";
	int printerprice = 0;
	int printerquantity = 0;
	
	//로그인 정보
	private String username;
	private String password;
	private static Connection dbTest;
	
	//
	private JLabel titleLabel = new JLabel("DB 컴퓨터 가게");
	private JLabel helloLabel = new JLabel("좋은 시간되세요. "+LocalDate.now());
	
	private JTabbedPane tab = new JTabbedPane();
	private JPanel find = new JPanel();
	private JLabel find_label = new JLabel("조회할 물품 선택");
	private JComboBox<String> find_combo = new JComboBox<String>();
	private JTextArea find_txtarea = new JTextArea();
	private JScrollPane scrl = new JScrollPane();
	
	private JPanel chk = new JPanel();
	private JTextArea chk_txtarea = new JTextArea();
	private JButton chk_buybutton = new JButton("최종구매");
	private JButton chk_resetbutton = new JButton("리셋");
	
	private JPanel purchase = new JPanel();
	private JTextArea pchs_txtarea = new JTextArea();
	private JLabel pchs_label;
	private JButton pchs_button = new JButton("조회");
	
	int total_price = 0;

	
	public JDBC_GUI() {
		panel.setLayout(null);
		
		idLabel.setBounds(20,10,60,30);
		pwdLabel.setBounds(20,50,60,30);
		idInput.setBounds(100,10,80,30);
		pwdInput.setBounds(100,50,80,30);
		loginButton.setBounds(200,25,80,35);
		
		loginButton.addActionListener(this);
		
		panel.add(idLabel);
		panel.add(pwdLabel);
		panel.add(idInput);
		panel.add(pwdInput);
		panel.add(loginButton);
		frame.add(panel);
		
		frame.setTitle("입력");
		frame.setSize(320, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void PCstore() {
		frame.setVisible(false);
		
		frame = new JFrame();
		panel = new JPanel();
		
		frame.setLayout(null);
		
		titleLabel.setFont(new Font("필기체", Font.BOLD, 30));
		helloLabel.setFont(new Font("필기체", Font.BOLD, 30));
		titleLabel.setBounds(10, 10, 480, 50);
		helloLabel.setBounds(500, 10, 480, 50);
		frame.add(titleLabel);
		frame.add(helloLabel);
		
		//구매 패널
			panel.setFont(new Font("필기체", 1, 12));
			panel.setBorder(new TitledBorder("구매"));
			panel.setBounds(10,70,480,300);
			panel.setLayout(null);
			
			modelLabel.setBounds(170, 30, 120, 30);
			countLabel.setBounds(330, 30, 120, 30);
			pcLabel.setBounds(10, 60, 120, 30);
			laptopLabel.setBounds(10, 120, 120, 30);
			printerLabel.setBounds(10, 180, 120, 30);
			pcInput.setBounds(170, 60, 120, 30);
			laptopInput.setBounds(170, 120, 120, 30);
			printerInput.setBounds(170, 180, 120, 30);
			pcnum.setBounds(330, 60, 120, 30);
			laptopnum.setBounds(330, 120, 120, 30);
			printernum.setBounds(330, 180, 120, 30);
			buyButton.setBounds(100, 235, 130, 40);
			quitButton.setBounds(250, 235, 130, 40);
			
			for(int i = 1; i <= 10; i++) {
				pcnum.addItem(i);
				laptopnum.addItem(i);
				printernum.addItem(i);
			}
			
			buyButton.addActionListener(this);
			quitButton.addActionListener(this);
			
			panel.add(modelLabel);
			panel.add(countLabel);
			panel.add(pcLabel);
			panel.add(laptopLabel);
			panel.add(printerLabel);
			panel.add(pcInput);
			panel.add(laptopInput);
			panel.add(printerInput);
			panel.add(pcnum);
			panel.add(laptopnum);
			panel.add(printernum);
			panel.add(buyButton);
			panel.add(quitButton);
		
		// 탭 패널
			tab.setBounds(510,70,480,300);
			tab.add("조회", find);
			tab.add("최종구매", chk);
			tab.add("구매내역", purchase);
			find.setLayout(null);
			chk.setLayout(null);
			purchase.setLayout(null);
			
			// 조회 패널
			find_label.setBounds(20, 20, 120, 30);
			find_combo.setBounds(160, 20, 120, 30);
			find_combo.addItem("PC");
			find_combo.addItem("Laptop");
			find_combo.addItem("Printer");
			find_txtarea.setBorder(new LineBorder(Color.gray, 2));
			find_combo.addActionListener(this);
			
			find_txtarea.setEditable(false);
			scrl.setViewportView(find_txtarea);
			scrl.setBounds(10,60,460,200);
			
			find.add(find_label);
			find.add(find_combo);
			find.add(scrl);
			
			// 최종구매 패널
			chk_txtarea.setBounds(10, 10, 460, 210);
			chk_buybutton.setBounds(220, 230, 120, 30);
			chk_resetbutton.setBounds(350, 230, 120, 30);
			chk_txtarea.setEditable(false);
			chk_buybutton.addActionListener(this);
			chk_resetbutton.addActionListener(this);
			chk.add(chk_txtarea);
			chk.add(chk_buybutton);
			chk.add(chk_resetbutton);
			
			// 구매내역
			pchs_txtarea.setBounds(10,10,460,210);
			pchs_label = new JLabel("DB 컴퓨터가게 총 수입: $" + total_price);
			pchs_label.setBounds(10, 230, 200, 30);
			pchs_button.setBounds(350, 230, 120, 30);
			pchs_txtarea.setEditable(false);
			pchs_button.addActionListener(this);
			
			purchase.add(pchs_txtarea);
			purchase.add(pchs_label);
			purchase.add(pchs_button);
			
			
		frame.add(tab);	
		frame.add(panel);
		
		frame.setTitle("JDBC와 자바 GUI 실습");
		frame.setSize(1020, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void showTable() throws SQLException{
		String specification = "";
		
		String sqlStr = "select count(column_name) num from cols where table_name = '"
				+ ((String) find_combo.getSelectedItem()).toUpperCase() + "'";
		
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		rs.next();
		int number = rs.getInt("num");
		String[] tables = new String[number];
		
		sqlStr = "select column_name from cols where table_name='"
				+ ((String)find_combo.getSelectedItem()).toUpperCase() + "'";
		stmt = dbTest.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		
		for(number = 0; rs.next(); number++) {
			tables[number] = rs.getString("column_name");
			specification += tables[number] + '\t';
			
		}
		for(specification += "\n"; number > 0; number--) {
			specification += "----------------------";
		}
		specification +="\n";
	
		sqlStr ="select * from " + (String) find_combo.getSelectedItem();
		stmt = dbTest.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		
		for(number = 0; rs.next(); number++) {
			for(int i = 0; i < tables.length; i++) {
				specification += rs.getString(tables[i]) + '\t';
			}
			specification += "\n";
		}
		find_txtarea.setText(specification);
		rs.close();
		stmt.close();
	}
	
	private void insertItem(String model, int quantity, int price) throws SQLException{
		String sqlStr = "insert into transaction(tnumber, tmodel, tcount, tprice) " + "values(tnum_seq.NEXTVAL" + "," + model
				+ "," + quantity + "," + price + ")";
		
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);

		stmt = dbTest.prepareStatement(sqlStr);
		stmt.executeUpdate();
		
		stmt.close();
	}
	
	public void showSalesDetail() throws SQLException {
		String sql = "select * from transaction";
		PreparedStatement stmt = dbTest.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		String result = "tnumber \t tmodel \t tcount \t tprice \n "
				+ "------------------------------------------------------------------------------------------ \n";
		while(rs.next()) {
			int tnumber = rs.getInt("tnumber");
			int tmodel = rs.getInt("tmodel");
			int tcount = rs.getInt("tcount");
			int tprice = rs.getInt("tprice");
			
			result += tnumber + " \t " + tmodel + " \t " + tcount + " \t " + tprice + "\n"; 
		}
		pchs_txtarea.setText(result);
		
		sql ="select sum(tprice) from transaction";
		stmt = dbTest.prepareStatement(sql);
		rs = stmt.executeQuery();
		
		rs.next();
		total_price = rs.getInt("sum(tprice)");
		
		pchs_label.setText("DB 컴퓨터가게 총 수입: $"+total_price);
		
		rs.close();
		stmt.close();
	}
	
	private void ConnectDB() {
	      try {
	         // JDBC Driver Loading
	         Class.forName("oracle.jdbc.OracleDriver");
	         dbTest = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", username, password);
	         JOptionPane.showMessageDialog(null, "로그인 되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
	         PCstore();
	      } catch (SQLException e) {
	    	  JOptionPane.showMessageDialog(null, "로그인 할 수가 없습니다. 아이디와 비밀번호를 다시 입력해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE);
	    	  new JDBC_GUI();
	      } catch (Exception e) {
	    	  JOptionPane.showMessageDialog(null, "로그인 할 수가 없습니다. 아이디와 비밀번호를 다시 입력해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE);
	    	  new JDBC_GUI();
	      }
	   }
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginButton) {
			username = idInput.getText();
			password = new String(pwdInput.getPassword());
			
			ConnectDB();
		}else if(e.getSource() == buyButton) {
			boolean isRight = true;
			if(!pcInput.getText().equals("") || !laptopInput.getText().equals("") || !printerInput.getText().equals("")) {
				if(!pcInput.getText().equals("")) {
					pcmodel = pcInput.getText();
					pcquantity = pcnum.getSelectedIndex() + 1;
					
					try {
						String pcStr = "select price from PC where model = " + pcmodel;
						PreparedStatement stmt = dbTest.prepareStatement(pcStr);
						ResultSet rs = stmt.executeQuery();
						if(rs.next()) {
							pcprice = rs.getInt("price") * pcquantity;
						}else {
							isRight = false;
							JOptionPane.showMessageDialog(null, "입력하신 모델이 존재하지 않습니다.", "메시지",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					pcmodel = "없음";
					pcquantity = 0;
				}
				if(!laptopInput.getText().equals("")) {
					laptopmodel = laptopInput.getText();
					laptopquantity = laptopnum.getSelectedIndex() + 1;
					
					try {
						String laptopStr = "select price from Laptop where model = " + laptopmodel;
						PreparedStatement stmt = dbTest.prepareStatement(laptopStr);
						ResultSet rs = stmt.executeQuery();
						if(rs.next()) {
							laptopprice = rs.getInt("price") * laptopquantity;
						}else {
							isRight = false;
							JOptionPane.showMessageDialog(null, "입력하신 모델이 존재하지 않습니다.", "메시지",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					laptopmodel = "없음";
					laptopquantity = 0;
				}
				if(!printerInput.getText().equals("")) {
					printermodel = printerInput.getText();
					printerquantity = printernum.getSelectedIndex() + 1;
					
					try {
						String printerStr = "select price from Printer where model = " + printermodel;
						PreparedStatement stmt = dbTest.prepareStatement(printerStr);
						ResultSet rs = stmt.executeQuery();
						if(rs.next()) {
							printerprice = rs.getInt("price") * printerquantity;
						}else {
							isRight = false;
							JOptionPane.showMessageDialog(null, "입력하신 모델이 존재하지 않습니다.", "메시지",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					printermodel = "없음";
					printerquantity = 0;
				}
				if(isRight) {
					String str = "";
					str += 	"-PC-\n"
							+ "PC model : " + pcmodel + "\t 개수 : " + pcquantity + "\t 가격 : " + pcprice + "\n\n";
					str += 	"-Laptop-\n"
							+ "Laptop model : " + laptopmodel + "\t 개수 : " + laptopquantity + "\t 가격 : " + laptopprice + "\n\n";
					str += 	"-Printer-\n"
							+ "Printer model : " + printermodel + "\t 개수 : " + printerquantity + "\t 가격 : " + printerprice + "\n\n";
					chk_txtarea.setText(str);
					
					JOptionPane.showMessageDialog(null,"정상적으로 추가되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					
					// 필드 초기화
					pcInput.setText("");
					laptopInput.setText("");
					printerInput.setText("");
					
					pcnum.setSelectedIndex(0);
					laptopnum.setSelectedIndex(0);
					printernum.setSelectedIndex(0);
				}
			}else {
				JOptionPane.showMessageDialog(null,"세 개 중에 한 개는 입력해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(e.getSource() == quitButton) {
			System.exit(0);
		}else if (e.getSource() == find_combo) {
			try {
				showTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource() == chk_buybutton) {
			if(chk_txtarea.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"먼저 구매를 해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			}else {
				try{
					int purchase_price = pcprice + laptopprice + printerprice;
					JOptionPane.showMessageDialog(null,"최종구매하여 [총 금액 : $" + purchase_price + "]가 결제되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					insertItem(pcmodel, pcquantity, pcprice);
					insertItem(laptopmodel, laptopquantity, laptopprice);
					insertItem(printermodel, printerquantity, printerprice);
					
					String[] button =  {"확인", "취소"};
					int chknum = JOptionPane.showOptionDialog(null,"계속 구매를 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, button, "확인");
					if(chknum == 0) {
						pcmodel = "";
						pcprice = 0;
						pcquantity = 0;
						
						laptopmodel = "";
						laptopprice = 0;
						laptopquantity = 0;
						
						printermodel = "";
						printerprice = 0;
						printerquantity = 0;
						
						chk_txtarea.setText(""); // chk_txtarea 비우기
					}else {
						System.exit(0);
					}
				
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}else if(e.getSource() == chk_resetbutton) {
			
			JOptionPane.showMessageDialog(null,"리셋 되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			
			// 변수 초기화
			pcmodel = "";
			pcprice = 0;
			pcquantity = 0;
			
			laptopmodel = "";
			laptopprice = 0;
			laptopquantity = 0;
			
			printermodel = "";
			printerprice = 0;
			printerquantity = 0;
			
			chk_txtarea.setText(""); // chk_txtarea 비우기
		}else if(e.getSource() == pchs_button) {
			try {
				showSalesDetail();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new JDBC_GUI();
		
	}
}

