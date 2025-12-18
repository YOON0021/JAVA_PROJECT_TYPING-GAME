import java.util.Collections;
import java.util.EventListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ZombieManager {
	private GamePanel gamePanel;
	private MainFrame mainFrame;
    private GroundPanel groundPanel;
    private TextStore textStore;
    private ScorePanel scorePanel;
    private boolean isGameOver = false; // 게임오버체크용
    private int difficulty; // 난이도 저장 변수

    private List<Zombie> zombies = Collections.synchronizedList(new LinkedList<>());

    public ZombieManager(GamePanel gp, MainFrame mf,GroundPanel g, TextStore t, ScorePanel s, int difficulty) {
    	this.gamePanel = gp;
        this.mainFrame = mf;
    	this.groundPanel = g;
        this.textStore = t;
        this.scorePanel = s;
        this.difficulty = difficulty;
    }

    public void start() {
        new Thread(() -> {
            // 게임 오버가 아닐 때 계속 생성
        	try { Thread.sleep(1000); } catch(Exception e){}
        	// 게임시작시 1초 정도 슬립(메인화면에서 전환시 좀비 바로 생성되면 체력감소 버그 수정)
        	
            while (!isGameOver) {
                spawnZombie(); // 좀비 생성
                long sleepTime = 2000;
                if (difficulty == 2) sleepTime = 1500; // Normal: 1.5초
                else if (difficulty == 3) sleepTime = 800;  // Hard: 0.8초 (엄청 빠름)
                try { Thread.sleep(sleepTime); } catch(Exception e){}
            }
        }).start();
    }
    // 좀비 생성
    private void spawnZombie() {
        if (isGameOver) return;

        String word = textStore.get(); // 단어 받기
        // y축 랜덤 생성
        int y = (int)(Math.random() * (groundPanel.getHeight() - 220));
        Zombie z = new Zombie(word, y, groundPanel, this, difficulty);
        zombies.add(z);

        SwingUtilities.invokeLater(() -> {
            groundPanel.add(z);
            groundPanel.repaint();
        });

        new Thread(z).start();
    }

    // 좀비가 기지에 도착했을 때
    public void handleZombieAttack(Zombie z) {
        if (isGameOver) return; // 이미 끝났으면 무시

        zombies.remove(z); // 삭제
        SwingUtilities.invokeLater(() -> {
            groundPanel.remove(z);
            groundPanel.repaint();
            
            // 체력을 깎고 남은 체력을 확인
            int remainLife = scorePanel.decreaseLife();
            
            // 체력이 0 이하고, 아직 게임오버 처리가 안 됐다면
            if (remainLife <= 0 && !isGameOver) {
                triggerGameOver();
            }
        });
    }
    // 모든 좀비 멈춤
    private void stopAllZombies() {
        synchronized (zombies) {
            for (Zombie z : zombies) z.stopMoving();
            zombies.clear();
        }
    }
    // 게임 클리어 처리
    private void triggerGameClear() {
        isGameOver = true;
        stopAllZombies();

        // 축하 메시지 출력
        JOptionPane.showMessageDialog(groundPanel, 
            "축하합니다! 살아남았습니다!\n", 
            "GAME CLEAR", 
            JOptionPane.INFORMATION_MESSAGE);

        // 클리어 기록 랭킹 저장
        String name = JOptionPane.showInputDialog(groundPanel, "명예의 전당에 이름을 남기세요:");
        if (name != null && !name.trim().isEmpty()) {
            new RankingManager().addScore(name.trim(), scorePanel.getScore());
        }
        
        mainFrame.showStartScreen(); // 메인화면 출력
    }

    // 게임 오버 처리 
    private void triggerGameOver() {
        isGameOver = true;

        // 살아있는 모든 좀비 멈추기
        synchronized (zombies) {
            for (Zombie z : zombies) {
                z.stopMoving(); 
            }
            zombies.clear();
        }
        int finalScore = scorePanel.getScore();
        
        // 이름 입력 받기
        String name = JOptionPane.showInputDialog(
            groundPanel, 
            "Game Over! 최종 점수: " + finalScore + "\n이름을 입력하세요:", 
            "기록 저장", 
            JOptionPane.QUESTION_MESSAGE
        );

        // 랭킹에 저장
        if (name != null && !name.trim().isEmpty()) {
            RankingManager rankingManager = new RankingManager();
            rankingManager.addScore(name.trim(), finalScore);
            JOptionPane.showMessageDialog(groundPanel, "랭킹에 등록되었습니다!");
        }
        // 프로그램 종료(메인화면 이동)
        mainFrame.showStartScreen();
    }

    public boolean killZombie(String word) {
        if (isGameOver) return false; // 게임 오버면 입력 무시

        synchronized (zombies) {
            Iterator<Zombie> it = zombies.iterator();
            while (it.hasNext()) {
                Zombie z = it.next();
                if (z.getWord().equals(word)) {
                    it.remove(); // 삭제
                    scorePanel.increase(20); // 점수 증가
                    new AudioPlayer().playShort("effects.wav", 1500); // 효과음
                    if (scorePanel.getScore() >= 500) {
                    	z.stopMoving(); 
                    	cleanUpZombie(z);
                        triggerGameClear(); // 승리 처리
                        return true;
                    }
                    z.die();
                    
                    return true;
                }
            }
        }
        return false;
    }
    
    public void cleanUpZombie(Zombie z) {
        SwingUtilities.invokeLater(() -> {
            groundPanel.remove(z);
            groundPanel.repaint();
        });
    }
    
}