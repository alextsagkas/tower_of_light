package main;

import interfaces.LogObserver;
import interfaces.LogSubject;
import interfaces.Resettable;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class KeyHandler implements KeyListener, Resettable, LogSubject {

    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean castBeacon;
    private boolean executeRest;
    private boolean advanceTime;
    private LogObserver logObserver;

    public KeyHandler() {
        super();

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;

        castBeacon = false;

        advanceTime = false;
    }

    public void attachLogObserver(LogObserver logObserver) {
        this.logObserver = logObserver;
    }

    public void notifyLogObserver(String log) {
        logObserver.updateLog(log);
    }

    private void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    private void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    private void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    private void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    private void setCastBeacon(boolean castBeacon) {
        this.castBeacon = castBeacon;
    }

    private void setExecuteRest(boolean executeRest) {this.executeRest = executeRest;}

    private void setAdvanceTime(boolean advanceTime) {
        this.advanceTime = advanceTime;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isCastBeacon() {
        return castBeacon;
    }

    public boolean isExecuteRest() {return executeRest;}

    public boolean isAdvanceTime() {
        return advanceTime;
    }

    public void reset() {
        setUpPressed(false);
        setDownPressed(false);
        setLeftPressed(false);
        setRightPressed(false);
        setCastBeacon(false);
        setAdvanceTime(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(@NotNull KeyEvent e) {
        setAdvanceTime(true);

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                setUpPressed(true);
                break;
            case KeyEvent.VK_S:
                setDownPressed(true);
                break;
            case KeyEvent.VK_A:
                setLeftPressed(true);
                break;
            case KeyEvent.VK_D:
                setRightPressed(true);
                break;
            case KeyEvent.VK_L:
                setCastBeacon(true);
                break;
            case KeyEvent.VK_R:
                setExecuteRest(true);
                break;
            default:
                notifyLogObserver(String.format("No action assigned to key %s", KeyEvent.getKeyText(keyCode)));
                break;
        }
    }

    @Override
    public void keyReleased(@NotNull KeyEvent e) {
        setAdvanceTime(false);

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                setUpPressed(false);
                break;
            case KeyEvent.VK_S:
                setDownPressed(false);
                break;
            case KeyEvent.VK_A:
                setLeftPressed(false);
                break;
            case KeyEvent.VK_D:
                setRightPressed(false);
                break;
            case KeyEvent.VK_L:
                setCastBeacon(false);
                break;
            case KeyEvent.VK_R:
                setExecuteRest(false);
                break;
            default:
                break;
        }
    }
}
