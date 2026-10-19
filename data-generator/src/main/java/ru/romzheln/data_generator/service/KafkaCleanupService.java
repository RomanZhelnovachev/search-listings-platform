package ru.romzheln.data_generator.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaAdmin;
import org.apache.kafka.clients.admin.RecordsToDelete;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafkaCleanupService {

    private final KafkaAdmin kafkaAdmin;

    public void cleanupTopic() throws
            ExecutionException, InterruptedException {
        try (Admin admin = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            Map<TopicPartition, OffsetSpec> request = admin.describeTopics(
                            java.util.List.of("listing-events")
                    ).allTopicNames().get()
                    .get("listing-events")
                    .partitions()
                    .stream()
                    .map(partition -> new TopicPartition(
                            "listing-events", partition.partition()))
                    .collect(Collectors.toMap(
                            topicPartition -> topicPartition,
                            topicPartition -> OffsetSpec.latest()
                    ));
            Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> offsets =
                    admin.listOffsets(request).all().get();
            Map<TopicPartition, RecordsToDelete> recordsToDelete = offsets.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> RecordsToDelete.beforeOffset(entry.getValue().offset())
                    ));
            admin.deleteRecords(recordsToDelete).all().get();
        }
    }
}
