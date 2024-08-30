package lection_5.homework.facade.ComplexSystems;

public class ProjectorSystem {

    public void turnOn() {
        System.out.println("Turning on projector.");
    }

    public void turnOff() {
        System.out.println("Turning off projector.");
    }

    public void setSource(String source) {
        System.out.println("Projector input source set to: " + source);
    }
}
