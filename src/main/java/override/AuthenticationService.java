package override;

import java.io.Serializable;

public interface AuthenticationService extends Serializable {
    boolean authenticate(String username, String token);
}
