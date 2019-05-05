package pojos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

public class User extends BaseMessage {

    private String username, password, salt;
    private LocalDateTime creationDate, lastLoginDate;

    public User(Command command) {
        this.command = command;
    }

    public User(
            Command command,
            String username, String password, String salt,
            LocalDateTime creationDate, LocalDateTime lastLoginDate) {
        this.command = command;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.creationDate = creationDate;
        this.lastLoginDate = lastLoginDate;
    }

    public String hash() {

        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

        // create salt if not existing
        if(salt == null || salt.trim().length() == 0) {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            this.salt = encoder.encodeToString(salt);
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] digest = md.digest(this.password.getBytes());
            return encoder.encodeToString(digest);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
