package com.transtour.backend.travel.controller;

import com.transtour.backend.travel.dto.SaveTaxesDTO;
import com.transtour.backend.travel.dto.TravelDto;
import com.transtour.backend.travel.model.Travel;
import com.transtour.backend.travel.service.SequenceGeneratorService;
import com.transtour.backend.travel.service.TravelService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping(path = "/v1/travel")
@CrossOrigin("*")
public class TravelController extends AbstractHandler {

    @Autowired
    TravelService service;

    @Autowired
    SequenceGeneratorService generatorService;

    @GetMapping
    public String gretting() {
        return "hello";
    }


    @PostMapping("/create")
    @Transactional
    public CompletableFuture<ResponseEntity> create(@RequestBody @Valid TravelDto travel, HttpServletRequest request, BindingResult bindingResult) throws Exception {
        Claims claims = getClaims(request);
        String userDni = claims.getSubject();
        return service
                .isOK(travel, bindingResult)
                .create(setSequence(travel), userDni)
                .thenApply(handlerTraverlCreation);
    }

    @PutMapping("/update")
    public CompletableFuture<ResponseEntity> update(@RequestBody TravelDto travel) throws Exception {
        return service.update(travel).thenApply(handlerTraverlCreation);
    }

    @PostMapping("/aprove")
    public CompletableFuture<ResponseEntity> aprove(@RequestBody Long orderNumber) throws Exception {
        return service.aprove(orderNumber).thenApply(handlerTraverlCreation);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity> findById(@PathVariable Long id) throws Exception {
        return service.find(id).thenApply(handlerFinById);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByDate(@RequestParam(name = "fecha_creacio") LocalDate date) throws Exception {
        return new ResponseEntity<TravelDto>(HttpStatus.NOT_IMPLEMENTED);

    }

    @GetMapping("/list")
    public CompletableFuture<ResponseEntity> list(Pageable pageable) throws Exception {
        return service.findAll(pageable).thenApply(handlerFinById);

    }

    @PostMapping("/saveTaxes")
    @Transactional
    public CompletableFuture<ResponseEntity> saveTaxes(@RequestBody SaveTaxesDTO saveTaxesDTO) throws Exception {
        return service.saveTaxes(saveTaxesDTO).thenApply(handlerTraverlCreation);
    }


    private TravelDto setSequence(TravelDto travel) {
        Long orderNumber = generatorService.getSequenceNumber(Travel.sequenceName);
        travel.setOrderNumber(orderNumber);
        return travel;
    }

    private Claims getClaims(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("test"))
                .parseClaimsJws(jwtToken).getBody();
    }

}
