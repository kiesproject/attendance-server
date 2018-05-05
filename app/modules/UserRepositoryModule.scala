package modules

import com.google.inject.AbstractModule
import models.{UserRepository, UserRepositoryOnPostgres}

class UserRepositoryModule extends AbstractModule {
  def configure() = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryOnPostgres])
  }
}
