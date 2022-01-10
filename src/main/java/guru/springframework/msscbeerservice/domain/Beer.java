package guru.springframework.msscbeerservice.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class Beer {
    private UUID id;
    private String upc;

    private String beerName;
    private BigDecimal price;
    private String beerStyle;

    private Integer minOnHand;

    private Integer version;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;
}
