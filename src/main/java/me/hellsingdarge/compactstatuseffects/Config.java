package me.hellsingdarge.compactstatuseffects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.hellsingdarge.compactstatuseffects.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static ModConfig INSTANCE = null;

    public static ModConfig getConfig() {
        return getConfig(false);
    }

    public static ModConfig getConfig(boolean serialize) {
        if (INSTANCE == null || serialize) {
            INSTANCE = readConfig();
        }

        return INSTANCE;
    }

    private static ModConfig readConfig() {
        final Path path = FMLPaths.CONFIGDIR.get().resolve("compactstatuseffects.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path.getParent());
                BufferedWriter writer = Files.newBufferedWriter(path);
                final String s = gson.toJson(new ModConfig());
                Files.write(path, s.getBytes());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            return gson.fromJson(new FileReader(path.toFile()), ModConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ModConfig();
    }
}
