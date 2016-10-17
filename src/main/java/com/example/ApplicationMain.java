package com.example;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ApplicationMain {

    public static void main(String[] args)throws  TimeoutException,InterruptedException{
        ActorSystem system = ActorSystem.create("MyActorSystem");
        ActorRef myActor = system.actorOf(Props.create(MyActor.class), "myActor");

        for(int i = 0; i < 10; i++) {
            myActor.tell(new MyActor.Request(i), null);
        }

        Await.ready(system.whenTerminated(), Duration.apply(1, TimeUnit.MINUTES));

    }

}