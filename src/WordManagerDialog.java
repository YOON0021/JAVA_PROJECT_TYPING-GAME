import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class WordManagerDialog extends JDialog {
    private TextStore textStore;
    private JTextField inputField;

    public WordManagerDialog(JFrame owner, TextStore textStore) {
        super(owner, "단어 추가 관리", true);
        this.textStore = textStore;
        setLayout(null);
        setSize(400, 300);
        setLocationRelativeTo(owner);
        
        // 배경색 설정
        getContentPane().setBackground(new Color(240, 240, 240));

        // 안내 라벨
        JLabel label = new JLabel("게임에 나올 단어를 추가하세요");
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(50, 30, 300, 30);
        add(label);

        // 입력창
        inputField = new JTextField();
        inputField.setBounds(50, 80, 200, 40);
        inputField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        add(inputField);

        // 추가 버튼
        JButton addBtn = new JButton("추가");
        addBtn.setBounds(260, 80, 80, 40);
        addBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        add(addBtn);

        // 닫기 버튼
        JButton closeBtn = new JButton("닫기");
        closeBtn.setBounds(150, 180, 100, 40);
        add(closeBtn);

        // 추가 버튼 클릭 시
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWord();
            }
        });
        
        // 입력창에서 엔터키시
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWord();
            }
        });

        // 닫기 버튼
        closeBtn.addActionListener(e -> dispose()); // 창 닫기
        
        setVisible(true); // 창 보여주기
    }
    // 단어 추가
    private void addWord() {
        String text = inputField.getText().trim();
        if (text.length() > 0) {
            textStore.add(text); // 저장소에 추가
            inputField.setText("");
            JOptionPane.showMessageDialog(this, "[" + text + "] 단어가 추가되었습니다!");
            inputField.requestFocus();
        }
    }
}