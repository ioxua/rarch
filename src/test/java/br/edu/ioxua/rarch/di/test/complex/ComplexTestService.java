package br.edu.ioxua.rarch.di.test.complex;

import br.edu.ioxua.rarch.di.test.simple.SimpleTestService;

public interface ComplexTestService {
    SimpleTestService getSimpleTestService();
}
