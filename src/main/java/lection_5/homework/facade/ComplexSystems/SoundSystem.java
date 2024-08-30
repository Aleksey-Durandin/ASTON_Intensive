package lection_5.homework.facade.ComplexSystems;

public class SoundSystem {

    public void turnOn() {
        System.out.println("Turning on sound system.");
    }

    public void turnOff() {
        System.out.println("Turning off sound system.");
    }

    public void setSource(String source) {
        System.out.println("Sound system input source set to: " + source);
    }

    public void setVolume(int volume) {
        System.out.println("Sound system volume set to: " + volume);
    }
}
