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

    String INCOME_ENVELOPE = "{\n" +
            "  \"inSequenceNumber\": 51,\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 3,\n" +
            "      \"price\": 0.106,\n" +
            "      \"quantity\": 7\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"deleteOrder\",\n" +
            "      \"orderId\": 10\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    String INCOME_ENVELOPE_54 = "{\n" +
            "  \"inSequenceNumber\": 54,\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"type\": \"addOrder\",\n" +
            "      \"orderId\": 87,\n" +
            "      \"productId\": \"PWD\",\n" +
            "      \"side\": \"buy\",\n" +
            "      \"price\": 3.19,\n" +
            "      \"quantity\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"addOrder\",\n" +
            "      \"orderId\": 88,\n" +
            "      \"productId\": \"PWD\",\n" +
            "      \"side\": \"sell\",\n" +
            "      \"price\": 3.85,\n" +
            "      \"quantity\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"addOrder\",\n" +
            "      \"orderId\": 89,\n" +
            "      \"productId\": \"APPLE\",\n" +
            "      \"side\": \"sell\",\n" +
            "      \"price\": 2.69,\n" +
            "      \"quantity\": 9\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 2,\n" +
            "      \"price\": 0.0948,\n" +
            "      \"quantity\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 65,\n" +
            "      \"price\": 3.18,\n" +
            "      \"quantity\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 11,\n" +
            "      \"price\": 1.09,\n" +
            "      \"quantity\": 6\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 0,\n" +
            "      \"price\": 1.64,\n" +
            "      \"quantity\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"changeOrder\",\n" +
            "      \"orderId\": 84,\n" +
            "      \"price\": 1.39,\n" +
            "      \"quantity\": 1\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
