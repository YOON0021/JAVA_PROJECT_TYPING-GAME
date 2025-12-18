import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {
    private Clip clip;

    public void play(String fileName, boolean loop) {
        try {
            stop(); // 기존에 재생 중인 게 있으면 정지

            File file = new File("assets/sounds/" + fileName);
            if (!file.exists()) {
                System.out.println("오디오 파일 없음");
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);

            // 볼륨 조절
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // -10 데시벨

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void playShort(String fileName, int durationMs) {
        play(fileName, false); // 재생 시작

        new Thread(() -> {
            try { Thread.sleep(durationMs); } catch(Exception e){}
            stop(); // 시간 되면 강제 종료
        }).start();
    }
    
    // 정지
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}