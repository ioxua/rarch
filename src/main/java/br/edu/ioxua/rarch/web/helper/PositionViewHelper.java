package br.edu.ioxua.rarch.web.helper;

import br.edu.ioxua.rarch.core.position.Position;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;
import br.edu.ioxua.rarch.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Injectable
@Named({"/positions"})
public class PositionViewHelper implements ViewHelper<Position> {
    @Override
    public Position createInstance(HttpServletRequest request, HttpServletResponse response, String paramPrefix) {
        Position position = new Position();

        position.setId(Util.safeLongFromString(request.getParameter(paramPrefix + "id")));
        position.setName(request.getParameter(paramPrefix + "name"));

        return position;
    }
}
