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
    private boolean useHPReplenish;
    private boolean useMPReplenish;
    private boolean changeWeapon;
    private boolean changeSecondaryWeapon;
    private boolean changeTrinket;
    private boolean advanceTime;
    private boolean advanceFreeActionTime;
    private LogObserver logObserver;

    public KeyHandler() {
        super();

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        castBeacon = false;
        executeRest = false;
        useHPReplenish = false;
        useMPReplenish = false;
        changeWeapon = false;
        changeTrinket = false;
        changeSecondaryWeapon = false;
        advanceTime = false;
        advanceFreeActionTime = false;
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

    private void setExecuteRest(boolean executeRest) {
        this.executeRest = executeRest;
    }

    public void setUseHPReplenish(boolean useHPReplenish) {
        this.useHPReplenish = useHPReplenish;
    }

    public void setUseMPReplenish(boolean useMPReplenish) {
        this.useMPReplenish = useMPReplenish;
    }

    public void setChangeWeapon(boolean changeWeapon) {
        this.changeWeapon = changeWeapon;
    }

    public void setChangeSecondaryWeapon(boolean changeSecondaryWeapon) {
        this.changeSecondaryWeapon = changeSecondaryWeapon;
    }

    public void setChangeTrinket(boolean changeTrinket) {
        this.changeTrinket = changeTrinket;
    }

    private void setAdvanceTime(boolean advanceTime) {
        this.advanceTime = advanceTime;
    }

    public void setAdvanceFreeActionTime(boolean advanceFreeActionTime) {
        this.advanceFreeActionTime = advanceFreeActionTime;
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

    public boolean isUseHPReplenish() {
        return useHPReplenish;
    }

    public boolean isUseMPReplenish() {
        return useMPReplenish;
    }

    public boolean isChangeWeapon() {
        return changeWeapon;
    }

    public boolean isChangeSecondaryWeapon() {
        return changeSecondaryWeapon;
    }

    public boolean isChangeTrinket() {
        return changeTrinket;
    }

    public boolean isAdvanceTime() {
        return advanceTime;
    }

    public boolean isAdvanceFreeActionTime() {
        return advanceFreeActionTime;
    }

    public void reset() {
        setUpPressed(false);
        setDownPressed(false);
        setLeftPressed(false);
        setRightPressed(false);
        setCastBeacon(false);
        setExecuteRest(false);
        setUseHPReplenish(false);
        setUseMPReplenish(false);
        setChangeWeapon(false);
        setChangeSecondaryWeapon(false);
        setChangeTrinket(false);
        setAdvanceTime(false);
        setAdvanceFreeActionTime(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(@NotNull KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                setUpPressed(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_S:
                setDownPressed(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_A:
                setLeftPressed(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_D:
                setRightPressed(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_L:
                setCastBeacon(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_R:
                setExecuteRest(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_H:
                setUseHPReplenish(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_M:
                setUseMPReplenish(true);
                setAdvanceTime(true);
                break;
            case KeyEvent.VK_T:
                setChangeWeapon(true);
                setAdvanceFreeActionTime(true);
                break;
            case KeyEvent.VK_Y:
                setChangeSecondaryWeapon(true);
                setAdvanceFreeActionTime(true);
                break;
            case KeyEvent.VK_U:
                setChangeTrinket(true);
                setAdvanceFreeActionTime(true);
                break;
            default:
                notifyLogObserver(String.format("No action assigned to key %s.", KeyEvent.getKeyText(keyCode)));
                break;
        }
    }

    @Override
    public void keyReleased(@NotNull KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                setUpPressed(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_S:
                setDownPressed(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_A:
                setLeftPressed(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_D:
                setRightPressed(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_L:
                setCastBeacon(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_R:
                setExecuteRest(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_H:
                setUseHPReplenish(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_M:
                setUseMPReplenish(false);
                setAdvanceTime(false);
                break;
            case KeyEvent.VK_T:
                setChangeWeapon(false);
                setAdvanceFreeActionTime(false);
            case KeyEvent.VK_Y:
                setChangeSecondaryWeapon(false);
                setAdvanceFreeActionTime(false);
                break;
            case KeyEvent.VK_U:
                setChangeTrinket(false);
                setAdvanceFreeActionTime(false);
                break;
            default:
                break;
        }
    }
}
