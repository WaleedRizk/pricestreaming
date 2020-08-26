package com.assignment.tickerService.kafkaConsumer.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@ConditionalOnProperty(
        value="useKafkaMessageBus",
        havingValue = "true",
        matchIfMissing = false)
public class TickerChannelKafkaAdapter {

    @Value("${tickerAPIEventTopic}")
    private String tickerEventTopic;

    @Autowired
    PublishSubscribeChannel tickerChannel;

    @Autowired
    ConsumerFactory consumerFactory;

    @Bean
    public KafkaMessageDrivenChannelAdapter kafkaMessageDrivenInquiryChannelAdapter() {
        KafkaMessageDrivenChannelAdapter kafkaMessageDrivenChannelAdapter = new KafkaMessageDrivenChannelAdapter(tickerKafkaListenerContainer(),
                KafkaMessageDrivenChannelAdapter.ListenerMode.record);
        kafkaMessageDrivenChannelAdapter.setOutputChannel(tickerChannel);
        return kafkaMessageDrivenChannelAdapter;
    }

    @Bean
    public ConcurrentMessageListenerContainer tickerKafkaListenerContainer() {
        ContainerProperties containerProps = new ContainerProperties(tickerEventTopic);
        return (ConcurrentMessageListenerContainer) new ConcurrentMessageListenerContainer(consumerFactory, containerProps);
    }

/*    @KafkaListener(topics = "assignment.BondLink.External.Public.Inquiry.Server.InquiryAPI")
    public void listen(Object cr) throws Exception {
        System.out.println(cr);
    }*/
}
