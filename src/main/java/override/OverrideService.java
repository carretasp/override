package override;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OverrideService {
    private Logger logger = LoggerFactory.getLogger(OverrideService.class);

    @Autowired
    private KieContainer kieContainer;

    /**
     * Create a new session, insert a account's details and fire rules to
     * determine what kind of override is to be issued.
     */
    public Account getOverride(Account account) {
        KieSession kieSession = kieContainer.newKieSession("OverridesSession");
        kieSession.insert(account);
        kieSession.fireAllRules();
        Account account1 = findOverrides(kieSession);
        kieSession.dispose();
        return account1;
    }

    private Account findOverrides(KieSession kieSession) {

        // Find all BusPass facts and 1st generation child classes of Account.
        ObjectFilter objectFilter = new ObjectFilter() {
            @Override
            public boolean accept(Object object) {
                if (Account.class.equals(object.getClass())) return true;
                if (Account.class.equals(object.getClass().getSuperclass())) return true;
                return false;
            }
        };

        // printFactsMessage(kieSession);

        List<Account> facts = new ArrayList<Account>();
        for (FactHandle handle : kieSession.getFactHandles(objectFilter)) {
            facts.add((Account) kieSession.getObject(handle));
        }
        if (facts.size() == 0) {
            return null;
        }
        // Assumes that the rules will always be generating a single fact.
        return facts.get(0);
    }

    /**
     * Print out details of all facts in working memory.
     * Handy for debugging.
     */
    @SuppressWarnings("unused")
    private void printFactsMessage(KieSession kieSession) {
        Collection<FactHandle> allHandles = kieSession.getFactHandles();

        String msg = "\nAll facts:\n";
        for (FactHandle handle : allHandles) {
            msg += "    " + kieSession.getObject(handle) + "\n";
        }
        logger.debug(msg);
    }
}