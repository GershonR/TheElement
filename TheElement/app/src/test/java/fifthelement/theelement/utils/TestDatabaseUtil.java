package fifthelement.theelement.utils;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import fifthelement.theelement.application.Main;

public class TestDatabaseUtil {
    private static final File DB_SRC = new File("app/src/main/assets/db/Element.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}

