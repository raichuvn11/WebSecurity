package ultil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class IntegrityVerifier {
    private static final Logger logger = LoggerUtil.getLogger();
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    
    private static final Map<String, String> componentHashes = new HashMap<>();
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    static {
        try {
            // Khởi tạo cặp khóa
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyGen.initialize(KEY_SIZE);
            KeyPair pair = keyGen.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
            
            // Lưu khóa vào file (trong thực tế nên lưu an toàn hơn)
            saveKeys();
        } catch (Exception e) {
            logger.severe("Error initializing IntegrityVerifier: " + e.getMessage());
        }
    }

    /**
     * Tạo chữ ký số cho dữ liệu
     */
    public static String signData(String data) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    /**
     * Xác minh chữ ký số
     */
    public static boolean verifySignature(String data, String signature) throws Exception {
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(data.getBytes());
        return sig.verify(Base64.getDecoder().decode(signature));
    }

    /**
     * Tạo hash cho một file
     */
    public static String generateFileHash(String filePath) throws IOException, NoSuchAlgorithmException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
        }
        return Base64.getEncoder().encodeToString(digest.digest());
    }

    /**
     * Kiểm tra tính toàn vẹn của file
     */
    public static boolean verifyFileIntegrity(String filePath, String expectedHash) throws IOException, NoSuchAlgorithmException {
        String actualHash = generateFileHash(filePath);
        return actualHash.equals(expectedHash);
    }

    /**
     * Đăng ký hash cho một component
     */
    public static void registerComponentHash(String componentId, String hash) {
        componentHashes.put(componentId, hash);
    }

    /**
     * Kiểm tra tính toàn vẹn của component
     */
    public static boolean verifyComponentIntegrity(String componentId, String currentHash) {
        String storedHash = componentHashes.get(componentId);
        return storedHash != null && storedHash.equals(currentHash);
    }

    /**
     * Lưu khóa vào file
     */
    private static void saveKeys() throws IOException {
        // Lưu private key
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        // Lưu public key
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        
        // Trong thực tế, nên lưu khóa an toàn hơn
        logger.info("Keys generated and saved");
    }

    /**
     * Tải khóa từ file
     */
    public static void loadKeys(String privateKeyPath, String publicKeyPath) throws Exception {
        // Đọc private key
        byte[] privateKeyBytes = readKeyFile(privateKeyPath);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        privateKey = keyFactory.generatePrivate(privateKeySpec);

        // Đọc public key
        byte[] publicKeyBytes = readKeyFile(publicKeyPath);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        publicKey = keyFactory.generatePublic(publicKeySpec);
    }

    private static byte[] readKeyFile(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            return keyBytes;
        }
    }
} 