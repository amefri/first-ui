package at.technikum.firstui.event;

import at.technikum.firstui.viewmodel.AddRouteLogViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publisher {

    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);


    private final Map<Event, List<Subscriber>> subscriberMap;
    private final Map<Event, List<ObjectSubscriber>> objectSubscriberMap;

    public Publisher() {
        subscriberMap = new HashMap<>();
        objectSubscriberMap = new HashMap<>();
    }

    // subscribe(event, subscriber)
    public void subscribe(Event event, Subscriber subscriber) {
        List<Subscriber> subscribers = subscriberMap.get(event);

        if (null == subscribers) {
            subscribers = new ArrayList<>();
        }

        subscribers.add(subscriber);

        subscriberMap.put(event, subscribers);
        logger.info("Subscriber added for event: " + event + subscriber);
    }
    public void subscribe(Event event, ObjectSubscriber objectSubscriber) {
        List<ObjectSubscriber> objectSubscribers = objectSubscriberMap.get(event);

        if (null == objectSubscribers) {
            objectSubscribers = new ArrayList<>();
        }

        objectSubscribers.add(objectSubscriber);

        objectSubscriberMap.put(event, objectSubscribers);
        logger.info("ObjectSubscriber added for event: " + event + objectSubscriber);
    }

    // publish(event, message)
    public void publish(Event event, String message) {
        List<ObjectSubscriber> subscribers = objectSubscriberMap.get(event);

        if (null == subscribers) {
            // TODO: Log this event
            return;
        }

        for (ObjectSubscriber subscriber: subscribers) {
            subscriber.notify(message);
        }
    }

    public void publish(Event event, Object message) {
        List<ObjectSubscriber> subscribers = objectSubscriberMap.get(event);

        if (null == subscribers) {
            // TODO: Log this event
            return;
        }

        for (ObjectSubscriber subscriber: subscribers) {
            subscriber.notify(message);
        }
    }


}