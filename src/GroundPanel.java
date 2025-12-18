import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GroundPanel extends JPanel {
    private Image bgImage;

    public GroundPanel() {
        setLayout(null);
        // 이미지 로드
        ImageIcon icon = new ImageIcon("assets/images/background.png");
        bgImage = icon.getImage();
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}