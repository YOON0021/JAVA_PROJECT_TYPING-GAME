import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel {
    private ScorePanel scorePanel;
    private GroundPanel groundPanel;
    private InputPanel inputPanel;
    private ZombieManager zombieManager;

    // 생성자 파라미터로 TextStore를 받음
    public GamePanel(MainFrame mainFrame, TextStore textStore, int difficulty) {
        this.setLayout(new BorderLayout());

        scorePanel = new ScorePanel();
        groundPanel = new GroundPanel();
        
        // 받은 textStore를 사용
        zombieManager = new ZombieManager(this,mainFrame,groundPanel, textStore, scorePanel,difficulty);
        inputPanel = new InputPanel(zombieManager);

        add(scorePanel, BorderLayout.NORTH);
        add(groundPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
        zombieManager.start(); 
    }
    
    @Override
    public boolean requestFocusInWindow() {
        return inputPanel.requestFocusInWindow();
    }
}