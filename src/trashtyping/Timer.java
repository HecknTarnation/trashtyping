package trashtyping;

/**
 *
 * @author Ben
 */
public class Timer extends Thread {

    private int ms;
    private boolean paused = false;

    public Timer() {
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                if (!paused) {
                    Thread.sleep(1);
                    ms++;
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    public void togglePaused(boolean on) {
        paused = on;
    }

    public void addTime(int ms) {
        this.ms += ms;
    }

    public int getTime() {
        return this.ms;
    }

}
