package gov.hhs.gsrs.ssg4.ssg4m.validators;

import gov.hhs.gsrs.ssg4.ssg4m.models.*;

import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import ix.core.validator.ValidationResponse;

public class RequiredFieldNonNullValidator implements ValidatorPlugin<Ssg4mSyntheticPathway> {

    @Override
    public boolean supports(Ssg4mSyntheticPathway newValue, Ssg4mSyntheticPathway oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return true;
    }

    @Override
    public void validate(Ssg4mSyntheticPathway objnew, Ssg4mSyntheticPathway objold, ValidatorCallback callback) {
    }

}
