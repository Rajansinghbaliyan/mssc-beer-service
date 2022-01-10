package guru.springframework.msscbeerservice.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BeerDto {
    private UUID id;
    private Integer version;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastModificationDate;

    private String beerName;
    private BigDecimal price;

    private BeerStyle beerStyle;

    private String upc;


}
