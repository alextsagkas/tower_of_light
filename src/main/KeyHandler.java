package main;

import interfaces.Resettable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class KeyHandler implements KeyListener, Resettable {

    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean castSpell;
    private boolean advanceTime;

    public KeyHandler() {
        super();

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;

        castSpell = false;

        advanceTime = false;
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

    private void setCastSpell(boolean castSpell) {
        this.castSpell = castSpell;
    }

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

    public boolean isCastSpell() {
        return castSpell;
    }

    public boolean isAdvanceTime() {
        return advanceTime;
    }

    public void reset() {
        setUpPressed(false);
        setDownPressed(false);
        setLeftPressed(false);
        setRightPressed(false);
        setCastSpell(false);
        setAdvanceTime(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
            case KeyEvent.VK_X:
                setCastSpell(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
            case KeyEvent.VK_X:
                setCastSpell(false);
                break;
            default:
                break;
        }
    }
}
