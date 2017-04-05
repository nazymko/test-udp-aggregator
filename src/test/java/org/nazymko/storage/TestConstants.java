package org.nazymko.storage;

/**
 * Created by nazymko.patronus@gmail.com
 */
public interface TestConstants {
    String INCOME_MESSAGE = "{\n" +
            "  \"inSequenceNumber\": 1,\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"type\": \"addOrder\",\n" +
            "      \"orderId\": 3,\n" +
            "      \"productId\": \"APPLE\",\n" +
            "      \"side\": \"buy\",\n" +
            "      \"price\": 1,\n" +
            "      \"quantity\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"addOrder\",\n" +
            "      \"orderId\": 4,\n" +
            "      \"productId\": \"PEAR\",\n" +
            "      \"side\": \"sell\",\n" +
            "      \"price\": 2,\n" +
            "      \"quantity\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 2,\n" +
            "      \"price\": 2,\n" +
            "      \"quantity\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"deleteOrder\",\n" +
            "      \"orderId\": 1\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    String INCOME_MESSAGE_SINGLE_SELL = "{\n" +
            "  \"inSequenceNumber\": 2,\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"type\": \"addOrder\",\n" +
            "      \"orderId\": 3,\n" +
            "      \"productId\": \"APPLE\",\n" +
            "      \"side\": \"sell\",\n" +
            "      \"price\": 1,\n" +
            "      \"quantity\": 3\n" +
            "    }\n" +
            "  ]" +
            "}";

    String INCOME_MESSAGE_DELETE_SINGLE_SELL = "{\n" +
            "  \"inSequenceNumber\": 3,\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"type\": \"deleteOrder\",\n" +
            "      \"orderId\": 3\n" +
            "    }\n" +
            "  ]" +
            "}";
    String INCOME_MESSAGE_CHANGE_SINGLE_SELL = "{\n" +
            "  \"inSequenceNumber\": 4,\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 3,\n" +
            "      \"price\": 3.333,\n" +
            "      \"quantity\": 10\n" +
            "    }\n" +
            "  ]" +
            "}";
}
