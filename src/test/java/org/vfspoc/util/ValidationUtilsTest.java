package org.vfspoc.util;

import org.junit.jupiter.api.Test;
import org.vfspoc.exception.VFSInvalideParameterException;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void checkNotNullOK() {
        ValidationUtils.checkNotNull(new Object(),"Erreur");
    }

    @Test
    void checkNotNullKO() {
        final String erreur = "Erreur Null";
        try {
            ValidationUtils.checkNotNull(null, erreur);
            fail("Erreur");
        }catch (VFSInvalideParameterException e){
            assertEquals(erreur,e.getMessage());
        }
    }

    @Test
    void checkNotEmptyOK() {
        ValidationUtils.checkNotEmpty("abc","Erreur");
    }

    @Test
    void checkNotEmptyNullKO() {
        final String erreur = "Erreur Null";
        try {
            ValidationUtils.checkNotEmpty(null, erreur);
            fail("Erreur");
        }catch (VFSInvalideParameterException e){
            assertEquals(erreur,e.getMessage());
        }
    }

    @Test
    void checkNotEmptyEmptyKO() {
        final String erreur = "Erreur Empty";
        try {
            ValidationUtils.checkNotEmpty("", erreur);
            fail("Erreur");
        }catch (VFSInvalideParameterException e){
            assertEquals(erreur,e.getMessage());
        }
    }

    @Test
    void checkParameterOK() {
        ValidationUtils.checkParameter(true,"Erreur");
    }

    @Test
    void checkParameterKO() {
        final String erreur = "Erreur test";
        try {
            ValidationUtils.checkParameter(false, erreur);
            fail("Erreur");
        }catch (VFSInvalideParameterException e){
            assertEquals(erreur,e.getMessage());
        }
    }
}