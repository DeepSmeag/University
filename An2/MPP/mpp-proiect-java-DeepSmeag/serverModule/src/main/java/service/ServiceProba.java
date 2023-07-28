package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import contests.model.Proba;
import contests.persistence.ProbaRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proba")
public class ServiceProba {
    @Autowired
    private ProbaRepo probaRepo;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @RequestMapping(method = RequestMethod.GET)
    public Proba[] getAll() {
        var probe = probaRepo.getAll();
        return probe.toArray(new Proba[probe.size()]);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getOne(@PathVariable Integer id) throws JsonProcessingException {
        Proba proba = probaRepo.getOne(id);
        if (proba == null) {
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(proba.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable Integer id,@RequestBody Proba proba) {
        Proba proba1 = probaRepo.getOne(id);
        if (proba1 == null) {
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        }
//        System.out.println(proba1);
//        System.out.println(proba.getNumeProba());
//        System.out.println(proba.getCategorieVarsta());
//        proba1.setNumeProba(proba.getNumeProba());
//        proba1.setCategorieVarsta(proba.getCategorieVarsta());
        boolean res = probaRepo.updateEntity(proba);
        System.out.println(res);
        proba = probaRepo.getOne(proba.getId());
//        proba to json
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(proba);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba proba) {
        System.out.println(proba);
        System.out.println(proba.getId());
        System.out.println(proba.getNumeProba().getId());
        System.out.println(proba.getCategorieVarsta().getId());
        try {
            boolean res = probaRepo.saveEntity(proba);
            System.out.println(res);
            System.out.println(proba.getId());

            return proba;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

//    @RequestMapping(value = "/get/1", method = RequestMethod.GET)
//    public ResponseEntity<String> getOne() throws JsonProcessingException {
//        Proba proba = probaRepo.getOne(1);
//        if (proba == null) {
//            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(proba);
//        return new ResponseEntity<>(json, HttpStatus.OK);
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Proba proba = probaRepo.getOne(id);
        if (proba == null) {
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        }
        probaRepo.deleteEntity(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping("/{user}/name")
    public String name(@PathVariable String user) {
        return "Hello " + user;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exceptionHandler(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleDeserializationError(HttpMessageNotReadableException ex) {
        // print stack trace to the console
        ex.printStackTrace();
        // or log the error
        // logger.error("Deserialization error", ex);

        // return a response with HTTP status 400 (Bad Request) and a message
        return new ResponseEntity<>("Deserialization error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
