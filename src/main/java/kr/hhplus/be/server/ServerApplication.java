package kr.hhplus.be.server;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@EnableFeignClients
@EnableKafka
@SpringBootApplication
//@EnableJpaRepositories
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public NewTopic couponTopic() {
		return TopicBuilder.name("coupon")
				.partitions(10)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic orderTopic() {
		return TopicBuilder.name("orderComplete")
				.partitions(10)
				.replicas(1)
				.build();
	}
}
