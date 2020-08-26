1. This is the kafkaConsumer

2. When the user logs into the ui, a websocket connection is made from the ui to the this service.

3. For the first time, right after the user makes a connection to the service, it gets the initial last saved load from redis cache.

4. Thereafter, User then subscribes to a topic user/queue/topic and listens to events on the topics.

5. This service also subscribes to a tickerApiTopic on the kafka bus waiting for events to come through.

6. Whenever the data is pushed on this topic, the service processes message and sends it to the ui on the above created websocket connection.

7. Thereafter the prices get updated and displayed on the ui.


To run this service, Simply run the ConsumerServiceApplication. It will get started at port 8081.

P.S. you have kafka and redis running locally.