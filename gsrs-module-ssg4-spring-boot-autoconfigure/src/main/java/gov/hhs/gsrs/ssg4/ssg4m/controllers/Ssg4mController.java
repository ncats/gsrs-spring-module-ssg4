package gov.hhs.gsrs.ssg4.ssg4m.controllers;

//import gov.hhs.gsrs.ssg4.DefaultDataSourceConfig;
import gov.hhs.gsrs.ssg4.Ssg4DataSourceConfig;
import gov.hhs.gsrs.ssg4.ssg4m.models.*;
import gov.hhs.gsrs.ssg4.ssg4m.services.Ssg4mEntityService;
import gov.hhs.gsrs.ssg4.ssg4m.searcher.LegacySsg4mSearcher;

//import ix.core.validator.ValidationResponse;
//import gov.nih.ncats.common.util.Unchecked;

/*
import gsrs.DefaultDataSourceConfig;
import gsrs.autoconfigure.GsrsExportConfiguration;
import gsrs.controller.*;
import gsrs.controller.hateoas.HttpRequestHolder;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.ETagRepository;
import gsrs.service.EtagExportGenerator;
import gsrs.service.ExportService;
import gsrs.service.GsrsEntityService;

import ix.core.models.ETag;
import ix.ginas.exporters.ExportMetaData;
import ix.ginas.exporters.ExportProcess;
import ix.ginas.exporters.Exporter;
import ix.ginas.exporters.ExporterFactory;
*/
import org.springframework.core.task.TaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.hateoas.server.ExposesResourceFor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@ExposesResourceFor(Ssg4mSyntheticPathway.class)
@RestController
@RequestMapping(Ssg4mEntityService.CONTEXT)
public class Ssg4mController {

    private final String CONTEXT = Ssg4mEntityService.CONTEXT;

    //@PersistenceContext
   // @PersistenceContext(unitName = Ssg4DataSourceConfig.NAME_ENTITY_MANAGER)
    //@PersistenceContext(unitName = DefaultDataSourceConfig.NAME_ENTITY_MANAGER)
    //private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ObjectMapper objectMapper;

    // Autowires for this module
    @Autowired
    private Ssg4mEntityService ssg4mEntityService;

    @GetMapping("")
    public ResponseEntity<Object> checkEndpoint() throws Exception {
        JSONObject status = new JSONObject();
        status.put("status", "OK");
        return new ResponseEntity(status, HttpStatus.OK);
    }

    @GetMapping("actuator/health")
    public ResponseEntity<Object> checkHealth() throws Exception {
        JSONObject status = new JSONObject();
        status.put("status", "UP");
        return new ResponseEntity(status, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getById(@PathVariable("id") String id) throws Exception {
        Optional<Ssg4mSyntheticPathway> synthPathway = ssg4mEntityService.get(id);
        if (id == null) {
            throw new IllegalArgumentException("There is no Synthetic Pathway Id provided");
        }
        if (synthPathway.isPresent()) {
            return new ResponseEntity(synthPathway.get(), HttpStatus.OK);
        }
        return new ResponseEntity(Optional.empty(), HttpStatus.OK);
    }

    @GetMapping("indexbysubuuid/{subUuid}")
    public ResponseEntity<String> findBySyntheticDetailBySubUuid(@PathVariable("subUuid") String substanceUuid) throws Exception {
        List<Ssg4mSyntheticPathwayDetail> list = ssg4mEntityService.findBySyntheticDetailBySubUuid(substanceUuid);
        if (substanceUuid == null) {
            throw new IllegalArgumentException("There is no Substance UUID provided in function getSyntheticDetailsBySubUuid");
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> createEntity(@RequestBody Ssg4mSyntheticPathway entityJson) throws Exception {
        Ssg4mSyntheticPathway result = ssg4mEntityService.create(entityJson);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Object> updateEntity(@RequestBody Ssg4mSyntheticPathway entityJson) throws Exception {
        Ssg4mSyntheticPathway result = ssg4mEntityService.update(entityJson);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    @PostMapping("/@validate")
    public ResponseEntity<Object> validateEntity(@RequestBody JsonNode entityJson) throws Exception {
        ValidationResponse<Ssg4mSyntheticPathway> result = ssg4mEntityService.validateEntity(entityJson);
        // return new ResponseEntity<>(result.getUpdatedEntity(), HttpStatus.OK);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    */
}
