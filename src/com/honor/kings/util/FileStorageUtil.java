package com.honor.kings.util;

import com.honor.kings.model.entity.Equipment;
import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.entity.Team;
import com.honor.kings.model.person.Player;

import java.io.*;
import java.util.List;

// 文件I/O 工具类：使用 Java 序列化实现数据持久化
// ObjectOutputStream / ObjectInputStream 将对象写入/读出 .ser 文件
// 所有实体类已实现 Serializable 接口，确保可序列化
public class FileStorageUtil {

    private static final String DATA_DIR = "data";
    private static final String SAVE_FILE = DATA_DIR + "/game_data.ser";

    // 保存全部数据：将5个列表封装为 GameData 对象，序列化到文件
    // try-with-resources 自动关闭流，无需手动 close()
    public static void saveAllData(List<Player> players, List<Hero> heroes,
                                    List<Equipment> equipment, List<Team> teams,
                                    List<MatchRecord> matches) {
        try {
            File dir = new File(DATA_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            GameData gameData = new GameData(players, heroes, equipment, teams, matches);
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
