package ultil;

public class LoginAttempt {
    private int attempts;
    private long lastAttemptTime;

    public LoginAttempt() {
        this.attempts = 0;
        this.lastAttemptTime = System.currentTimeMillis();
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        this.attempts++;
        this.lastAttemptTime = System.currentTimeMillis();
    }

    public long getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void reset() {
        this.attempts = 0;
        this.lastAttemptTime = System.currentTimeMillis();
    }
    public void setLastAttemptTime(long lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }
}
