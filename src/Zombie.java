import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Zombie extends JLabel implements Runnable {
    private GroundPanel parent;
    private ZombieManager manager;
    private String word;
    private boolean alive = true;
    private int x, y;
    
    // 좀비마다 속도를 다르게 하기 위한 변수
    private int speed = 3; 
    private ImageIcon explosionIcon;
    
    public Zombie(String word, int y, GroundPanel parent, ZombieManager manager, int difficulty) {
        this.word = word;
        this.parent = parent;
        this.manager = manager;
        
        int screenWidth = parent.getWidth();
        if (screenWidth == 0) screenWidth = 1200;
        
        // 랜덤 좀비 선택 (1 ~ 3)
        int rank = (int)(Math.random() * 3) + 1; 
        
        // 이미지 로드
        String imagePath = "assets/images/zombie" + rank + ".gif";
        setIcon(new ImageIcon(imagePath));
        explosionIcon = new ImageIcon("assets/images/explosion.gif");
        setText(word);

        // 폰트 설정
        setFont(new Font("맑은 고딕", Font.BOLD, 18));
        setForeground(Color.WHITE);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);

        // 좀비 종류별 크기 및 속도설정
        int width = 0;
        int height = 0;
        int yCorrection = 0; // 높낮이 보정

        switch(rank) {
            case 1: // 기본 좀비
                width = 200;
                height = 180;
                speed = 3; // 보통 속도
                yCorrection = 0;
                break;
                
            case 2: // 댄서 좀비 
                width = 180; 
                height = 220; 
                speed = 3; 
                yCorrection = -20;
                break;
                
            case 3: // 미식축구 좀비 
                // 덩치가 크고 속도가 빠름
                width = 250; 
                height = 200;
                speed = 6; // 속도 2배
                yCorrection = -10;
                break;
        }
        
        if (difficulty == 2) { // Normal
            speed += 2; 
        } else if (difficulty == 3) { // Hard
            speed += 4; 
        }
        
        // 크기 적용
        setSize(width, height);
        
        // 위치 설정
        this.x = screenWidth - width; 
        this.y = y + yCorrection;
        
        setLocation(this.x, this.y);
    }

    @Override
    public void run() {
        while (alive) {
            try { Thread.sleep(50); } catch(Exception e){}
            
            //  왼쪽으로 좀비를 이동
            x -= speed; 
            
            SwingUtilities.invokeLater(() -> {
                setLocation(x, y);
                parent.repaint();
            });
            // 좀비가 벽에 닿을시
            if (x < 0) { 
                alive = false;
                manager.handleZombieAttack(this);
            }
        }
    }

    public String getWord() { return word; }
    public void die() {
        alive = false; // 이동 멈춤
        
        SwingUtilities.invokeLater(() -> {
            setText(""); // 글씨 없애기
            // 이미지 교체 (폭발 GIF)
            setIcon(explosionIcon);
            parent.repaint(); // 화면 갱신
        });

        // 0.5초뒤에 완전히 삭제
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.cleanUpZombie(Zombie.this);
            }
        });
        timer.setRepeats(false); // 한 번만 실행
        timer.start();
    }
    public void kill() { alive = false; }
    public void stopMoving() { alive = false; }
}