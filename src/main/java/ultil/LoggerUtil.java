package ultil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
    private static Logger logger = Logger.getLogger("MyAppLogger");

    static {
        try {
            // Tạo thư mục logs nếu chưa có
            Files.createDirectories(Paths.get("logs"));

            // Tạo FileHandler ghi log vào file logs/app.log, nối tiếp file cũ (append=true)
            FileHandler fh = new FileHandler("logs/app.log", true);
            fh.setFormatter(new SimpleFormatter());
            fh.setEncoding(StandardCharsets.UTF_8.name());
            fh.setLevel(Level.ALL);

            // Tạo ConsoleHandler để in log ra console
            ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(new SimpleFormatter());
            ch.setLevel(Level.ALL);

            // Set level cho logger để nhận tất cả các mức log
            logger.setLevel(Level.ALL);

            // Thêm cả file handler và console handler vào logger
            logger.addHandler(fh);
            logger.addHandler(ch);

            // Không tắt logging cha, để log mặc định vẫn hoạt động (nếu muốn tắt thì set false)
            logger.setUseParentHandlers(false);

            // In ra console thông báo đường dẫn log
            System.out.println("Logs sẽ được ghi vào file: " + Paths.get("logs/app.log").toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Không thể khởi tạo FileHandler: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
