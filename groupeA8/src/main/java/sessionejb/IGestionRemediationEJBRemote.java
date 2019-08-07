package sessionejb;

import javax.ejb.Remote;

import entities.Remediation;

@Remote
public interface IGestionRemediationEJBRemote {
	Remediation addRemediation(Remediation reme);
}
