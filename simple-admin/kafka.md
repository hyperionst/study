# Kafka 명령어

### hello 토픽을 생성
```shell
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic hello
```

### delete Topic
```shell
kafka-topics --delete --topic "hello" --zookeeper "localhost:2181"
```

### 토픽 확인
```shell
kafka-topics.bat --list --zookeeper "localhost:2181"
```

### 퍼블리싱
```shell
kafka-console-producer --broker-list localhost:9092 --topic hello
```

### 서브스크라이브
```shell
kafka-console-consumer --bootstrap-server localhost:9092 --topic hello --from-beginning
```
```shell
kafka-console-consumer --bootstrap-server 114.200.254.187:9092 --topic ktsdata --from-beginning
```