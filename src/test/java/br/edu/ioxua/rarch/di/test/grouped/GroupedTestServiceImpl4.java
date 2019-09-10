package br.edu.ioxua.rarch.di.test.grouped;

import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Injectable;

@Group(clazz = String.class)
@Injectable
public class GroupedTestServiceImpl4 implements GroupedTestService {
}
