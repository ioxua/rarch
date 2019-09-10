package br.edu.ioxua.rarch.core.regional;

import br.edu.ioxua.rarch.core.repository.RepositoryImpl;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

@Injectable
@Named(clazz = Regional.class)
public class RegionalRepository extends RepositoryImpl<Regional> {
    public RegionalRepository() {
        super(Regional.class);
    }
}
