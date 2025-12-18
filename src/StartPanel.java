import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPanel extends JPanel {
    private MainFrame mainFrame;
    private TextStore textStore; // 단어를 저장할 곳
    private Image bgImage;
    private int selectedDifficulty = 2;
    private JLabel diffDisplayLabel;
    public StartPanel(MainFrame mainFrame, TextStore textStore) {
        this.mainFrame = mainFrame;
        this.textStore = textStore;
        setLayout(null);

        // 배경 로드
        ImageIcon icon = new ImageIcon("assets/images/background2.png");
        bgImage = icon.getImage();

        // 제목
        JLabel titleLabel = new JLabel("ZOMBIE DEFENSE SURVIVAL");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 80));
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(150, 150, 1200, 100);
        add(titleLabel);

        // 게임 시작 버튼
        JButton startBtn = new JButton("GAME START");
        startBtn.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        startBtn.setBackground(new Color(200, 0, 0));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setBounds(550, 350, 400, 60);
        add(startBtn);
        startBtn.addActionListener(e -> mainFrame.startGame(selectedDifficulty));
        
        String[] difficulties = {"EASY (쉬움)", "NORMAL (보통)", "HARD (어려움)"};
        
        JComboBox<String> difficultyCombo = new JComboBox<>(difficulties);
        difficultyCombo.setBounds(550, 450, 400, 50); // 시작 버튼 아래 위치
        difficultyCombo.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 글씨 큼직하게
        difficultyCombo.setBackground(Color.WHITE);
        
        // 기본값을 'NORMAL' (인덱스 1)로 설정
        difficultyCombo.setSelectedIndex(1); 
        
        // 콤보박스 선택 이벤트 처리
        difficultyCombo.addActionListener(e -> {
            // 선택된 항목의 인덱스(순서)를 가져옴 (0, 1, 2)
            int index = difficultyCombo.getSelectedIndex();
            
            // 인덱스는 0부터 시작하므로, 난이도 숫자(1, 2, 3)로 맞추기 위해 +1 해줌
            selectedDifficulty = index + 1; 
            
            System.out.println("선택된 난이도: " + selectedDifficulty); // 확인용
        });
        
        add(difficultyCombo);
     // 3. [수정됨] 단어 관리 버튼 (입력창 대신 버튼 추가)
        JButton manageBtn = new JButton("단어 추가");
        manageBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        manageBtn.setBackground(Color.DARK_GRAY);
        manageBtn.setForeground(Color.WHITE);
        manageBtn.setFocusPainted(false);
        manageBtn.setBounds(550, 550, 400, 50); // 시작 버튼 아래에 배치
        add(manageBtn);
        manageBtn.addActionListener(e -> {
            // 새 창을 띄웁니다.
            new WordManagerDialog(mainFrame, textStore);
        });
        
        JButton rankBtn = new JButton("랭킹 보기");
        rankBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        rankBtn.setBackground(new Color(50, 50, 150)); // 남색
        rankBtn.setForeground(Color.WHITE);
        rankBtn.setFocusPainted(false);
        rankBtn.setBounds(550, 650, 400, 50); // 단어관리 버튼 아래
        add(rankBtn);
        // 이벤트: 랭킹 버튼 클릭 시
        rankBtn.addActionListener(e -> {
            new RankingDialog(mainFrame);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}