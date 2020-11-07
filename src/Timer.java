/**
 * <p>This is a Timer class</p>
 * <p>Counting if time have pass a period</p>
 * <p>Created on 7/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class Timer {

    private double cooldown;
    private double cooldownCount;
    private double preTime = System.currentTimeMillis();

    private boolean isPause;

    /**
     * Constructor of Timer
     * @param cooldown The length of the cool down period, in Milliseconds
     * @param countOnStart A boolean value indicate if the timer will return true on the very first moment
     * */
    public Timer(double cooldown, Boolean countOnStart){
        isPause = false;
        this.cooldown = cooldown;
        if (countOnStart){
            cooldownCount = 0;
        }else{
            cooldownCount = cooldown;
        }
    }

    /** Reset the timer when finish current cool down period */
    private void resetCooldownCount() {
        this.cooldownCount = cooldown;
    }

    /**
     * Check if the timer finish cool down
     * @return true if the timer finish a period and then do the reset
     * */
    public boolean isCool(){
        if(isPause){return false;}
        if (this.cooldownCount <= 0){
            resetCooldownCount();
            return true;
        }
        this.update();
        return false;
    }

    /**
     * Update current remaining time of the period
     * */
    public void update(){
        this.cooldownCount -= System.currentTimeMillis() - this.preTime;
        this.preTime = System.currentTimeMillis();
    }


    /**
     * Speed up or slow down the timescale multiply the percentage
     * @param percentage timescale percentage
     * */
    public void scale(double percentage){
        cooldown *= percentage;
        cooldownCount *= percentage;
    }

    /**
     * Pause the timer
     * */
    public void pauseTimer(){
        isPause = !isPause;
    }

    public boolean isPause() {
        return isPause;
    }
}
