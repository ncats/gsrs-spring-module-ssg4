package gov.hhs.gsrs.ssg4.ssg4m.controllers;

import gov.hhs.gsrs.ssg4.Ssg4DataSourceConfig;
import gov.hhs.gsrs.ssg4.SubstanceModuleService;
import gov.hhs.gsrs.ssg4.ssg4m.models.*;
import gov.hhs.gsrs.ssg4.ssg4m.services.Ssg4mEntityService;
import gov.hhs.gsrs.ssg4.ssg4m.searcher.LegacySsg4mSearcher;

import gov.nih.ncats.common.util.Unchecked;

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

import org.springframework.core.task.TaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
@GsrsRestApiController(context = Ssg4mEntityService.CONTEXT, idHelper = IdHelpers.NUMBER)
public class Ssg4mController extends EtagLegacySearchEntityController<Ssg4mController, Ssg4mSyntheticPathway, Long> {

    // Autowires for Starter and Substance modules
    @PersistenceContext(unitName =  Ssg4DataSourceConfig.NAME_ENTITY_MANAGER)
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private GsrsControllerConfiguration gsrsControllerConfiguration;

    @Autowired
    private GsrsExportConfiguration gsrsExportConfiguration;

    @Autowired
    private ETagRepository eTagRepository;

    @Autowired
    private ExportService exportService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ObjectMapper objectMapper;

    // Autowires for this module
    @Autowired
    private Ssg4mEntityService ssg4mEntityService;

    @Autowired
    private LegacySsg4mSearcher legacySsg4mSearcher;

    @Override
    public GsrsEntityService<Ssg4mSyntheticPathway, Long> getEntityService() {
        return ssg4mEntityService;
    }

    @Override
    protected LegacyGsrsSearchService<Ssg4mSyntheticPathway> getlegacyGsrsSearchService() {
        return legacySsg4mSearcher;
    }

    @Override
    protected Stream<Ssg4mSyntheticPathway> filterStream(Stream<Ssg4mSyntheticPathway> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }
}
