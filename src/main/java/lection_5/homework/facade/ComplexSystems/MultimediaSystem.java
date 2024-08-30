package lection_5.homework.facade.ComplexSystems;

public class MultimediaSystem {

    public void turnOn() {
        System.out.println("Turning on multimedia system.");
    }

    public void turnOff() {
        System.out.println("Turning off multimedia system.");
    }

    public void playMovie(String title) {
        System.out.println("Multimedia system playing movie: " + title);
    }

    public void playMusic(String playlist) {
        System.out.println("Multimedia system playing music from playlist: " + playlist);
    }
}
