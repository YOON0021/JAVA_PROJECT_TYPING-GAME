import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
    private int score = 0;
    private int life = 5;
    
    private JLabel scoreLabel = new JLabel("SCORE: 0");
    private JPanel lifePanel = new JPanel(); // 하트들을 담을 패널
    private JLabel[] hearts = new JLabel[5]; // 하트 아이콘 배열
    
    public ScorePanel() {
        this.setBackground(new Color(50, 50, 50)); // 패널 배경
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        // 점수판
        scoreLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        scoreLabel.setForeground(Color.YELLOW);
        add(scoreLabel);
        
        // 하트 패널 설정
        lifePanel.setOpaque(false);
        add(lifePanel);

        // 하트 아이콘 로드
        ImageIcon heartIcon = new ImageIcon("assets/images/heart.png");
        // 이미지 크기 조절
        Image img = heartIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        heartIcon = new ImageIcon(img);

        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new JLabel(heartIcon);
            lifePanel.add(hearts[i]);
        }
    }
    // 점수 증가
    public void increase(int amount) {
        score += amount;
        scoreLabel.setText("SCORE: " + score);
    }
    // 생명력 감소
    public int decreaseLife() {
        if (life > 0) {
            life--;
            // 하트 하나 안 보이게끔
            hearts[life].setVisible(false); 
        }
        return life;
    }
    // 점수 반환
    public int getScore() { return score; }
}