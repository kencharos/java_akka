package com.example;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor extends UntypedActor {

    private int finished = 0;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof SubActor.Request) {
            ActorRef subActor = getContext().actorOf(Props.create(SubActor.class));
            subActor.tell(message, getSelf());
        } else if (message instanceof SubActor.Response) {
            SubActor.Response res = (SubActor.Response) message;

            System.out.println("Answer is " + res.getAnswer());
            getSender().tell(PoisonPill.getInstance(), getSelf());
            finished++;
            if (finished >= 10) {
                getContext().system().terminate();
            }

        } else {
            unhandled(message);
        }
    }
}