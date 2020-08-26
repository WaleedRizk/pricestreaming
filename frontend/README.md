Pricing Stream UI

This is the React based UI that allows the streaming ticker data to be shown on a grid.

Getting Started

In the frontend folder, run 

npm install

then

npm start 

The UI will run on localhost on port 3000 by default.

Prerequisites
You will need to have Kafka running, along with Redis. The KafkaProducer connects to the streaming service via REST, produces onto Kafka topic, 
and the KafkaConsumer reads the streaming ticker prices and pushes them via a websocket to the UI every second.


Built With
React- The web framework used
Maven - Dependency Management
AG-Grid - for the grid function

Authors
Waleed Rizk

License
This project is free for all.

Acknowledgments
Everyone who contributed to the frameorks used.