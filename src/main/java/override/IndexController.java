package override;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OverrideService overrideService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    String index(Model model) {
        Account account = accountRepository.findByNameEquals("Mary");
        model.addAttribute("account", account);
        return "index";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    String overrideSubmit(@ModelAttribute Account account,
                          @RequestParam("username") String username,
                          @RequestParam("token") String token,
                          Model model) {
        Account updatedAccount = overrideService.getOverride(account);

        if (validateOverride(updatedAccount.isOverride(), username, token)) {

            Account savedAccount = accountRepository.findOne(account.getAccountId());

            savedAccount.setName(updatedAccount.getName());
            savedAccount.setBalance(updatedAccount.getBalance());
            savedAccount.setPayment(updatedAccount.getPayment());
            savedAccount.setOverride(updatedAccount.isOverride());
            savedAccount.setRulesTriggered(updatedAccount.getRulesTriggered());

            accountRepository.save(savedAccount);

            updatedAccount = savedAccount;

            model.addAttribute("success", "Successfully updated.");

        } else {
            model.addAttribute("error", "Username and Token failed authentication.");
        }

        model.addAttribute("account", updatedAccount);
        return "index";
    }

    @RequestMapping("properties")
    @ResponseBody
    java.util.Properties properties() {
        return System.getProperties();
    }

    @RequestMapping("/system")
    String system() {
        return "system";
    }

    private boolean validateOverride(boolean overridePolicy, String user, String passcode) {
        boolean success = true;
        if (overridePolicy) {
            try {
                success = authenticationService.authenticate(user, passcode);
            } catch (RemoteConnectFailureException e){
                success = false;
                log.error(e.getMessage());
            }
            log.info("Is '" + user + "' authenticated: " + success);
        }
        return success;
    }
}