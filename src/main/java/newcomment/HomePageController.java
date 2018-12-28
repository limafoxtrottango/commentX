package newcomment;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomePageController {

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public ModelAndView homePage(ModelMap modelMap) {
        return new ModelAndView("home");
    }
}
