package override;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OverrideController {
    private static Logger log = LoggerFactory.getLogger(OverrideController.class);

    @Autowired
    private OverrideService overrideService;

    @RequestMapping(value = "/override",
            method = RequestMethod.GET)
    public Account getOverride(
            @RequestParam(required = true) String name,
            @RequestParam(required = true) int balance,
            @RequestParam(required = true) int payment) {

        Account account = new Account(name, balance, payment);
        return overrideService.getOverride(account);
    }
}
