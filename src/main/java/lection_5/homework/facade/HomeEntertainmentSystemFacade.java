package lection_5.homework.facade;

import lection_5.homework.facade.ComplexSystems.*;

public class HomeEntertainmentSystemFacade {
    private ProjectorSystem projector;
    private LightSystem light;
    private SoundSystem sound;
    private MultimediaSystem multimedia;
    private VideoGameSystem videoGame;

    public HomeEntertainmentSystemFacade(ProjectorSystem projector,
                                         LightSystem light,
                                         SoundSystem sound,
                                         MultimediaSystem multimedia,
                                         VideoGameSystem videoGame) {
        this.projector = projector;
        this.light = light;
        this.sound = sound;
        this.multimedia = multimedia;
        this.videoGame = videoGame;
    }

    public void watchMovie(String title)  {
        System.out.println("***Preparing system to watch movie...");
        multimedia.turnOn();
        projector.turnOn();
        projector.setSource("Multimedia");
        sound.turnOn();
        sound.setSource("Multimedia");
        sound.setVolume(30);
        multimedia.playMovie(title);
        light.turnOff();
        System.out.println("***System ready. Enjoy your movie.");
    }

    public void listenMusic(String playlist) {
        System.out.println("***Preparing system to play music...");
        multimedia.turnOn();
        sound.turnOn();
        sound.setSource("Multimedia");
        System.out.println("***System ready. Enjoy your music.");
    }

    public void playVideoGames() {
        System.out.println("***Preparing system to play video games...");
        videoGame.turnOn();
        projector.turnOn();
        projector.setSource("VideoGame");
        sound.turnOn();
        sound.setSource("VideoGame");
        sound.setVolume(20);
        System.out.println("***System ready. Enjoy your video games.");
    }

    public void turnOff(){
        System.out.println("***Turning the system off...");
        light.turnOn();
        projector.turnOff();
        sound.turnOff();
        multimedia.turnOff();
        videoGame.turnOff();
    }
}
