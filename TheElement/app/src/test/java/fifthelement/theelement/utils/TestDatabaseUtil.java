package fifthelement.theelement.utils;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fifthelement.theelement.application.Main;

public class TestDatabaseUtil {
    private static File DB_SRC = new File("app/src/main/assets/db/Element.script");
    private static Connection c;

    public static File copyDB() throws IOException {
        File directory = new File("../");
        final File target = File.createTempFile("temp-db", ".script", directory);
        if(!DB_SRC.exists())
            DB_SRC = new File("src/main/assets/db/Element.script");
        Files.copy(DB_SRC, target);
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        try {
            c = DriverManager.getConnection("jdbc:hsqldb:file:" + Main.getDBPathName(), "SA", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static void killDB(final File tempDB) {
        try {
            final PreparedStatement st = c.prepareStatement("SHUTDOWN");
            st.execute();
            c.close();
            Path script = Paths.get(tempDB.getAbsolutePath());
            Path properties = Paths.get(tempDB.getAbsolutePath().replace(".script", ".properties"));
            try {
                java.nio.file.Files.delete(script);
                java.nio.file.Files.delete(properties);
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

