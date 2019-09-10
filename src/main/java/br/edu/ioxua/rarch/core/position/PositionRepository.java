package br.edu.ioxua.rarch.core.position;

import br.edu.ioxua.rarch.core.repository.RepositoryImpl;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

@Injectable
@Named(clazz = Position.class)
public class PositionRepository extends RepositoryImpl<Position> {
    public PositionRepository() {
        super(Position.class);
    }
}
