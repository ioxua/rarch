package br.edu.ioxua.rarch.web.helper;

import br.edu.ioxua.rarch.core.regional.Regional;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;
import br.edu.ioxua.rarch.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Injectable
@Named({"/regionals"})
public class RegionalViewHelper implements ViewHelper<Regional> {
    @Override
    public Regional createInstance(HttpServletRequest request, HttpServletResponse response, String paramPrefix) throws NumberFormatException {
        Regional regional = new Regional();

        regional.setId(Util.safeLongFromString(request.getParameter(paramPrefix + "id")));
        regional.setName(request.getParameter(paramPrefix + "name"));

        return regional;
    }
}
