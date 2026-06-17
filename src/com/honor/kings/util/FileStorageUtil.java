package com.honor.kings.util;

import com.honor.kings.model.entity.*;
import com.honor.kings.model.person.Player;

import java.io.*;
import java.util.List;

/**
 * FileStorageUtil：文件 I/O 工具类
 * 演示：文件I/O（ObjectOutputStream / ObjectInputStream 序列化）、
 *       异常处理（IOException / ClassNotFoundException）
 * 职责：将游戏数据序列化到 data/game_data.ser，或从该文件反序列化加载
 */
public class FileStorageUtil {

    private static final String DATA_DIR = "data";
    private static final String SAVE_FILE = DATA_DIR + "/game_data.ser";

    // 保存全部数据：将5个列表封装为 GameData 对象，序列化到文件
    // try-with-resources 自动关闭流，无需手动 close()
    public static void saveAllData(List<Player> players, List<Hero> heroes,
                                    List<Equipment> equipment, List<Team> teams,
                                    List<MatchRecord> matches) {
        saveAllData(players, heroes, equipment, teams, matches, new java.util.ArrayList<>());
    }

    // saveAllData（重载）：包含战斗记录的完整保存方法
    // 异常处理：try-with-resources 自动关闭 ObjectOutputStream
    // 文件I/O：写入 data/game_data.ser 文件
    public static void saveAllData(List<Player> players, List<Hero> heroes,
                                    List<Equipment> equipment, List<Team> teams,
                                    List<MatchRecord> matches, List<BattleRecord> battleRecords) {
        try {
            File dir = new File(DATA_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            GameData gameData = new GameData(players, heroes, equipment, teams, matches, battleRecords);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
                oos.writeObject(gameData);
                oos.flush();
            }
            System.out.println("数据已保存到 " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("保存数据失败: " + e.getMessage());
        }
    }

    // 加载存档：从 .ser 文件读取 GameData 对象并强制类型转换
    // 文件不存在时返回 null，由调用方决定是否使用默认数据
    public static GameData loadAllData() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (GameData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("读取存档文件失败: " + e.getMessage());
            return null;
        }
    }
}
