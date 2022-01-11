package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPageList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping("/")
    public ResponseEntity<BeerPageList> findAll(@RequestParam(defaultValue = "25") int pageSize,
                                                @RequestParam(defaultValue = "0") int pageNumber){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.findAll(PageRequest.of(pageNumber,pageSize)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BeerDto> getById(@NotNull @PathVariable String uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.getById(UUID.fromString(uuid)));
    }

    @PostMapping("/")
    public ResponseEntity<BeerDto> saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.saveNewBeer(beerDto));
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable String beerId, @Validated @RequestBody BeerDto beerDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(beerService.updateBeer(UUID.fromString(beerId), beerDto));
    }
}
