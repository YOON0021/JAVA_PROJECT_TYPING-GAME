# 🧟‍♂️ Zombie Typing Defense (Java Swing Project)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Java_Swing-GUI-blue?style=for-the-badge)
![Multi-Threading](https://img.shields.io/badge/Tech-Multi--Threading-green?style=for-the-badge)

> **몰려오는 좀비들을 타자로 제압하라!** > Java Swing과 멀티스레딩(Multi-threading) 기술을 활용해 제작한 좀비 타자 디펜스 게임입니다.

---

## 📖 목차 (Table of Contents)
1. [프로젝트 개요](#-프로젝트-개요)
2. [실행 화면](#-실행-화면)
3. [주요 기능](#-주요-기능)
4. [기술 스택 및 설계](#-기술-스택-및-설계)

---

## 📝 프로젝트 개요
단순한 타자 연습에서 벗어나 **디펜스 게임(Defense Game)** 요소를 결합하여 사용자의 흥미를 높였습니다.  
각기 다른 속도를 가진 좀비들이 독립적인 스레드(Thread)로 움직이며, 파일 입출력을 통해 사용자만의 단어장을 만들고 랭킹을 경쟁할 수 있습니다.

* **개발 기간:** 2025.11.06 ~ 2025.12.16 (약 5주)
* **개발 인원:** 1명 (개인 프로젝트)
* **주요 목표:** Java GUI(Swing) 구현, 스레드 동기화 처리, 파일 I/O 데이터 관리

---

## 📸 실행 화면

| 메인 화면 | 게임 플레이 |
| :---: | :---: |
| <img width="569" height="343" alt="image" src="https://github.com/user-attachments/assets/672d3dfc-5dcc-462b-84f8-ef3a65ddf91f" /> | <img width="569" height="343" alt="image" src="https://github.com/user-attachments/assets/140e0284-e8b6-434d-bbe5-2eb22ea94c70" /> |
| **게임 오버 & 랭킹 등록** | **단어 관리** |
| <img width="569" height="342" alt="image" src="https://github.com/user-attachments/assets/64186392-e05f-4c81-9a07-393e2021ddd2" /> | <img width="569" height="291" alt="image" src="https://github.com/user-attachments/assets/f113e915-bb4c-4988-a2d7-ed8886d313c7" /> |

---

## ✨ 주요 기능

### 1. 🎮 게임 플레이 시스템
* **다양한 좀비 유형:** 일반, 댄서, 미식축구(빠름, 대형) 등 3가지 타입의 좀비가 랜덤 등장.
* **난이도 조절:** Easy, Normal, Hard 모드 제공 (좀비 생성 주기 및 이동 속도 변화).
* **타격감 구현:** 단어 입력 성공 시 **폭발 이펙트(GIF)** 및 **효과음(Sound)** 재생.

### 2. 🧵 기술적 구현 (Multi-Threading)
* **독립적 객체 이동:** 모든 좀비(`Zombie.java`)는 `Runnable`을 구현한 **독립된 스레드**로 동작하며 비동기적으로 이동합니다.
* **동기화 처리:** `synchronized` 블록과 `SwingUtilities.invokeLater`를 사용하여 UI 스레드와 로직 스레드 간의 충돌(Race Condition)을 방지했습니다.

### 3. 💾 데이터 관리 (File I/O)
* **단어장 커스터마이징:** `words.txt` 파일을 통해 게임 데이터를 관리하며, 게임 내에서 단어를 추가하면 파일에 영구 저장됩니다.
* **랭킹 시스템:** 게임 종료 시 점수를 `ranking.txt`에 저장하고, 내림차순 정렬 알고리즘을 통해 상위 10명의 랭킹을 출력합니다.

---

## 🛠 기술 스택 및 설계

### 개발 환경
* **Language:** Java 11
* **IDE:** Eclipse / VS Code
* **OS:** Windows / Mac

### 클래스 다이어그램 (Class Diagram)
```mermaid
classDiagram
    MainFrame --> StartPanel : 화면 전환
    MainFrame --> GamePanel : 화면 전환
    
    GamePanel *-- ScorePanel
    GamePanel *-- GroundPanel
    GamePanel *-- InputPanel
    GamePanel *-- ZombieManager : 관리

    InputPanel ..> ZombieManager : 입력 전달
    ZombieManager o-- Zombie : 스레드 생성
    ZombieManager ..> TextStore : 단어 요청
    ZombieManager ..> RankingManager : 랭킹 저장
    
    class ZombieManager{
        +spawnZombie()
        +killZombie()
    }
    class Zombie{
        +run() : Thread
    }
