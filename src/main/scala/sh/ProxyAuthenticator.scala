package sh

import java.net.Authenticator;
import java.net.PasswordAuthentication

class ProxyAuthenticator(user: String, password: String) extends Authenticator {

  def this() = this("", "")

  override def getPasswordAuthentication(): PasswordAuthentication = {
    return new PasswordAuthentication(user, password.toCharArray());
  }
}