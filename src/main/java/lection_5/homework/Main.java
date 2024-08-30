package lection_5.homework;

import lection_5.homework.facade.ComplexSystems.*;
import lection_5.homework.facade.HomeEntertainmentSystemFacade;
import lection_5.homework.mediator.ChatMediator;
import lection_5.homework.mediator.User;
import lection_5.homework.singleton.MySingleton;

public class Main {

    public static void main(String[] args) {
        System.out.println("---SINGLETON---");
        MySingleton object1 = MySingleton.getInstance();
        MySingleton object2 = MySingleton.getInstance();

        System.out.println("Object1 == Object2?");
        System.out.println(object1 == object2);

        System.out.println("---MEDIATOR---");
        ChatMediator mediator = new ChatMediator();

        User user1 = new User(mediator, "Alex");
        User user2 = new User(mediator, "Ivan");
        User user3 = new User(mediator, "Anna");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);

        user1.sendMessage("Hello, everybody!");

        System.out.println("---FACADE---");
        HomeEntertainmentSystemFacade homeEntertainmentSystemFacade = new HomeEntertainmentSystemFacade(
                new ProjectorSystem(),
                new LightSystem(),
                new SoundSystem(),
                new MultimediaSystem(),
                new VideoGameSystem());
        homeEntertainmentSystemFacade.watchMovie("Pulp fiction");
        homeEntertainmentSystemFacade.turnOff();
        homeEntertainmentSystemFacade.playVideoGames();
    }
}
