package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mayeyemi on 8/26/19,
 */


@Controller
public class UIController {

    Logger logger = Logger.getLogger("this");
        @RequestMapping("/home")
        public String home() {
           logger.log(Level.INFO, "yaaaay, im here ");
            return "index";

        }

}
