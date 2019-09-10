package br.edu.ioxua.rarch.di.test.named;

import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

@Injectable
@Named(clazz = String.class)
public class NamedTestServiceImpl2 implements NamedTestService {
}
