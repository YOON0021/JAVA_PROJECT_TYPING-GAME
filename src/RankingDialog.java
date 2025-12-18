import java.awt.Color;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RankingDialog extends JDialog {
    
    public RankingDialog(JFrame owner) {
        super(owner, "명예의 전당", true);
        setSize(400, 500);
        setLocationRelativeTo(owner);
        
        // 데이터 가져오기
        RankingManager manager = new RankingManager();
        String text = manager.getRankingText();
        
        // 텍스트 생성
        JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        textArea.setEditable(false); // 수정 막기
        textArea.setBackground(new Color(240, 240, 240));
        
        // 스크롤바 추가
        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll);
        
        setVisible(true);
    }
}