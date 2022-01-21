package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.web.model.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPageList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@Slf4j
public class BeerController {
    private final BeerService beerService;

    @GetMapping("/")
    public ResponseEntity<BeerPageList> findAll(@RequestParam(defaultValue = "25") int pageSize,
                                                @RequestParam(defaultValue = "0") int pageNumber,
                                                @RequestParam(defaultValue = "false") boolean showInventory) {
        log.debug("Get All Beers");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.findAll(PageRequest.of(pageNumber, pageSize), showInventory));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BeerDto> getById(@NotNull @PathVariable UUID uuid,
                                           @RequestParam(defaultValue = "false") boolean showInventory) {
        log.debug("Get Beer For Id: " + uuid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.getById(uuid, showInventory));
    }

    @GetMapping("/upc/{upc}")
    public ResponseEntity<BeerDto> getByUpc(@PathVariable String upc) {
        log.debug("Get Beer Fro Upc: " + upc);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.findBeerByUpc(upc));
    }

    @PostMapping("/")
    public ResponseEntity<BeerDto> saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
        log.debug("Post Beer");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.saveNewBeer(beerDto));
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable String beerId, @Validated @RequestBody BeerDto beerDto) {
        log.debug("Put For Beer Id: " + beerId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.updateBeer(UUID.fromString(beerId), beerDto));
    }
}
