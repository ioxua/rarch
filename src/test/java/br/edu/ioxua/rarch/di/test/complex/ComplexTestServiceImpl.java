package br.edu.ioxua.rarch.di.test.complex;

import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.test.simple.SimpleTestService;
import lombok.Getter;

import javax.inject.Inject;

@Injectable
public class ComplexTestServiceImpl implements ComplexTestService {

    @Inject
    @Getter
    private SimpleTestService simpleTestService;

}
