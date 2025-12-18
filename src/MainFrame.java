import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
    private TextStore textStore; // 여기서 데이터를 관리

    public MainFrame() {
        setTitle("Zombie Survival Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLocationRelativeTo(null);
        
        // 1. 데이터 저장소 생성 (공유 자원)
        textStore = new TextStore();
        
        // 2. 시작 화면에 저장소 전달
        setContentPane(new StartPanel(this, textStore));
        
        setVisible(true);
    }

    public void startGame(int difficulty) {
        // 3. 게임 시작 시, 이미 단어가 추가된 textStore를 그대로 전달
        GamePanel gamePanel = new GamePanel(this,textStore,difficulty);
        
        setContentPane(gamePanel); 
        revalidate(); 
        repaint();
        
        SwingUtilities.invokeLater(() -> {
            gamePanel.requestFocusInWindow();
        });
    }
    //게임이 끝나면 다시 시작 화면으로 돌아오는 메서드
    public void showStartScreen() {
        setContentPane(new StartPanel(this, textStore));
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}