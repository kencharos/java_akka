package com.example;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Date;

public class SubActor extends UntypedActor {

    public static class Response {
        private final int answer;

        public Response(int answer) {
            this.answer = answer;
        }

        public int getAnswer() {
            return answer;
        }
    }

    public static class Request {
        private final int num;

        public Request(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof SubActor.Request) {
            System.out.println("Start sub at " + new Date());

            Thread.currentThread().sleep(1000L);

            int num = ((SubActor.Request)message).getNum();
            System.out.println("Finish sub at " + new Date());
            getSender().tell(new Response(num * num), getSelf());

        } else {
            unhandled(message);
        }
    }
}