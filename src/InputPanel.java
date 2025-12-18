import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {
    
	private JTextField input = new JTextField(40);

    public InputPanel(ZombieManager manager) {
    	input.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(input);
        input.setTransferHandler(null);
        
        input.addActionListener(e -> {
            String text = input.getText();
            boolean killed = manager.killZombie(text);
            input.setText("");
        });
    }
    
    // 커서 입력창 이동
    @Override
    public boolean requestFocusInWindow() {
        return input.requestFocusInWindow();
    }
}