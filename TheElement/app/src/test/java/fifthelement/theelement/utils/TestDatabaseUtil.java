package fifthelement.theelement.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import fifthelement.theelement.application.Main;

public class TestDatabaseUtil {
    private static final File DB_SRC = new File("app/src/main/assets/db/Element.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        FileChannel src = new FileInputStream(DB_SRC).getChannel();
        FileChannel dest = new FileOutputStream(target).getChannel();
        dest.transferFrom(src, 0, src.size());
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}

