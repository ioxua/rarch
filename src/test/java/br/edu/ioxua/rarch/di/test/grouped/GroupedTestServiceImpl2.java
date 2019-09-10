package br.edu.ioxua.rarch.di.test.grouped;

import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Injectable;

@Group({ "a" })
@Injectable
public class GroupedTestServiceImpl2 implements GroupedTestService {
}
