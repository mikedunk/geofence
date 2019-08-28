package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by OZON3 on 25/08/2019.
 */
@RestController
@RequestMapping(
        value = "userlog",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserLogController {

    @Autowired
    private UserLogService logService;

    @RequestMapping(value = { "/", ""}, method = RequestMethod.POST)
    @ResponseBody
    public UserLog save(@RequestBody @Validated UserLogApiModel model) throws Exception {
        try {
            return logService.save(model);
        } catch (Exception ex) {
            throw new Exception("Error saving user log");
        }
    }

    @RequestMapping(value = { "/", ""}, method = RequestMethod.GET)
    @ResponseBody
    public List<UserLog> getList() throws Exception {
        try {
            return logService.getAll();
        } catch (Exception ex) {
            throw new Exception("Error fetching user log");
        }
    }

    @RequestMapping(value = "/paged", method = RequestMethod.GET)
    @ResponseBody
    public Page<UserLog> getMerchants(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "20") int size) throws Exception {
        try {
            return logService.getAllPaged(new PageRequest(page, size));
        } catch (Exception ex) {
            throw new Exception("Error fetching user log");
        }
    }

}
