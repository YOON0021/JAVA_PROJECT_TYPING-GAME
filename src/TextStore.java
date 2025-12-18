import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class TextStore {
    private Vector<String> v = new Vector<String>();
    private final String FILE_NAME = "words.txt"; // 단어장 파일 이름

    public TextStore() {
        loadFromFile();
    }
    
    // 파일에서 단어 읽어오기
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        
        //  파일이 없을시 기본 단어들을 세팅하고 파일을 새로 만듦
        if (!file.exists()) {
            initDefaultWords(); // 기본 단어 파일에 추가
            saveAllToFile();    // 파일 생성 및 저장
            return;
        }

        // 파일이 있을시 벡터에 넣음
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line; // 한줄씩
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) { // 빈 줄은 무시
                    v.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 단어 가져오기
    public String get() {
        if (v.isEmpty()) return "";
        int index = (int)(Math.random() * v.size()); //랜덤 생성
        return v.get(index);
    }
    
    // 단어 추가
    public void add(String word) {
        v.add(word);
        appendToFile(word); // 파일 뒤에 이어쓰기
    }
    
    // 파일에 단어 추가
    private void appendToFile(String word) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) { // true = 이어쓰기 모드
            bw.write(word);
            bw.newLine(); // 줄 바꿈
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 초기 단어
    private void initDefaultWords() {
        v.add("apple"); v.add("river"); v.add("shadow"); v.add("mountain");
        v.add("crystal"); v.add("bridge"); v.add("whisper"); v.add("forest");
        v.add("galaxy"); v.add("window"); v.add("thunder"); v.add("ocean");
        v.add("candle"); v.add("mirror"); v.add("blanket"); v.add("lantern");
        v.add("horizon"); v.add("desert"); v.add("compass"); v.add("feather");
        v.add("breeze"); v.add("diamond"); v.add("village"); v.add("meadow");
        v.add("island"); v.add("puzzle"); v.add("garden"); v.add("silver");
        v.add("storm"); v.add("echo"); v.add("flame"); v.add("cloud");
        v.add("stone"); v.add("planet"); v.add("treasure"); v.add("kingdom");
        v.add("sailor"); v.add("shield"); v.add("ladder"); v.add("clock");
        v.add("bottle"); v.add("carpet"); v.add("train"); v.add("magnet");
        v.add("engine"); v.add("dragon"); v.add("rocket"); v.add("valley");
        v.add("castle"); v.add("violin"); v.add("rabbit"); v.add("pillow");
        v.add("turtle"); v.add("camera"); v.add("spider"); v.add("pencil");
        v.add("butter"); v.add("flower"); v.add("wallet"); v.add("helmet");
        v.add("toast"); v.add("station"); v.add("program"); v.add("keyboard");
        v.add("monitor"); v.add("coffee"); v.add("cookie"); v.add("orange");
        v.add("guitar"); v.add("dolphin"); v.add("monkey"); v.add("elephant");
        v.add("pepper"); v.add("jacket"); v.add("summer"); v.add("winter");
        v.add("sunset"); v.add("freedom"); v.add("energy"); v.add("surprise");
        v.add("mystery"); v.add("speaker"); v.add("journey"); v.add("victory");
        v.add("harmony");
    }

    // 모든 단어를 파일로 저장
    private void saveAllToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String s : v) {
                bw.write(s);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}