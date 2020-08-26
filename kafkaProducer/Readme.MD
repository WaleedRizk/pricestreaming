1. This is the kafka Producer.

2. We have a DataScheduler thread running which polls the priceService http://localhost:8080/price every 3 seconds.

3. If the service is up and running, it gets the latest data from the service and compares to the last available data in redis. If the data has been changed,
it updates the data in the redis cache and then publishes the message on the same kafka topic tickerApiTopic.

4. If the service is down, it wont do anything or send the data over the kafka bus.

To run this service, simply run the ServiceApplication. It will get started at port 8082.

P.S. you have kafka and redis running locally.
