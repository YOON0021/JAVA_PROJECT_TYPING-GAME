import java.io.*;
import java.util.*;

public class RankingManager {
    private static final String FILE_NAME = "ranking.txt";
    private List<ScoreEntry> rankings = new ArrayList<>();

    public static class ScoreEntry implements Comparable<ScoreEntry> {
        String name;
        int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(ScoreEntry o) {
            return o.score - this.score; // 점수 내림차순 정렬
        }
    }

    public RankingManager() {
        loadRankings(); // 파일 로드
    }

    // 파일에서 랭킹 읽어오기
    private void loadRankings() {
        rankings.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    rankings.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
                }
            }
            Collections.sort(rankings); // 읽은 후 정렬
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 새로운 점수 추가 및 저장
    public void addScore(String name, int score) {
        rankings.add(new ScoreEntry(name, score));
        Collections.sort(rankings);

        // 상위 10등까지만 유지
        if (rankings.size() > 10) {
            rankings = rankings.subList(0, 10);
        }

        saveRankings();
    }

    // 파일에 저장하기
    private void saveRankings() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ScoreEntry entry : rankings) {
                bw.write(entry.name + "," + entry.score);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 랭킹 텍스트창 불러오기
    public String getRankingText() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TOP 10 RANKING ===\n\n");
        
        if (rankings.isEmpty()) {
            sb.append("아직 등록된 랭킹이 없습니다.");
        } else {
            for (int i = 0; i < rankings.size(); i++) {
                ScoreEntry entry = rankings.get(i);
                sb.append(String.format("%d위.  %s  :  %d점\n", (i + 1), entry.name, entry.score));
            }
        }
        return sb.toString();
    }
}